package com.bbbang.luck

import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.info.*
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.servers.Server

@OpenAPIDefinition(
    info = Info(
        title = "luck-vertx",
        version = "1.0.0",
        description = "幸运星接口文档",
        termsOfService = "https://localhost.com/2020/02/02/terms-of-service.html",
        license = License(name = "Private License 2.0", url = "=https://localhost.com/2020/02/02/license.html"),
        contact = Contact(name = "bbBang", url = "https://localhost.com/2020/02/02/contact.html", email = "contact@bbbang.com")
    ),
    servers =[
        Server(url = "http://localhost:1960", description ="Host"),
        Server(url = "http://lh.bbbang.com:1960", description ="Cors"),
        Server(url = "https://api.bbbang.ltd/luck", description ="Online"),
    ],
    security = [SecurityRequirement(name = "bearerAuth")]
)

object Api {
}
fun main(args: Array<String>) {
	run(*args)
}

