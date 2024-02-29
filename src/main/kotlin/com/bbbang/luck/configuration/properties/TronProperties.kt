package com.bbbang.luck.configuration.properties

import com.bbbang.luck.configuration.BotConfiguration
import com.bbbang.luck.configuration.TronConfiguration
import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("tron")
class TronProperties: TronConfiguration {

    override lateinit var url: String
    override lateinit var key: String
    override var contractAddress: String="TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t"
    override lateinit var rechargeAddress: String




}

