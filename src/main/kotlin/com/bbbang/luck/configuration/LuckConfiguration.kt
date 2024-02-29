package com.bbbang.luck.configuration

import java.math.BigDecimal


 interface LuckConfiguration {


    /**
     * 限红 最高点
     */
    var limitBid: BigDecimal

    /**
     * 藏蓝 最低点
     */
    var reservePrice: BigDecimal

    /**
     * 提现最小金额
     */
    var withdrawalLimit:BigDecimal

    /**
     * 赔率
     */
    var odds: BigDecimal


    /**
     * 抽水点数
     */
    var water: BigDecimal


    /**
     * 平台抽水点数
     */
    var platformWater: BigDecimal



    /**
     * 上级代理抽水点数 实时到账
     */
    var agentWater: BigDecimal

    /**
     * 合伙人反水 每日结算
     */
    var partnerWater: BigDecimal



    /**
     * 邀请返利1美分
     */
    var inviteRebate: BigDecimal


    /**
     * 红包个数
     */
    val redPackNumbers:Int

    /**
     * 红包魔法
     */
    val magic:String

    /**
     * 红包图
     */
    val redPackUrl:String




}