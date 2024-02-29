package com.bbbang.luck.domain.vo;

import com.bbbang.parent.entities.BaseEntity
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import io.micronaut.core.annotation.Introspected
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.math.BigDecimal

@Schema()
@Introspected
data class ReportAnalysisDailyVO(
    @field:Schema(description = "分析时间",type = "string" ,example = "2025-02-20 20:20:20",required=true)
    @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/shanghai")
    var  analysisDate:LocalDateTime?=null,
	
    @field:Schema(description = "新增用户[10]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  userCounts:Int?=null,

    @field:Schema(description = "流水",example = "1365.12",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  gameCounts:BigDecimal?=null,


    @field:Schema(description = "中雷",example = "123.23",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  boomCounts:BigDecimal?=null,


    @field:Schema(description = "分润",example = "10.23",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  profitCounts:BigDecimal?=null,

) : BaseEntity() {
 
}
