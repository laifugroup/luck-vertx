package com.bbbang.luck.service.bot.command

import com.bbbang.luck.api.bot.ext.simpleMessage
import com.bbbang.luck.api.bot.i18n.I18nConstants

import com.bbbang.luck.api.bot.type.*
import com.bbbang.luck.configuration.properties.TronProperties
import com.bbbang.luck.domain.bo.LuckInviteBO
import com.bbbang.luck.domain.vo.LuckInviteVO
import com.bbbang.luck.domain.vo.LuckUserVO
import com.bbbang.luck.service.LuckInviteService
import com.bbbang.luck.service.LuckUserService
import com.bbbang.luck.service.wrapper.LuckUserServiceWrapper
import io.micronaut.context.MessageSource
import io.reactivex.internal.util.HalfSerializer.onNext
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.apache.commons.codec.digest.DigestUtils
import org.telegram.telegrambots.extensions.bots.commandbot.commands.DefaultBotCommand
import org.telegram.telegrambots.meta.api.methods.groupadministration.CreateChatInviteLink
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.ChatInviteLink
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import java.util.function.Supplier

/**
 * 群组类型：默认private(私有群组)
 *    cancel join_group   和没有邀请链接是一致的。
 *
 * 群组类型： public(公共群组)
 *    join                  在群组group type设置中，必须设置为approve new members 关闭
 *    apply to join group   在群组group type设置中，必须设置为approve new members 打开
 *
 */
@Singleton
class InviteCommand(private val tronProperties: TronProperties,) : DefaultBotCommand(CommandsType.INVITE.code, CommandsType.INVITE.desc) {


    @Inject
    lateinit var messageSource: MessageSource
    @Inject
    lateinit var luckUserServiceWrapper: LuckUserServiceWrapper
    @Inject
    lateinit var luckInviteService: LuckInviteService
    override fun execute(
        absSender: AbsSender?,
        botUser: User,
        chat: Chat?,
        messageId: Int?,
        arguments: Array<out String>?
    ) {

        val locale = botUser.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH
        if (botUser.isBot){
            return
        }
        //不允许私聊中执行指令
        if (chat?.id==botUser.id){
            val privateChatCommandMessage = messageSource.getMessage("private.chat.bot.command", locale, tronProperties.rechargeAddress).orElse(I18nConstants.constants)
            absSender?.simpleMessage(chatId = chat.id,privateChatCommandMessage)
            return
        }
        luckUserServiceWrapper.findByBotUser(botUser,chat)
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
                val luckInviteLink =messageSource.getMessage("luck.invite.link", locale, luckInvite?.url).orElse(I18nConstants.constants)
                sendInviteMessage.text = luckInviteLink
                absSender?.executeAsync(sendInviteMessage)
            }.subscribe{

            }
    }





}