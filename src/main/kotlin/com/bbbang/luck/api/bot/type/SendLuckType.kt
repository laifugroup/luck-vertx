package com.bbbang.luck.api.bot.type



enum class SendLuckType(val code:Int, val desc: String) {
    UNSETTLED(1,"未结算"),
    TERMED(2,"已结算"),
    EXPIRED(3,"已过期"),
    CANCEL(4,"已撤销"),
}


