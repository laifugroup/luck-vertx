package com.bbbang.luck.service.bot.callback


import com.bbbang.luck.api.bot.i18n.I18nConstants
import com.bbbang.luck.api.bot.type.InviteType
import com.bbbang.luck.domain.bo.LuckInviteBO
import com.bbbang.luck.service.LuckInviteService
import com.bbbang.luck.service.wrapper.LuckUserServiceWrapper
import io.micronaut.context.MessageSource
import jakarta.inject.Singleton
import org.apache.commons.codec.digest.DigestUtils
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.groupadministration.CreateChatInviteLink
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Singleton
class InviteLinkActionHandler(
                        private val luckUserServiceWrapper: LuckUserServiceWrapper,
                        private val messageSource: MessageSource,
                        private val luckInviteService: LuckInviteService
) {
    fun handler(absSender: DefaultAbsSender, callbackQuery: CallbackQuery) {
        val chat=callbackQuery.message.chat
        val botUser=callbackQuery.from

        luckUserServiceWrapper.findByBotUser(callbackQuery)
            .onErrorResume {
                val errorMessage = it.message?:""
                val sendMessage= SendMessage(chat?.id.toString(), errorMessage)
                absSender?.executeAsync(sendMessage)
                return@onErrorResume Mono.empty()
            }
            .flatMap {user->
                val sendMessage = SendMessage().apply {
                    this.chatId=chat?.id.toString()
                }
                //如果他没有加入群组，让他在群组中生成，并更新群组记录 --未作
                val userNotJoinGroup="用户：[${botUser.id}] 未加入任何群组,请在群组中生成邀请链接"
                if (user?.groupId == null){
                    sendMessage.text = userNotJoinGroup
                    absSender?.executeAsync(sendMessage)
                    return@flatMap Mono.empty()
                }
                val createChatInviteLink = CreateChatInviteLink().apply {
                    this.chatId = user.groupId.toString()
                    this.createsJoinRequest = false
                    this.expireDate = LocalDate.now().plusDays(365).atStartOfDay(ZoneOffset.UTC).toEpochSecond().toInt()
                }
                return@flatMap luckInviteService.findByUserId(user.id!!)
                    .switchIfEmpty(
                        Mono.just(absSender?.execute(createChatInviteLink!!)).map { inviteLink->
                            LuckInviteBO().apply {
                                this.userId = user.id
                                this.url = inviteLink?.inviteLink
                                this.urlHash = DigestUtils.md5Hex(this.url?.toByteArray(Charsets.UTF_8))
                                this.groupId = chat?.id
                                this.status = InviteType.ENABLE.code
                                this.expiredAt = LocalDateTime.ofEpochSecond(createChatInviteLink.expireDate.toLong(), 0, ZoneOffset.UTC)
                            }
                        }.flatMap {
                            luckInviteService.save(it)
                        }
                    )
                    .flatMap {invite->
                        if (invite.url.isNullOrEmpty()){
                            return@flatMap Mono.just(absSender?.execute(createChatInviteLink!!)).map { inviteLink->
                                LuckInviteBO().apply {
                                    this.userId = user.id
                                    this.url = inviteLink?.inviteLink
                                    this.urlHash = DigestUtils.md5Hex(this.url?.toByteArray(Charsets.UTF_8))
                                    this.groupId = chat?.id
                                    this.status = InviteType.ENABLE.code
                                    this.expiredAt = LocalDateTime.ofEpochSecond(createChatInviteLink.expireDate.toLong(), 0, ZoneOffset.UTC)
                                }
                            }.flatMap {//update
                                luckInviteService.update(invite.id!!,it)
                            }
                        }
                        return@flatMap Mono.just(invite)
                    }
            }.map {luckInvite->
                val sendInviteMessage = SendMessage().apply {
                    this.chatId=chat?.id.toString()
                }
                val locale = botUser.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH
                val luckInviteLink =messageSource.getMessage("luck.invite.link", locale, luckInvite?.url).orElse(I18nConstants.constants)
                sendInviteMessage.text = luckInviteLink
                absSender?.executeAsync(sendInviteMessage)
            }
        .subscribe()
    }




}