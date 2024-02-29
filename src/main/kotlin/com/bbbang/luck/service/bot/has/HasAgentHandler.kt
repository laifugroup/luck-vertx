 package com.bbbang.luck.service.bot.has


import com.bbbang.luck.service.LuckWalletService
import com.bbbang.luck.service.wrapper.LuckUserServiceWrapper
import io.micronaut.context.MessageSource
import jakarta.inject.Singleton
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import reactor.core.publisher.Mono
import java.util.*

 @Singleton
 class HasAgentHandler(
     private val luckUserServiceWrapper: LuckUserServiceWrapper,
     private  val luckWalletService: LuckWalletService,
     private val messageSource: MessageSource,
 ) {

     fun handler(absSender: DefaultAbsSender, update: Update) {
         luckUserServiceWrapper.findByBotUser(update)
             .onErrorResume {
                 val errorMessage = it.message?:""
                 val sendMessage= SendMessage(update.message.chat?.id.toString(),errorMessage)
                 absSender.executeAsync(sendMessage)
                 return@onErrorResume Mono.empty()
             }
             .flatMap {
                 luckWalletService.findWalletByUserId(it.id,it.groupId)
             }
             .map {
                 val locale = update.message.from?.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH
                 val credit= it.credit
                 //val myKeyboardMarkup=ReplyMarkUpHelper.getMyKeyboardMarkup()
               //  absSender.replyMessage(update.message.chatId, update.message.messageId, myInfo,myKeyboardMarkup)
             }
             .subscribe()
     }




 }