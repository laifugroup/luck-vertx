package com.bbbang.luck.configuration.properties

import com.bbbang.luck.configuration.ServiceConfiguration
import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("app.luck.service")
class ServiceProperties: ServiceConfiguration {

    override lateinit var customerService: String
    override lateinit var finance: String
    override lateinit var playRule:String
    override lateinit var financeBotUserId:String

}

