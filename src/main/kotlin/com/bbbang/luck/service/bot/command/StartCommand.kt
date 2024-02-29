package com.bbbang.luck.service.bot.command

import com.bbbang.luck.api.bot.ext.simpleMessage
import com.bbbang.luck.api.bot.i18n.I18nConstants

import com.bbbang.luck.api.bot.type.LuckUserType
import com.bbbang.luck.api.bot.type.BotMessageType
import com.bbbang.luck.api.bot.type.CommandsType
import com.bbbang.luck.configuration.properties.TronProperties
import com.bbbang.luck.domain.bo.LuckUserBO
import com.bbbang.luck.domain.vo.LuckUserVO
import com.bbbang.luck.helper.ReplyMarkUpHelper
import com.bbbang.luck.service.LuckUserService
import com.bbbang.luck.service.LuckWalletService
import com.bbbang.luck.service.wrapper.LuckUserServiceWrapper
import io.micronaut.context.MessageSource
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.telegram.telegrambots.extensions.bots.commandbot.commands.DefaultBotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard.ForceReplyKeyboardBuilder
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.bots.AbsSender
import reactor.core.publisher.Mono
import java.util.*

/**
 * 1. 搜索到机器人后，点击开始触发
 * 2. 在群组选择指令 触发
 */
@Singleton
class StartCommand @Inject constructor(
    private val messageSource: MessageSource,
    private val tronProperties: TronProperties,
    private val luckUserServiceWrapper: LuckUserServiceWrapper,
    private val luckWalletService: LuckWalletService
) : DefaultBotCommand(CommandsType.START.code, CommandsType.START.desc) {

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
                absSender?.simpleMessage(chat?.id,errorMessage)
                return@onErrorResume Mono.empty()
            }
            .flatMap {user->
                luckWalletService.findWalletByUserId(user.id,user.groupId).map { user }
            }
            .map { _ ->
                val locale = botUser.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH
                    val welcomeStart = messageSource.getMessage("welcome.start", locale,"lv0","优秀玩家",chat?.id, botUser.id).orElse(I18nConstants.constants)
                    val sendMessage= SendMessage().apply {
                        this.chatId=chat?.id.toString()
                        this.text=welcomeStart
                    }
                    //sendMessage.replyMarkup= ReplyMarkUpHelper.getStartReplyKeyboardMarkup()
                absSender?.executeAsync(sendMessage)
            }
            .subscribe {
            }
    }

}