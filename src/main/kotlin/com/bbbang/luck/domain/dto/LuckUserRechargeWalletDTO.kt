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
data class LuckUserRechargeWalletDTO(

    @field:Schema(description = "用户ID[1541693333435453411]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  userId:Long?=null,
	

    @field:Schema(description = "博弈组ID[112333222]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  groupId:Long?=null,
	

    @field:Schema(description = "用户钱包地址[TYMjeFcgSMdj1rXryzcBXiUpLwYmdVpcvh]",example = "TYMjeFcgSMdj1rXryzcBXiUpLwYmdVpcvh",required=true)
	@field:NotBlank(message = "[用户钱包地址]不能为空")
	@field:Pattern(regexp = "\\S*",message="[用户钱包地址]不符合规则")
    @field:Size(min=2, max = 64, message = "[用户钱包地址]长度范围2-64")
    var  address:String?=null,
	

    @field:Schema(description = "网络类型[1-TRC20,2-ERC20]",example = "1",required=true)
    var  type:Int?=null,
	
) {
 
}
