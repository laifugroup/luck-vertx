package com.bbbang.luck.api.bot.type



enum class CallBackActionsType(val code:String, val desc: String) {

    WITHDRAWAL("WITHDRAWAL","提现"),
    RECHARGE("RECHARGE","充值"),
    PLAY_RULE("PLAY_RULE","玩法"),
    BALANCE("BALANCE","余额"),

    INVITE_LINK("INVITE_LINK","推广链接"),
    INVITE_QUERY("INVITE_QUERY","推广查询"),
    WATER_REPORT("WATER_REPORT","反水统计"),
    GAME_REPORT("TODAY_REPORT","游戏报表"),

    FINANCE("FINANCE","财务"),
    CUSTOMER_SERVICE("CUSTOMER_SERVICE","客服"),


    GRAB_RED_PACK("GRAB_RED_PACK","抢红包"),

    //财务审核通过
    AUDIT_PASS("AUDIT_PASS","审核通过"),
    AUDIT_REJECT("AUDIT_REJECT","审核拒绝"),

}


enum class MyActionsType(val code:String, val desc: String) {
    BINDING_WALLET("BINDING_WALLET","绑定钱包"),


}


