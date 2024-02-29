package com.bbbang.luck.api.bot.type

/**
 * bot接受的我消息类型
 */
enum class BotMessageType(val code:String, val desc: String) {
    PRIVATE("private","私密对话"),
    GROUP("group","群组消息"),
    CHANNEL("channel","订阅消息"),
    SUPERGROUP("supergroup","超级群组"),

}


