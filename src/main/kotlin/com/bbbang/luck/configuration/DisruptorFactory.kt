package com.bbbang.luck.configuration

import com.bbbang.luck.event.DivideEventHandler
import com.bbbang.luck.event.DivideRedPackEvent
import com.bbbang.luck.event.DivideRedPackEventFactory
import com.bbbang.luck.service.bot.bots.LuckWebhookCommandBot
import com.bbbang.luck.service.bot.service.DivideRedPackService
import com.lmax.disruptor.dsl.Disruptor
import com.lmax.disruptor.util.DaemonThreadFactory
import io.micronaut.context.annotation.Factory
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Factory
class DisruptorFactory {


    @Singleton
    fun disruptorFactory(divideEventHandler:DivideEventHandler):Disruptor<DivideRedPackEvent>{
        val bufferSize = 1024
        val disruptor = Disruptor<DivideRedPackEvent>(DivideRedPackEventFactory(), bufferSize, DaemonThreadFactory.INSTANCE)
        disruptor.handleEventsWith(divideEventHandler)
        return disruptor
    }

}