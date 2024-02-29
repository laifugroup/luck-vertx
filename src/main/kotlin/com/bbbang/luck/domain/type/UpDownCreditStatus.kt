package com.bbbang.luck.domain.type

enum class UpDownCreditStatus(val code:Int, val message:String) {
    APPLY(1,"已申请"),
    PASS(2,"已通过"),
    TRADE_FAIL(3,"交易失败"),
    TRADE_SUCCESS(4,"交易成功"),
    REJECT(5,"已驳回"),

}