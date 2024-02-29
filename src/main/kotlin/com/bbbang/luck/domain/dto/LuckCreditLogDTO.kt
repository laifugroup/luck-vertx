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
data class LuckCreditLogDTO(

    @field:Schema(description = "用户ID[1541693333435453411]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  userId:Long?=null,
	

    @field:Schema(description = "博弈组ID[-1001977552617]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  groupId:Long?=null,
	

    @field:Schema(description = "之前积分[100]",example = "1",required=true)
    var  creditBefore:BigDecimal?=null,
	

    @field:Schema(description = "积分[100]",example = "1",required=true)
    var  credit:BigDecimal?=null,
	

    @field:Schema(description = "之后积分[200]",example = "1",required=true)
    var  creditAfter:BigDecimal?=null,
	

    @field:Schema(description = "类型[1-充值上分,2-信用上分,3-抢到红包上分,4-奖励上分,5-中雷下分,6-提现下分,7-抢红包保证金下分,8-未中雷上分,9-调账]",example = "1",required=true)
    var  type:Int?=null,
	

    @field:Schema(description = "备注[封禁用户]",example = "封禁用户",required=true)
	@field:NotBlank(message = "[备注]不能为空")
	@field:Pattern(regexp = "\\S*",message="[备注]不符合规则")
    @field:Size(min=2, max = 64, message = "[备注]长度范围2-64")
    var  remark:String?=null,
	
) {
 
}
