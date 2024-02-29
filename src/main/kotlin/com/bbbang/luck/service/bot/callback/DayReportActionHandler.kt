package com.bbbang.luck.service.bot.callback

import com.bbbang.luck.api.bot.i18n.I18nConstants

 import com.bbbang.luck.api.bot.type.CreditLogType
import com.bbbang.luck.domain.vo.DaysStatisticsVO
import com.bbbang.luck.helper.SimpleDateFormatHelper
import com.bbbang.luck.service.LuckCreditLogService
import com.bbbang.luck.service.wrapper.LuckUserServiceWrapper
import io.micronaut.context.MessageSource
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.hibernate.SessionFactory
import org.hibernate.reactive.stage.Stage
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * 游戏日报
 */
@Singleton
class DayReportActionHandler(private val luckCreditLogService: LuckCreditLogService,
                             private var messageSource: MessageSource,
                             private var luckUserService: LuckUserServiceWrapper) {


     fun handler(absSender: DefaultAbsSender, callbackQuery: CallbackQuery){
         luckUserService.findByBotUser(callbackQuery)
             .flatMap{user->
                 user.id?.let { luckCreditLogService.findGameStatistics(it) }
             }.map {
                 val answerCallbackQuery = AnswerCallbackQuery().apply {
                     this.callbackQueryId = callbackQuery.id
                     this.showAlert=true
                 }
                 val locale = callbackQuery.from?.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH
                 val today = LocalDateTime.now().format(DateTimeFormatter.ofPattern(SimpleDateFormatHelper.yyyyMMdd))
                  val profit=  (it.compensationSum?: BigDecimal.ZERO)?.add(it.grabSum?:BigDecimal.ZERO)
                      ?.minus(it.sendSum?: BigDecimal.ZERO)?.minus(it.boomSum?:BigDecimal.ZERO)
                 val luckGameReport = messageSource.getMessage( "luck.game.report", locale, today,
                     profit,(it.sendSum?:BigDecimal.ZERO)?.negate(),it.compensationSum?:BigDecimal.ZERO,it.grabSum?:BigDecimal.ZERO,(it.boomSum?:BigDecimal.ZERO)?.negate()).orElse(I18nConstants.constants)
                 answerCallbackQuery.text = luckGameReport
                 absSender.executeAsync(answerCallbackQuery)
             }
          .subscribe {

        }
    }


}