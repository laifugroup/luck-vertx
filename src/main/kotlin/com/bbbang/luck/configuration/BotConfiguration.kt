package com.bbbang.luck.configuration


 interface BotConfiguration {
    /**
     * 创建者ID
     */
    var creatorId:String
    /**
     * 机器人编码
     */
    var username: String

    /**
     * 机器人token
     */
    val token:String

    /**
     * 机器人描述
     */
    val desc:String

}