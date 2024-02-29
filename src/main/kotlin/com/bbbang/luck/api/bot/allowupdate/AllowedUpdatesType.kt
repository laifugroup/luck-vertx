package com.bbbang.luck.api.bot.allowupdate

enum class AllowedUpdatesType(val code:String, val desc: String) {
    MESSAGE("message","普通消息"),
    EDITED_MESSAGE("edited_message","编辑后的消息"),
    CHANNEL_POST("channel_post","频道消息"),
    EDITED_CHANNEL_POST("edited_channel_post","编辑后的频道消息"),
    INLINE_QUERY("inline_query","选择器查询"),
    CHOSE_INLINE_RESULT("chosen_inline_result","选择器结果"),
    CALLBACK_QUERY("callback_query","按键查询"),
    SHIPPING_QUERY("shipping_query","提交订单查询"),
    PRE_CHECKOUT_QUERY("pre_checkout_query","预付款查询"),
    POLL("poll","调查结果"),
    POLL_ANSWER("poll_answer","投票结果"),
    MY_CHAT_MEMBER("my_chat_member","成员已加入频道/组/超级组"),
    CHAT_MEMBER("chat_member","群组成员变更"),
    CHAT_JOIN_REQUEST("chat_join_request","加入群聊请求"),


}