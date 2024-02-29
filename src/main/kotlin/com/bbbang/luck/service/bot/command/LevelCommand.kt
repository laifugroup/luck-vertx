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
 * 等级和称号     充值和游戏升等级 <LV0初出茅庐,LV5特别合伙人>      募捐等活动获得荣誉称号
 */
@Singleton
class LevelCommand @Inject constructor() : DefaultBotCommand(CommandsType.CLEAN.code, CommandsType.CLEAN.desc) {

    override fun execute(
        absSender: AbsSender?,
        botUser: User,
        chat: Chat?,
        messageId: Int?,
        arguments: Array<out String>?
    ) {
            val cleanMessage= SendMessage().apply {
                this.chatId=chat?.id.toString()
                this.text="已清除所有回复框"
            }
        cleanMessage.replyMarkup= ReplyKeyboardRemove(true)
            absSender?.executeAsync(cleanMessage)

    }

}