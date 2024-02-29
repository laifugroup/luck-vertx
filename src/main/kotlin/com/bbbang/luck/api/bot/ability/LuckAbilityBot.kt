package com.bbbang.luck.api.bot.ability

import com.bbbang.luck.configuration.properties.BotProperties
import jakarta.annotation.PostConstruct
import jakarta.inject.Singleton
import okhttp3.internal.toLongOrDefault
import org.telegram.abilitybots.api.bot.AbilityBot
import org.telegram.abilitybots.api.objects.Ability
import org.telegram.abilitybots.api.objects.Locality
import org.telegram.abilitybots.api.objects.MessageContext
import org.telegram.abilitybots.api.objects.Privacy
import org.telegram.telegrambots.bots.DefaultBotOptions


@Singleton
class LuckAbilityBot(options:DefaultBotOptions, private val botProperties: BotProperties) :
    AbilityBot(botProperties.token,botProperties.username,options) {

    @PostConstruct
    fun init() {
    }
    fun sayHelloWorld(): Ability {
        return Ability
            .builder()
            .name("hello")
            .info("says hello world!")
            .locality(Locality.ALL)
            .privacy(Privacy.PUBLIC)
            .action { ctx: MessageContext ->
                silent.send(
                    "Hello world!",
                    ctx.chatId()
                )
            }
            .build()
    }

    override fun creatorId(): Long {
       return botProperties.creatorId.toLongOrDefault(0)
    }



}