package com.bbbang.luck.service.bot.bots

import com.bbbang.luck.configuration.properties.BotProperties
import com.bbbang.luck.service.bot.command.CleanCommand
import io.micronaut.context.MessageSource
import jakarta.annotation.PostConstruct
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramWebhookCommandBot
import org.telegram.telegrambots.meta.api.objects.Update



@Singleton
class KeyboardBot(options: DefaultBotOptions,private val botProperties: BotProperties) :
    TelegramWebhookCommandBot(options, true, botProperties.token) {

    @Inject
    lateinit var messageSource: MessageSource

    @Inject
    lateinit var cleanCommand: CleanCommand
   // @Inject
    //lateinit var startCommand: KeyboardStartCommand

    @PostConstruct
    fun init() {
        register(cleanCommand)
       // register(startCommand)
    }

    override fun getBotToken(): String {
        return botProperties.token
    }

    override fun getBotUsername(): String {
        return botProperties.username
    }

    override fun getBotPath(): String {
        return botProperties.username
    }


    override fun processNonCommandUpdate(update: Update?) {
      // super.processInvalidCommandUpdate(update)
    }
    override fun processInvalidCommandUpdate(update: Update?) {
        super.processInvalidCommandUpdate(update)
        //无效指令
    }




}