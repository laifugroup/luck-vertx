package com.bbbang.luck.configuration.properties

import com.bbbang.luck.configuration.BotWebHookConfiguration
import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("app.luck.bot.webhook")
class BotWebHookProperties: BotWebHookConfiguration {

    override lateinit var internalUrl: String
    override lateinit var url: String
    override lateinit var secretToken: String

}

