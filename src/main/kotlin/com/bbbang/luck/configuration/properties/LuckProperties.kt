package com.bbbang.luck.configuration.properties

import com.bbbang.luck.configuration.LuckConfiguration
import io.micronaut.context.annotation.ConfigurationProperties
import java.math.BigDecimal

@ConfigurationProperties("app.luck")
class LuckProperties: LuckConfiguration {
    private val defaultLimitBid = BigDecimal.valueOf(1000)
    private val defaultReservePrice = BigDecimal.valueOf(5)
    private val defaultOdds = BigDecimal.valueOf(1.8)
    private val defaultRedPackNumbers = 6
    private val defaultWithdrawalLimit = BigDecimal(10)
    //平台
    private val defaultWater = BigDecimal(4)
    private val defaultPlatformWater = BigDecimal(2.5)
    //合伙人
    private val defaultPartnerWater = BigDecimal(1.5)
    //上级代理
    private val defaultAgentWater = BigDecimal(1.5)
    private val defaultInviteRebate = BigDecimal(0.01)


    override var limitBid: BigDecimal=defaultLimitBid
    override var reservePrice: BigDecimal=defaultReservePrice
    override var withdrawalLimit: BigDecimal=defaultWithdrawalLimit
    override var odds: BigDecimal=defaultOdds
    override var water: BigDecimal=defaultWater
    override var platformWater: BigDecimal=defaultPlatformWater
    override var agentWater: BigDecimal=defaultAgentWater
    override var partnerWater: BigDecimal=defaultPartnerWater
    //邀请新用户-1美分
    override var inviteRebate: BigDecimal=defaultInviteRebate

    override var redPackNumbers:Int=defaultRedPackNumbers


    override lateinit var redPackUrl:String
    override lateinit var magic:String



}

