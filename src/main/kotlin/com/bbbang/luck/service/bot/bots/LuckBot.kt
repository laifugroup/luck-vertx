package com.bbbang.luck.service.bot.bots

import com.bbbang.luck.configuration.properties.BotProperties
import io.micronaut.context.MessageSource
import jakarta.annotation.PostConstruct
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramWebhookCommandBot
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.generics.LongPollingBot


@Singleton
class LuckBot(options:DefaultBotOptions, private val botProperties: BotProperties) :
    TelegramLongPollingCommandBot(options,botProperties.token) {

    @Inject
    lateinit var messageSource: MessageSource

    @PostConstruct
    fun init() {
    }

    override fun getBotToken(): String {
        return botProperties.token
    }

    override fun getBotUsername(): String {
        return botProperties.username
    }



    override fun processNonCommandUpdate(update: Update?) {
       super.processInvalidCommandUpdate(update)
    }
    override fun processInvalidCommandUpdate(update: Update?) {
        super.processInvalidCommandUpdate(update)
        //无效指令
    }




}