package com.bbbang.luck.domain.bo;

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
data class WithdrawalLogBO (
    @field:Schema(description = "用户ID[1541693333435453411]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  userId:Long?=null,
	
    @field:Schema(description = "积分[100]",example = "1")
    var  credit:BigDecimal?=null,
	
    @field:Schema(description = "备注[驳回原因：未达到一倍流水]",example = "驳回原因：未达到一倍流水")
    var  remark:String?=null,
	
    @field:Schema(description = "类型[1-已申请,2-已通过,待交易,3-交易失败,4-交易成功,5-已驳回]",example = "1")
    var  status:Int?=null,
	
):BaseEntity() {
 
}
