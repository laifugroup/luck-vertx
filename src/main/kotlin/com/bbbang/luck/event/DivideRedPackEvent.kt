package com.bbbang.luck.event

import com.bbbang.luck.domain.vo.LuckSendLuckVO
import com.bbbang.luck.service.bot.bots.LuckWebhookCommandBot
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import java.math.BigDecimal

class DivideRedPackEvent() {
    var sendRedPackVO: LuckSendLuckVO?= null
    var callbackQuery: CallbackQuery?=null
    var oddsCredit:BigDecimal?=null
}