 package com.bbbang.luck.service.bot.has


import com.bbbang.luck.api.bot.ext.simpleMessage
import com.bbbang.luck.api.bot.i18n.I18nConstants
import com.bbbang.luck.configuration.properties.TronProperties
import io.micronaut.context.MessageSource
import jakarta.inject.Singleton
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import java.util.*

 /**
  * 私聊bot信息提示
  */
 @Singleton
 class HasPrivateChatBotHandler(
     private val messageSource: MessageSource,
     private val tronProperties: TronProperties
 ) {

     fun handler(absSender: DefaultAbsSender, update: Update) {
         val locale = update.message.from?.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH
         val privateChatBotMessage = messageSource.getMessage("private.chat.bot.start", locale,tronProperties.rechargeAddress)
             .orElse(I18nConstants.constants)
         absSender.simpleMessage(update.message.chatId,privateChatBotMessage,ReplyKeyboardRemove(true))
         //println("privateChatBot=$result")
     }




 }