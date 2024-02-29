package com.bbbang.luck.domain.type

/**
 * 扫雷系统-用户角色
 */
enum class LuckUserRoleType(val code:String, val message:String) {
    ADMIN("ADMIN","管理员"),
    USER("USER","用户"),
    FINANCE("FINANCE","财务人员"),
}