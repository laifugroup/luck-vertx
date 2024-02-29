package com.bbbang.luck.api.bot.type



/**
 * bot指令
 */
enum class CommandsType(val code:String, val desc: String) {
    //不在指令窗口出现
    INIT("/init","初始化系统"),
    //JOIN_GROUP(Commands.JOIN_GROUP,"加入群组"),
    CLEAN("/clean","清除指令"),
    REPORT("/report","群组报表"),

    //需要在指令窗口出现
    START("/start","开始"),
    HELP("/help","帮助"),
    INVITE("/invite","邀请"),
    PLAY_RULE("/wanfa","游戏规则"),
    PARTNER("/partner","合伙人"),


}


