package com.bbbang.luck.domain.vo

import io.micronaut.core.annotation.Introspected
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal


@Schema()
@Introspected
data class ContentDataVO(val name:String,val x:List<String>,val y:List<BigDecimal>) {
}