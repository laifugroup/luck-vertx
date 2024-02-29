package com.bbbang.luck.configuration


 interface BotWebHookConfiguration {

    val internalUrl:String


    val url:String


    /**
     * 机器人安全token
     */
    val secretToken:String


}