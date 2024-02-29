package com.bbbang.luck.configuration

import com.bbbang.luck.configuration.properties.BotWebHookProperties
import com.bbbang.luck.api.bot.allowupdate.AllowedUpdatesType
import com.bbbang.luck.api.bot.webhook.LuckDefaultWebhook
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.env.Environment
import jakarta.inject.Singleton
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import javax.inject.Named

@Factory
class TelegramBotsConfiguration {


//    @Bean
//    @Singleton
//    @Named("default")
//    fun telegramBots(): TelegramBotsApi {
//        return TelegramBotsApi(DefaultBotSession::class.java)
//    }


    @Bean
    @Singleton
    @Named("webhook")
    fun telegramBotsWebHook(defaultWebhook: LuckDefaultWebhook): TelegramBotsApi {
        return TelegramBotsApi(DefaultBotSession::class.java, defaultWebhook)
    }


    @Bean
    @Singleton
    fun defaultWebhook(botWebHookProperties: BotWebHookProperties): LuckDefaultWebhook {
        val defaultBotOptions=  LuckDefaultWebhook()
            defaultBotOptions .setInternalUrl(botWebHookProperties.internalUrl)
        return  defaultBotOptions
    }

    /**
     * message - 普通消息
     * edited_message - 编辑后的消息
     * channel_post - 频道消息
     * edited_channel_post - 编辑后的频道消息
     * inline_query - 选择器查询
     * chosen_inline_result - 选择器结果
     * callback_query - 按键查询
     * shipping_query - 提交订单查询
     * pre_checkout_query - 预付款查询
     * poll - 调查结果
     * poll_answer - 投票结果
     * my_chat_member - 成员已加入频道/组/超级组
     * chat_member - 群组成员变更
     * chat_join_request - 加入群聊请求
     *
     */
    @Bean
    @Singleton
    fun defaultBotOptions( env: Environment): DefaultBotOptions {
        val defaultBotOptions=if (env.activeNames.contains("prod")) DefaultBotOptions() else DefaultBotOptions().apply {
            this.proxyHost="127.0.0.1"
            this.proxyPort=33210
            this.proxyType= DefaultBotOptions.ProxyType.HTTP
        }
        //允许消息更新
        val allowedUpdates=listOf(AllowedUpdatesType.CHAT_MEMBER.code
            ,AllowedUpdatesType.MESSAGE.code
            , AllowedUpdatesType.EDITED_MESSAGE.code
            ,AllowedUpdatesType.CALLBACK_QUERY.code
        )
        defaultBotOptions.allowedUpdates=allowedUpdates
        return defaultBotOptions
    }

//    @Bean
//    @Singleton
//    fun defaultAbsSender(luckLongPollingCommandBot: LuckLongPollingCommandBot): DefaultAbsSender  {
//        return luckLongPollingCommandBot as DefaultAbsSender
//    }





}