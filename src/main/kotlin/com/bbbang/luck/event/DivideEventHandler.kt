package com.bbbang.luck.event

import com.bbbang.luck.service.bot.service.DivideRedPackService
import com.lmax.disruptor.EventHandler
import jakarta.inject.Singleton


@Singleton
class DivideEventHandler(private val  divideRedPackService: DivideRedPackService): EventHandler<DivideRedPackEvent> {


    override fun onEvent(event: DivideRedPackEvent?, sequence: Long, endOfBatch: Boolean) {
        divideRedPackService.divideRedPack(event)
    }


}