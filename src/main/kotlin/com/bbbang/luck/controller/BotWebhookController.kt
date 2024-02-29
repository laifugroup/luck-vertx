package com.bbbang.luck.controller;

import com.bbbang.luck.domain.vo.LuckSendLuckVO
import com.bbbang.luck.event.DivideRedPackEvent
import com.bbbang.luck.service.bot.bots.LuckWebhookCommandBot
import com.bbbang.luck.service.bot.service.BotWebHookService
import com.bbbang.parent.entities.Rsp
import com.bbbang.parent.helper.AuthenticationHelper
import com.bbbang.parent.rule.SecurityRules
import com.lmax.disruptor.EventTranslator
import com.lmax.disruptor.dsl.Disruptor
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.async.annotation.SingleResult
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.validation.Validated
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.telegram.telegrambots.meta.api.objects.Update
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.authentication.ServerAuthentication
import io.micronaut.security.utils.SecurityService
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.generics.TelegramBot
import reactor.core.publisher.Mono
import java.math.BigDecimal

/**
 *
 * https://proxy.bbbang.ltd/luck/v1/bot/webhook/handleMessage/callback/luck888local_bot
 * 
 */
@Controller("/v1/bot/webhook")
@Tag(name = "botWebhook", description = "TG机器人WebHook")
@Introspected
@Validated
@Secured(value = [SecurityRules.IS_ANONYMOUS])
 class BotWebhookController(
   private val botWebHookService: BotWebHookService,
)  {

    @Operation(summary ="[新增]机器人webhook回调",
        parameters = [
            Parameter (
                name = "botName",
                `in` = ParameterIn.PATH,
                example = "testinvitelink123bot",
                required = true
            ),
            Parameter(
                name = "X-Telegram-Bot-Api-Secret-Token",
                `in` = ParameterIn.HEADER,
                example = "ceq0vd6p2ehd15jkxs62oedq55hu2qq8",
                required = true
            ),
            Parameter(
                name = "X-Real-Ip",
                `in` = ParameterIn.HEADER,
                example = "127.0.3",
                required = true
            ),
        ]
        )
    @Post("/handleMessage/callback/{botName}")
    @SingleResult
    @Secured(value = [SecurityRules.IS_ANONYMOUS])
    fun onUpdateReceived(
                           @PathVariable botName: String
                          ,httpRequest: HttpRequest<Update>
                          ,@Header("X-Telegram-Bot-Api-Secret-Token") secretToken: String
                          ,@Header("X-Real-Ip") realIp: String
    ):Mono<HttpResponse<Any>>{
        val body=httpRequest.body
        if (body.isEmpty){
            return Mono.just(HttpResponse.ok(Rsp.failed("[非法访问]停止攻击行为").block()))
        }

        //val authentication= ServerAuthentication("1111", listOf("user"), mapOf("userId" to "111111"))
        //securityService.authentication
        val result= botWebHookService.onWebhookUpdateReceived(botName,secretToken,realIp,body.get())
        return Mono.just(HttpResponse.ok(result))
    }

    //

    @Operation(summary ="[红包雨]", description = "1. 营销 2.开奖结果推送")
    @Get("/handleMessage/callback/{botName}")
    @SingleResult
    @Secured(value = [SecurityRules.IS_ANONYMOUS])
   fun rain(@PathVariable botName: String): Mono<Rsp<Any>> {
//        disruptor.publishEvent(EventTranslator<DivideRedPackEvent> { event, _ ->
//            event.oddsCredit= BigDecimal.ZERO
//            event.callbackQuery=null
//            event.sendRedPackVO= LuckSendLuckVO()
//        })
        return Rsp.success("connection ok")
    }



}

