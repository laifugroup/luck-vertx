package com.bbbang.luck

import com.bbbang.luck.api.bot.allowupdate.AllowedUpdatesType
import com.bbbang.luck.configuration.properties.BotWebHookProperties
import com.bbbang.luck.event.DivideRedPackEvent
import com.bbbang.luck.event.GrabEventHandler
import com.bbbang.luck.event.GrabRedPackEvent
import com.bbbang.luck.service.bot.bots.LuckWebhookCommandBot
import com.bbbang.luck.service.bot.callback.GrabRedPackActionHandler
import com.bbbang.parent.helper.LicenseHelper
import com.lmax.disruptor.dsl.Disruptor
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook
import kotlin.system.exitProcess

@Singleton
open class LuckApplicationStartupEventListener: ApplicationEventListener<ServerStartupEvent> {


    @Inject
    lateinit var luckWebhookCommandBot: LuckWebhookCommandBot
    @Inject
    lateinit var telegramBots: TelegramBotsApi
    @Inject
    lateinit var botWebHookProperties: BotWebHookProperties

    @Inject
    lateinit var disruptor: Disruptor<DivideRedPackEvent>
    @Inject
    lateinit var disruptorGrabRedPackEvent: Disruptor<GrabRedPackEvent>


    override fun onApplicationEvent(event: ServerStartupEvent?) {
        //开源版本屏蔽证书验证
//        try {
//            LicenseHelper.verifyLicenseStartUp()
//        }catch (e:Exception){
//            println("[证书]许可已到期或不正确,请向服务商获取新证书。")
//            exitProcess(-1)
//        }
        registerDisruptor()
        registerTelegram()
    }
    private fun registerTelegram(){
        try{
            val allowedUpdates=listOf(
                AllowedUpdatesType.CHAT_MEMBER.code
                , AllowedUpdatesType.MESSAGE.code
                , AllowedUpdatesType.EDITED_MESSAGE.code
                , AllowedUpdatesType.CALLBACK_QUERY.code
            )
            telegramBots.registerBot(luckWebhookCommandBot, SetWebhook(botWebHookProperties.url).apply {
                this.secretToken=botWebHookProperties.secretToken
                this.allowedUpdates= allowedUpdates
            })
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun registerDisruptor(){
        disruptor.start()
        disruptorGrabRedPackEvent.start()
    }


}