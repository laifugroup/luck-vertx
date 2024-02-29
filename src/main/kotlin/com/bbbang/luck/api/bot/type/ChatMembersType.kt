package com.bbbang.luck.api.bot.type



enum class ChatMembersType(val code:String, val desc: String) {
    MEMBER("member","新的用户成为群组普通成员"),
    ADMINISTRATOR("administrator","新的用户成为群组管理员"),
    LEFT("left","一个用户离开/退出了群组"),
    KICKED("kicked","一个用户被踢出了群组"),

}


