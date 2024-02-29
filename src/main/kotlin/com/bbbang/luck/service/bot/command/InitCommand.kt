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
import com.bbbang.luck.service.wrapper.LuckPlatformServiceWrapper
import com.bbbang.luck.service.wrapper.LuckUserServiceWrapper
import io.micronaut.context.MessageSource
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.telegram.telegrambots.extensions.bots.commandbot.commands.DefaultBotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.bots.AbsSender
import reactor.core.publisher.Mono
import java.util.*

/**
 * 1. 初始化BOT在群组使用
 *   1.1 初始化系统管理员
 *   -------------------
 *   以下都不需要设置-全部被bot归属平台托管，私有部署才需要更改！！
 *
 *   1.2 初始化管理员<ROLE>
 *   1.3 初始化财务<ROLE>
 *   1.4 初始化 客服<ROLE>
 *   1.5 初始化 玩法<订阅>
 */
@Singleton
class InitCommand @Inject constructor(
    private val messageSource: MessageSource,
    private val tronProperties: TronProperties,
    private val luckPlatformServiceWrapper: LuckPlatformServiceWrapper
) : DefaultBotCommand(CommandsType.INIT.code, CommandsType.INIT.desc) {

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

        luckPlatformServiceWrapper.saveGroupId(botUser,chat?.id)
            .map {
                val groupMessage=messageSource.getMessage("group.init.message",locale,it.groupId,it.adminBotUserId).orElse(I18nConstants.constants)
                absSender?.simpleMessage(chat?.id,groupMessage)
            }.subscribe {  }

    }

}