package com.bbbang.luck.event


class DivideRedPackEventFactory: com.lmax.disruptor.EventFactory<DivideRedPackEvent> {


    override fun newInstance(): DivideRedPackEvent {
        return  DivideRedPackEvent()
    }

}