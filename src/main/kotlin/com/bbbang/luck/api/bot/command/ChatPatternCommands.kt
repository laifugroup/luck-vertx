package com.bbbang.luck.api.bot.command

class ChatPatternCommands {
    companion object{
        //发送包
        const  val SEND_LUCK = "\\d+[-|/]\\d"
        //红包雨<免费福利> 没有余额的人不允许抢
        const  val SEND_LUCK_RAIN = "\\d+[-|/][Xx]"
        //查询余额
        const val BALANCE = "(?i)(ye|余额|查|yue)"
        const val UP_CREDIT = "上分"
        const val DOWN_CREDIT = "下分(\\d+(\\.\\d+)?)"

        const val MY = "\uD83D\uDC64个人中心"
        const val GAME = "\uD83E\uDDE7游戏"
        const val PARTNER = "\uD83D\uDD25合伙人"

    }
}