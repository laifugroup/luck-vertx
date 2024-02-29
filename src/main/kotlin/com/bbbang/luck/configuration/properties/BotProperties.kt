package com.bbbang.luck.configuration.properties

import com.bbbang.luck.configuration.BotConfiguration
import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("app.luck.bot")
class BotProperties() : BotConfiguration {
    override lateinit var creatorId: String
    override lateinit var username: String
    override lateinit var token: String
    override var desc: String="luck  bot"





}

