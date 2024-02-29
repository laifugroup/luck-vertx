package com.bbbang.luck.configuration


 interface TronConfiguration {
    /**
     * 访问URL
     */
    var url: String

    /**
     * tron访问key
     */
    val key:String

    /**
     * 合约地址
     */
    val contractAddress:String

    /**
     * 充值地址
     */
    val rechargeAddress:String


 }