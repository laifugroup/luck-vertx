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
import com.bbbang.luck.helper.SimpleDateFormatHelper
import com.bbbang.luck.service.LuckUserService
import com.bbbang.luck.service.LuckWalletService
import com.bbbang.luck.service.ReportService
import com.bbbang.luck.service.wrapper.LuckPlatformServiceWrapper
import com.bbbang.luck.service.wrapper.LuckUserServiceWrapper
import io.micronaut.context.MessageSource
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.telegram.telegrambots.extensions.bots.commandbot.commands.DefaultBotCommand
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard.ForceReplyKeyboardBuilder
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.bots.AbsSender
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * 报表 群组管理员报表
 */
@Singleton
class ReportCommand @Inject constructor(
    private val messageSource: MessageSource,
    private val tronProperties: TronProperties,
    private val reportService: ReportService,
    private val platformServiceWrapper: LuckPlatformServiceWrapper
) : DefaultBotCommand(CommandsType.REPORT.code, CommandsType.REPORT.desc) {

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
        //报表 -- 群组自己才能发送报表
        platformServiceWrapper.findByGroupId(chat?.id)
            .flatMap {
               if (it.adminBotUserId==botUser?.id){
                   reportService.getGroupReport(it.groupId)
               }else{
                   Mono.empty()
               }
            }
           .map {
               val analysisDate = it.analysisDate?.format(DateTimeFormatter.ofPattern(SimpleDateFormatHelper.yyyyMMdd))
                val groupReportMessage = messageSource.getMessage("luck.group.report", locale
                    ,analysisDate, it.userCounts,it.gameCounts,it.boomCounts,it.profitCounts).orElse(I18nConstants.constants)
                val sendMessage=SendMessage(botUser?.id.toString(),groupReportMessage)
                val result=   absSender?.execute(sendMessage)
                //println(result)
            }.subscribe()
    }

}