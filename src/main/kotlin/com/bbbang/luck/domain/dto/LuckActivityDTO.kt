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
data class LuckActivityDTO(

    @field:Schema(description = "活动编码[RECHARGE]",example = "RECHARGE",required=true)
	@field:NotBlank(message = "[活动编码]不能为空")
	@field:Pattern(regexp = "\\S*",message="[活动编码]不符合规则")
    @field:Size(min=2, max = 64, message = "[活动编码]长度范围2-64")
    var  activityCode:String?=null,
	

    @field:Schema(description = "积分[100]",example = "1",required=true)
    var  credit:BigDecimal?=null,
	

    @field:Schema(description = "赠送[10]",example = "1",required=true)
    var  sendCredit:BigDecimal?=null,
	

    @field:Schema(description = "备注[充值100送10]",example = "充值100送10",required=true)
	@field:NotBlank(message = "[备注]不能为空")
	@field:Pattern(regexp = "\\S*",message="[备注]不符合规则")
    @field:Size(min=2, max = 64, message = "[备注]长度范围2-64")
    var  remark:String?=null,
	
) {
 
}
