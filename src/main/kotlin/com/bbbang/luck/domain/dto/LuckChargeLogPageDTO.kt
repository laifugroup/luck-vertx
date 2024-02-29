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
data class LuckChargeLogPageDTO(
    @field:Schema(description = "id",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  id:Long?=null,
	
    @field:Schema(description = "用户ID[1541693333435453411]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  userId:Long?=null,
	
    @field:Schema(description = "类型[1-提现,2-充值]",example = "1")
    var  type:Int?=null,
	
    @field:Schema(description = "交易hash[0x7d107e0c7c5d148dae4b103bd04f9e1fb8998f5af6292afdfb58ea30fe581365]",example = "0x7d107e0c7c5d148dae4b103bd04f9e1fb8998f5af6292afdfb58ea30fe581365")
    var  transactionHash:String?=null,
	
    @field:Schema(description = "交易状态[success]",example = "success")
    var  transactionStatus:String?=null,
	
    @field:Schema(description = "交易区块高度[27504624]",example = "1")
    var  transactionBlock:Int?=null,
	
    @field:Schema(description = "交易区块高度[Apr-20-2023 05:26:43 AM +UTC]",type = "string" ,example = "2025-02-20 20:20:20",required=true)
    @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/shanghai")
    var  transactionTimestamp:LocalDateTime?=null,
	
    @field:Schema(description = "发起者[0x6ba1df44827dd28a6f571fa72cdedb9100281a9a]",example = "0x6ba1df44827dd28a6f571fa72cdedb9100281a9a")
    var  fromHash:String?=null,
	
    @field:Schema(description = "接收者[0x99174202841116fd7c5ca39c473c99fbe32f5c26]",example = "0x99174202841116fd7c5ca39c473c99fbe32f5c26")
    var  toHash:String?=null,
	
    @field:Schema(description = "合约[0x55d398326f99059ff775485246999027b3197955]",example = "0x55d398326f99059ff775485246999027b3197955")
    var  contract:String?=null,
	
    @field:Schema(description = "交易数量[14555.88]",example = "1")
    var  tokens:BigDecimal?=null,
	
    @field:Schema(description = "原生代币数量[0]",example = "0")
    var  value:String?=null,
	
    @field:Schema(description = "交易费用[0.000153345 BNB]",example = "0.000153345 BNB")
    var  transactionFee:String?=null,
	
    @field:Schema(description = "gas费用[0.000000003 BNB]",example = "0.000000003 BNB")
    var  gasPrice:String?=null,
	
    @field:Schema(description = "BNB价格[$318.04 / BNB]",example = "$318.04 / BNB")
    var  bnbPrice:String?=null,
	
    @field:Schema(description = "备注[交易失败,BNB不足]",example = "交易失败,BNB不足")
    var  remark:String?=null,
	
)  {
 
}
