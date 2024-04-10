package com.bbbang.luck.event

import com.bbbang.luck.service.bot.callback.GrabRedPackActionHandler
import com.bbbang.luck.service.bot.service.DivideRedPackService
import com.lmax.disruptor.EventHandler
import jakarta.inject.Singleton


@Singleton
class GrabEventHandler(private val  grabRedPackActionHandler: GrabRedPackActionHandler): EventHandler<GrabRedPackEvent> {

    override fun onEvent(event: GrabRedPackEvent, sequence: Long, endOfBatch: Boolean) {
        event?.let {
            grabRedPackActionHandler.grabRedPackHandler(it.absSender,it.callbackQuery)
        }
    }


}