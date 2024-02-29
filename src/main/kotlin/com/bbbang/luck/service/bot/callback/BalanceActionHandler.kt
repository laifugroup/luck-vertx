package com.bbbang.luck.service.bot.callback

import com.bbbang.luck.api.bot.i18n.I18nConstants

import com.bbbang.luck.service.LuckWalletService
import com.bbbang.luck.service.wrapper.LuckUserServiceWrapper
import io.micronaut.context.MessageSource
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import java.util.*


@Singleton
class BalanceActionHandler() {
    @Inject
    lateinit var messageSource: MessageSource

    @Inject
    lateinit var luckUserService: LuckUserServiceWrapper
    @Inject
    lateinit var luckWalletService: LuckWalletService

     fun handler(absSender: DefaultAbsSender, callbackQuery: CallbackQuery){
        val fromId = callbackQuery.from.id
         luckUserService.findByBotUser(callbackQuery).flatMap {
             luckWalletService.findWalletByUserId(it.id,it.groupId)
         }
         .map {
            val answerCallbackQuery = AnswerCallbackQuery().apply {
                 this.callbackQueryId = callbackQuery.id
                 this.showAlert=true
             }
             val locale = callbackQuery.from?.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH
             val credit= it.credit
             val luckBalance = messageSource.getMessage("luck.balance", locale,fromId,credit).orElse(
                 I18nConstants.constants)
             answerCallbackQuery.text = luckBalance
             absSender.executeAsync(answerCallbackQuery)
         }
        .subscribe{
        }
    }


}