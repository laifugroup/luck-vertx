package com.bbbang.luck.service.bot.service

import com.bbbang.luck.api.bot.ext.simpleMessage
import com.bbbang.luck.api.bot.i18n.I18nConstants
import com.bbbang.luck.configuration.properties.BotProperties
import com.bbbang.luck.configuration.properties.BotWebHookProperties
import com.bbbang.luck.service.bot.bots.KeyboardBot
import com.bbbang.luck.service.bot.bots.LuckWebhookCommandBot
import com.bbbang.luck.service.wrapper.LuckPlatformServiceWrapper
import com.bbbang.parent.exception.BusinessException
import io.micronaut.context.MessageSource
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import reactor.core.publisher.Mono
import java.util.*

@Singleton
class BotWebHookService(private val  luckWebhookCommandBot: LuckWebhookCommandBot,
                        //private val keyboardBot: KeyboardBot,
                        private val  botWebHookProperties: BotWebHookProperties,
                        private val  luckPlatformServiceWrapper: LuckPlatformServiceWrapper,
                        private val  botProperties: BotProperties,
                        private val  messageSource: MessageSource,
) {

    private val logger= LoggerFactory.getLogger(BotWebHookService::class.java)

     fun onWebhookUpdateReceived(botName:String,secretToken: String, realIp: String, update: Update?): BotApiMethod<*>? {
        logger.info("realIp=${realIp} : botName=${botName} in chat=${update?.message?.chatId} with message=${update?.message?.text}")
        if (botWebHookProperties.secretToken != secretToken) {
            throw BusinessException("[非法访问]secretToken不正确")
        }else  if (botName != botProperties.username) {
            throw BusinessException("[非法访问]botName不存在")
        }
         //不是私聊 BOT 才提醒
         if (update?.message?.chatId!=update?.message?.from?.id){
             luckPlatformServiceWrapper.findByGroupId(update).onErrorResume {
                 val locale = update?.message?.from?.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH
                 val groupInitTips = messageSource.getMessage("group.init.tips", locale).orElse(I18nConstants.constants)
                luckWebhookCommandBot?.simpleMessage(update?.message?.chatId,groupInitTips)
                 return@onErrorResume Mono.empty()
             }.subscribe()
         }

        return luckWebhookCommandBot.onWebhookUpdateReceived(update)
    }

}