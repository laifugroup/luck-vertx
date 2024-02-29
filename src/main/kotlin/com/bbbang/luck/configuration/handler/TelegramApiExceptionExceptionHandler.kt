package com.bbbang.luck.configuration.handler

import com.bbbang.parent.entities.Rsp
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

@Singleton
@Requires(classes = [TelegramApiException::class ])
 class TelegramApiExceptionExceptionHandler : ExceptionHandler<TelegramApiException, MutableHttpResponse<Rsp<Any?>>>  {

    override fun handle(request: HttpRequest<Any>, exception: TelegramApiException): MutableHttpResponse<Rsp<Any?>> {
        exception.printStackTrace()
        val rsp = Rsp<Any?>(code =HttpStatus.INTERNAL_SERVER_ERROR.code , message = exception.message?:exception.localizedMessage, data = null)
        return HttpResponse.status<Rsp<Any?>>(HttpStatus.OK).body(rsp)
    }
}