package com.bbbang.luck.domain.type

/**
 * 活动类型
 */
enum class ActivityType(val code:String, val message:String) {
    FIRST_RECHARGE_ACTIVITY("FIRST_RECHARGE","首充100送10"),
    RECHARGE_ACTIVITY("RECHARGE","充值送:冲500送50"),
    NEW_USER_ACTIVITY("NEW_USER","邀请新用户送5"),
}