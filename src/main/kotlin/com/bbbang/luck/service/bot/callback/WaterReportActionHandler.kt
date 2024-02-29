package com.bbbang.luck.service.bot.callback

import com.bbbang.luck.api.bot.i18n.I18nConstants
import com.bbbang.luck.helper.SimpleDateFormatHelper
import com.bbbang.luck.service.LuckCreditLogService
import com.bbbang.luck.service.wrapper.LuckUserServiceWrapper
import io.micronaut.context.MessageSource
import jakarta.inject.Singleton
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * 下级反水
 */
@Singleton
class WaterReportActionHandler(private val luckCreditLogService: LuckCreditLogService,
                             private var messageSource: MessageSource,
                             private var luckUserService: LuckUserServiceWrapper) {


     fun handler(absSender: DefaultAbsSender, callbackQuery: CallbackQuery){
         luckUserService.findByBotUser(callbackQuery)
             .flatMap{user->
                 user.id?.let { luckCreditLogService.findWaterStatistics(it) }
             }.map {
                 val answerCallbackQuery = AnswerCallbackQuery().apply {
                     this.callbackQueryId = callbackQuery.id
                     this.showAlert=true
                 }
                 val locale = callbackQuery.from?.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH
                 val today = LocalDateTime.now().format(DateTimeFormatter.ofPattern(SimpleDateFormatHelper.yyyyMMdd))
                 val total=(it.inviteSum?: BigDecimal.ZERO).add(it.childBoomRebateSum?: BigDecimal.ZERO)
                 val luckWaterReport = messageSource.getMessage( "luck.water.report", locale, today,
                     total,it.inviteSum?:BigDecimal.ZERO,it.childBoomRebateSum?:BigDecimal.ZERO).orElse(I18nConstants.constants)
                 answerCallbackQuery.text = luckWaterReport
                 absSender.executeAsync(answerCallbackQuery)
             }
          .subscribe {

        }
    }


}