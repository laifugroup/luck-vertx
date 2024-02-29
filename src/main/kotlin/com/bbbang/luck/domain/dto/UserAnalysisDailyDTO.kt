package com.bbbang.luck.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import io.micronaut.core.annotation.Introspected
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import jakarta.validation.constraints.*
import java.math.BigDecimal

@Schema()
@Introspected
data class UserAnalysisDailyDTO(

    @field:Schema(description = "分析时间",type = "string" ,example = "2025-02-20 20:20:20",required=true)
    @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/shanghai")
    var  analysisDate:LocalDateTime?=null,
	

    @field:Schema(description = "统计数字[10]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  counts:Long?=null,
	
) {
 
}
