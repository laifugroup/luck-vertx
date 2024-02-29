package com.bbbang.luck.domain.dto;

import com.bbbang.parent.entities.BaseEntity
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import io.micronaut.core.annotation.Introspected
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.Date
import java.math.BigDecimal

@Schema()
@Introspected
data class LuckActivityPageDTO(
    @field:Schema(description = "id",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  id:Long?=null,
	
    @field:Schema(description = "活动编码[RECHARGE]",example = "RECHARGE")
    var  activityCode:String?=null,
	
    @field:Schema(description = "积分[100]",example = "1")
    var  credit:BigDecimal?=null,
	
    @field:Schema(description = "赠送[10]",example = "1")
    var  sendCredit:BigDecimal?=null,
	
    @field:Schema(description = "备注[充值100送10]",example = "充值100送10")
    var  remark:String?=null,
	
)  {
 
}
