package com.bbbang.luck.service.bot.callback

import com.bbbang.luck.api.bot.i18n.I18nConstants

import com.bbbang.luck.helper.SimpleDateFormatHelper
import com.bbbang.luck.service.LuckInviteLogService
import com.bbbang.luck.service.wrapper.LuckUserServiceWrapper
import io.micronaut.context.MessageSource
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import java.time.format.DateTimeFormatter
import java.util.*


@Singleton
class InviteQueryActionHandler() {
    @Inject
    lateinit var messageSource: MessageSource

    @Inject
    lateinit var luckUserService: LuckUserServiceWrapper


    @Inject
    lateinit var inviteLogService: LuckInviteLogService

    fun handler(absSender: DefaultAbsSender, callbackQuery: CallbackQuery){
        luckUserService.findByBotUser(callbackQuery)
        .flatMap {user->
            inviteLogService.findUserInviteStatistics(user.id?:0)
        }.map { userInviteStatistics->
            val answerCallbackQuery = AnswerCallbackQuery().apply {
                this.callbackQueryId = callbackQuery.id
                this.showAlert=true
            }
            val locale = callbackQuery.from?.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH
                val luckBalance = messageSource.getMessage("luck.invite.query", locale, userInviteStatistics.currentDay, userInviteStatistics.currentMonth, userInviteStatistics.total,).orElse(I18nConstants.constants)
                val bindList=StringBuilder()
                userInviteStatistics.inviteLogList?.forEach {
                    bindList.append("${it.createdAt?.format(DateTimeFormatter.ofPattern(SimpleDateFormatHelper.yyMMddHHmm))}  ${if(it.remark?.length?:0 <5) it.remark else it.remark?.substring(0,5)}\n")
                }
            answerCallbackQuery.text = "$luckBalance \n${bindList}"
          val result=  absSender.executeAsync(answerCallbackQuery)
                println(result.get())
        }.subscribe()
    }

}