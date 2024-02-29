package com.bbbang.luck.service.bot.command

import com.bbbang.luck.api.bot.ext.simpleMessage
import com.bbbang.luck.api.bot.i18n.I18nConstants

import com.bbbang.luck.api.bot.type.BalanceType
import com.bbbang.luck.api.bot.type.LuckUserType
import com.bbbang.luck.api.bot.type.BotMessageType
import com.bbbang.luck.api.bot.type.CommandsType
import com.bbbang.luck.configuration.properties.TronProperties
import com.bbbang.luck.domain.vo.LuckUserVO
import com.bbbang.luck.service.LuckUserService
import com.bbbang.luck.service.wrapper.LuckUserServiceWrapper
import io.micronaut.context.MessageSource
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.telegram.telegrambots.extensions.bots.commandbot.commands.DefaultBotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender
import reactor.core.publisher.Mono
import java.util.*


@Singleton
class PartnerCommand(private val tronProperties: TronProperties,) : DefaultBotCommand(CommandsType.PARTNER.code, CommandsType.PARTNER.desc) {

    @Inject
    lateinit var messageSource: MessageSource
    @Inject
    lateinit var luckUserServiceWrapper: LuckUserServiceWrapper

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
            .map {
                val sendMessage = SendMessage().apply {
                    this.chatId=chat?.id.toString()
                    this.enableHtml(true)
                }
                val locale = botUser.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH
                val luckPartnerPolicy =messageSource.getMessage("luck.partner.policy", locale).orElse(
                    I18nConstants.constants)
                sendMessage.text = luckPartnerPolicy
                absSender?.executeAsync(sendMessage)
            }
            .subscribe {
        }
    }



}