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
data class LuckChargeLogDTO(

    @field:Schema(description = "用户ID[1541693333435453411]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  userId:Long?=null,
	

    @field:Schema(description = "类型[1-提现,2-充值]",example = "1",required=true)
    var  type:Int?=null,
	

    @field:Schema(description = "交易hash[0x7d107e0c7c5d148dae4b103bd04f9e1fb8998f5af6292afdfb58ea30fe581365]",example = "0x7d107e0c7c5d148dae4b103bd04f9e1fb8998f5af6292afdfb58ea30fe581365",required=true)
	@field:NotBlank(message = "[交易hash]不能为空")
	@field:Pattern(regexp = "\\S*",message="[交易hash]不符合规则")
    @field:Size(min=2, max = 64, message = "[交易hash]长度范围2-64")
    var  transactionHash:String?=null,
	

    @field:Schema(description = "交易状态[success]",example = "success",required=true)
	@field:NotBlank(message = "[交易状态]不能为空")
	@field:Pattern(regexp = "\\S*",message="[交易状态]不符合规则")
    @field:Size(min=2, max = 64, message = "[交易状态]长度范围2-64")
    var  transactionStatus:String?=null,
	

    @field:Schema(description = "交易区块高度[27504624]",example = "1",required=true)
    var  transactionBlock:Int?=null,
	

    @field:Schema(description = "交易区块高度[Apr-20-2023 05:26:43 AM +UTC]",type = "string" ,example = "2025-02-20 20:20:20",required=true)
    @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/shanghai")
    var  transactionTimestamp:LocalDateTime?=null,
	

    @field:Schema(description = "发起者[0x6ba1df44827dd28a6f571fa72cdedb9100281a9a]",example = "0x6ba1df44827dd28a6f571fa72cdedb9100281a9a",required=true)
	@field:NotBlank(message = "[发起者]不能为空")
	@field:Pattern(regexp = "\\S*",message="[发起者]不符合规则")
    @field:Size(min=2, max = 64, message = "[发起者]长度范围2-64")
    var  fromHash:String?=null,
	

    @field:Schema(description = "接收者[0x99174202841116fd7c5ca39c473c99fbe32f5c26]",example = "0x99174202841116fd7c5ca39c473c99fbe32f5c26",required=true)
	@field:NotBlank(message = "[接收者]不能为空")
	@field:Pattern(regexp = "\\S*",message="[接收者]不符合规则")
    @field:Size(min=2, max = 64, message = "[接收者]长度范围2-64")
    var  toHash:String?=null,
	

    @field:Schema(description = "合约[0x55d398326f99059ff775485246999027b3197955]",example = "0x55d398326f99059ff775485246999027b3197955",required=true)
	@field:NotBlank(message = "[合约]不能为空")
	@field:Pattern(regexp = "\\S*",message="[合约]不符合规则")
    @field:Size(min=2, max = 64, message = "[合约]长度范围2-64")
    var  contract:String?=null,
	

    @field:Schema(description = "交易数量[14555.88]",example = "1",required=true)
    var  tokens:BigDecimal?=null,
	

    @field:Schema(description = "原生代币数量[0]",example = "0",required=true)
	@field:NotBlank(message = "[原生代币数量]不能为空")
	@field:Pattern(regexp = "\\S*",message="[原生代币数量]不符合规则")
    @field:Size(min=2, max = 64, message = "[原生代币数量]长度范围2-64")
    var  value:String?=null,
	

    @field:Schema(description = "交易费用[0.000153345 BNB]",example = "0.000153345 BNB",required=true)
	@field:NotBlank(message = "[交易费用]不能为空")
	@field:Pattern(regexp = "\\S*",message="[交易费用]不符合规则")
    @field:Size(min=2, max = 64, message = "[交易费用]长度范围2-64")
    var  transactionFee:String?=null,
	

    @field:Schema(description = "gas费用[0.000000003 BNB]",example = "0.000000003 BNB",required=true)
	@field:NotBlank(message = "[gas费用]不能为空")
	@field:Pattern(regexp = "\\S*",message="[gas费用]不符合规则")
    @field:Size(min=2, max = 64, message = "[gas费用]长度范围2-64")
    var  gasPrice:String?=null,
	

    @field:Schema(description = "BNB价格[$318.04 / BNB]",example = "$318.04 / BNB",required=true)
	@field:NotBlank(message = "[BNB价格]不能为空")
	@field:Pattern(regexp = "\\S*",message="[BNB价格]不符合规则")
    @field:Size(min=2, max = 64, message = "[BNB价格]长度范围2-64")
    var  bnbPrice:String?=null,
	

    @field:Schema(description = "备注[交易失败,BNB不足]",example = "交易失败,BNB不足",required=true)
	@field:NotBlank(message = "[备注]不能为空")
	@field:Pattern(regexp = "\\S*",message="[备注]不符合规则")
    @field:Size(min=2, max = 64, message = "[备注]长度范围2-64")
    var  remark:String?=null,
	
) {
 
}
