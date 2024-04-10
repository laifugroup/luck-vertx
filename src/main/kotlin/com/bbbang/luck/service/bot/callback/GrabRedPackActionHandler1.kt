package com.bbbang.luck.service.bot.callback

import com.bbbang.luck.configuration.properties.LuckProperties
import com.bbbang.luck.configuration.properties.TronProperties
import com.bbbang.luck.event.DivideRedPackEvent
import com.bbbang.luck.event.GrabRedPackEvent
import com.bbbang.luck.service.LuckGoodLuckService
import com.bbbang.luck.service.LuckSendLuckService
import com.bbbang.luck.service.LuckWalletService
import com.bbbang.luck.service.wrapper.LuckUserServiceWrapper
import com.lmax.disruptor.EventTranslator
import com.lmax.disruptor.dsl.Disruptor
import io.micronaut.context.MessageSource
import jakarta.inject.Singleton
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.objects.CallbackQuery

@Singleton
class GrabRedPackActionHandler1(
    private val grabDisruptor: Disruptor<GrabRedPackEvent>
) {
    fun grabRedPackHandlerDisruptor(absSender: DefaultAbsSender, callbackQuery: CallbackQuery){
        grabDisruptor.publishEvent(EventTranslator<GrabRedPackEvent> { event, _ ->
            event.callbackQuery=callbackQuery
            event.absSender=absSender
        })
    }

}