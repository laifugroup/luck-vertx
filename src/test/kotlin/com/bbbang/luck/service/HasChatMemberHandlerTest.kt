package com.bbbang.luck.service

import com.bbbang.luck.api.bot.i18n.I18nConstants
import com.bbbang.luck.api.bot.type.BotMessageType
import com.bbbang.luck.api.bot.type.ChatMembersType
import com.bbbang.luck.api.bot.type.CreditLogType
import com.bbbang.luck.configuration.properties.LuckProperties
import com.bbbang.luck.domain.bo.LuckInviteLogBO
import com.bbbang.luck.domain.po.LuckCreditLogPO
import com.bbbang.luck.service.bot.has.HasChatMemberHandler
import com.bbbang.luck.service.wrapper.LuckUserServiceWrapper
import com.bbbang.parent.utils.BigDecimalUtils
import io.micronaut.context.MessageSource
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.apache.commons.codec.digest.DigestUtils
import org.hibernate.SessionFactory
import org.hibernate.reactive.stage.Stage
import org.junit.jupiter.api.Test
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramWebhookCommandBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.*
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberMember
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

@MicronautTest
class HasChatMemberHandlerTest {

    @Inject
    lateinit var hasChatMemberHandler: HasChatMemberHandler

    @Inject
    lateinit var telegramWebhookCommandBot: TelegramWebhookCommandBot


    @Inject
    lateinit var luckProperties: LuckProperties
    @Inject
    lateinit var sessionFactory: SessionFactory
    @Inject
    lateinit var luckUserServiceWrapper: LuckUserServiceWrapper
    @Inject
    lateinit var inviteService: LuckInviteService
    @Inject
    lateinit var inviteLogService: LuckInviteLogService
    @Inject
    lateinit var luckUserService: LuckUserService
    @Inject
    lateinit var messageSource: MessageSource
    @Inject
    lateinit var luckWalletService: LuckWalletService
    @Inject
    lateinit var luckActivityService: LuckActivityService

   // private var sessionFactoryStage: Stage.SessionFactory = sessionFactory.unwrap(Stage.SessionFactory::class.java)


    @Test
    fun testHandler() {
        val update=Update().apply {
                this.chatMember= ChatMemberUpdated().apply {
                    this.newChatMember=ChatMemberMember().apply {
                        this.user=User().apply {
                            this.id=6305388872
                        }
                    }
                    this.inviteLink= ChatInviteLink().apply {
                        this.inviteLink="https://t.me/+LeyKoPDikBM3MGRl"
                    }
                    this.chat= Chat().apply {
                        this.id=-1001977552617
                        this.type= BotMessageType.SUPERGROUP.code
                    }
                    this.from= User().apply {
                        this.id=430713401
                    }
                }
        }
       val result= handler(telegramWebhookCommandBot,update).block()
        println(result)
        assert(result!=null)
    }

    fun handler(absSender: DefaultAbsSender, update: Update):Mono<Any> {
        val chatMember = update.chatMember
        val inviteLink = chatMember.inviteLink?.inviteLink
        return luckUserServiceWrapper.findByBotUser(chatMember.from,chatMember.chat)
            .flatMap { inviteeUser ->
                val md5 = DigestUtils.md5Hex(inviteLink?.toByteArray(Charsets.UTF_8))
                //var md5="8fdd9252ac42a58adbeffe21906347f6"
                inviteService.findByUrlHash(md5).flatMap {invite->
                    //邀请关系记录
                    val luckInviteLogBO= LuckInviteLogBO().apply {
                        this.userId = invite.userId
                        this.inviteUrl = invite.url
                        this.urlHash = invite.urlHash
                        this.groupId = invite.groupId
                        this.inviteeUserId = inviteeUser.id
                        this.remark=inviteeUser.userName
                    }
                    //邀请关系更新
                    luckUserService.updateInviteeUserIdById(invite.userId, inviteeUser.id)
                        .flatMap{ inviteLogService.save(luckInviteLogBO)}
                }
            }.flatMap {inviteLog->
                //新用户赠送金
                val inviteeUserWallet=luckWalletService.findByUserId(inviteLog.inviteeUserId)
                val inviteUserWallet=luckWalletService.findByUserId(inviteLog.userId)
                val activity=luckActivityService.findByNewUserActivity()
                Mono.zip(inviteeUserWallet,inviteUserWallet,activity)
            }
            .doOnError{
                println(it)
            }
            .flatMap {
                val t1=it.t1
                val t2=it.t2
                val t3=it.t3

                Mono.just( it.t1)
            }
    }


}