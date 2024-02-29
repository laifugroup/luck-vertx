package com.bbbang.luck.domain.vo;

import com.bbbang.parent.entities.BaseEntity
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import io.micronaut.core.annotation.Introspected
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.math.BigInteger


@Schema()
@Introspected
data class DaysStatisticsVO(
    @field:Schema(description = "发包支出",example = "1",required=true)
    var  sendSum:BigDecimal?=null,

    @field:Schema(description = "发包盈利",example = "1",required=true)
    var  compensationSum:BigDecimal?=null,


    @field:Schema(description = "抢包收入",example = "1",required=true)
    var  grabSum:BigDecimal?=null,

    @field:Schema(description = "中雷赔付",example = "1",required=true)
    var  boomSum:BigDecimal?=null,


    ) : BaseEntity() {
 
}
