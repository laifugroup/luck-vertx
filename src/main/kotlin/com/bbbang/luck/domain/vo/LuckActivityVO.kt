package com.bbbang.luck.domain.vo;

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
data class LuckActivityVO(
    @field:Schema(description = "活动编码[RECHARGE]",example = "RECHARGE",required=true)
    var  activityCode:String?=null,
	
    @field:Schema(description = "积分[100]",example = "1",required=true)
    var  credit:BigDecimal?=null,
	
    @field:Schema(description = "赠送[10]",example = "1",required=true)
    var  sendCredit:BigDecimal?=null,
	
    @field:Schema(description = "备注[充值100送10]",example = "充值100送10",required=true)
    var  remark:String?=null,
	
) : BaseEntity() {
 
}
