package com.bbbang.luck.event


class GrabRedPackEventFactory: com.lmax.disruptor.EventFactory<GrabRedPackEvent> {


    override fun newInstance(): GrabRedPackEvent {
        return  GrabRedPackEvent()
    }

}