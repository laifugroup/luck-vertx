//package com.bbbang.luck.interceptor
//
//
//import io.micronaut.http.HttpMethod
//import io.micronaut.http.MutableHttpRequest
//import io.micronaut.http.annotation.Body
//import io.micronaut.http.annotation.RequestFilter
//import io.micronaut.http.annotation.ServerFilter
//import io.micronaut.http.filter.FilterChain
//
///**
// *  @title 拦截telegram请求，然后附加验证对象
// */
//@ServerFilter("/luck/v1/bot/webhook/handleMessage/callback/*")
//class LuckBotWebhookWalletFilter() {
//
//
//    @RequestFilter
//     fun doFilter(request: MutableHttpRequest<*>,@Body requestBody: String) {
//         println("filter")
//        //,chain: FilterChain
////        if (request.method== HttpMethod.POST){
////            request.bearerAuth("")
////        }
////
////        val request22 = request.mutate().attribute(AUTHENTICATION_ATTR, auth);
////
////        val context = request.getAttribute("AUTHENTICATION_ATTR")
//       // chain.proceed(request)
//    }
//
//}