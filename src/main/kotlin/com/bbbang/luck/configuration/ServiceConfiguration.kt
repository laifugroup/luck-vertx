package com.bbbang.luck.configuration


 interface ServiceConfiguration {
    /**
     * 客户服务
     */
    var customerService: String

    /**
     * 充值/提现/财务
     */
    val finance:String

    /**
     * 订阅服务
     */
    val playRule:String

    /**
     * 财务人员ID
     */

    val financeBotUserId:String





}