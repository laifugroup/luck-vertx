package com.bbbang.luck.api.bot.type


enum class CreditLogType(val code:Int, val desc: String) {
    //RECHARGE(1,"充值"),
    CREDITED(2,"信用上分"),
    GRAB_RED_PACK(3,"抢到红包"),
    //SECURITY_DEPOSIT(4,"抢红包保证金(结束后退回账户)"),
    //SECURITY_DEPOSIT_BACK(5,"保证金退回"),
    REWARD(6,"奖励"),

    SEND_RED_PACK(7,"发红包"),
    BOOM(8,"玩家中雷赔付"),
    COMPENSATION(9,"收到中雷赔付"),
    ADJUSTMENT_DOWN(10,"调账(扣除)"),
    ADJUSTMENT_UP(11,"调账(增加)"),
    PLATFORM_WATER(12,"我发包平台抽水2.5%"),
    AGENT_WATER(13,"我发包代理抽水1.5%"),

    CHILD_BOOM_REBATE(14,"下级中雷返利1.5%"),
    CREDIT_UP(15,"充值上分"),
    CREDIT_DOWN(16,"提现下分"),
    ACTIVITY(17,"活动奖励"),
    INVITE(18,"邀请返利0.1U"),

    CREDIT_DOWN_REJECT(19,"下分不通过"),
    CREDIT_UP_PASS(20,"上分通过"),

}


