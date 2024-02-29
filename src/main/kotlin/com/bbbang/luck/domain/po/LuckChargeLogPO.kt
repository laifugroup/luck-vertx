package com.bbbang.luck.domain.po;

import com.bbbang.parent.entities.BaseEntity
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import io.micronaut.core.annotation.Introspected
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.Date
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.Comment
import com.bbbang.parent.entities.*
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column

@Entity
@Table(name = "luck_charge_log", schema = "\"luck\"")
@Comment("钱包充值记录")
@Introspected
@DynamicUpdate
@DynamicInsert
@AttributeOverride(name = "id", column =Column(name = "id"))
data class LuckChargeLogPO(
    @field:Schema(description = "用户ID[1541693333435453411]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("用户ID[1541693333435453411]")
    var  userId:Long?=null,
	
    @field:Schema(description = "类型[1-提现,2-充值]",example = "1")
    @field:Comment("类型[1-提现,2-充值]")
    var  type:Int?=null,
	
    @field:Schema(description = "交易hash[0x7d107e0c7c5d148dae4b103bd04f9e1fb8998f5af6292afdfb58ea30fe581365]",example = "0x7d107e0c7c5d148dae4b103bd04f9e1fb8998f5af6292afdfb58ea30fe581365")
    @field:Comment("交易hash[0x7d107e0c7c5d148dae4b103bd04f9e1fb8998f5af6292afdfb58ea30fe581365]")
    var  transactionHash:String?=null,
	
    @field:Schema(description = "交易状态[success]",example = "success")
    @field:Comment("交易状态[success]")
    var  transactionStatus:String?=null,
	
    @field:Schema(description = "交易区块高度[27504624]",example = "1")
    @field:Comment("交易区块高度[27504624]")
    var  transactionBlock:Int?=null,
	
    @field:Schema(description = "交易区块高度[Apr-20-2023 05:26:43 AM +UTC]",type = "string" ,example = "2025-02-20 20:20:20",required=true)
    @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/shanghai")
    @field:Comment("交易区块高度[Apr-20-2023 05:26:43 AM +UTC]")
    var  transactionTimestamp:LocalDateTime?=null,
	
    @field:Schema(description = "发起者[0x6ba1df44827dd28a6f571fa72cdedb9100281a9a]",example = "0x6ba1df44827dd28a6f571fa72cdedb9100281a9a")
    @field:Comment("发起者[0x6ba1df44827dd28a6f571fa72cdedb9100281a9a]")
    var  fromHash:String?=null,
	
    @field:Schema(description = "接收者[0x99174202841116fd7c5ca39c473c99fbe32f5c26]",example = "0x99174202841116fd7c5ca39c473c99fbe32f5c26")
    @field:Comment("接收者[0x99174202841116fd7c5ca39c473c99fbe32f5c26]")
    var  toHash:String?=null,
	
    @field:Schema(description = "合约[0x55d398326f99059ff775485246999027b3197955]",example = "0x55d398326f99059ff775485246999027b3197955")
    @field:Comment("合约[0x55d398326f99059ff775485246999027b3197955]")
    var  contract:String?=null,
	
    @field:Schema(description = "交易数量[14555.88]",example = "1")
    @field:Comment("交易数量[14555.88]")
    var  tokens:BigDecimal?=null,
	
    @field:Schema(description = "原生代币数量[0]",example = "0")
    @field:Comment("原生代币数量[0]")
    var  value:String?=null,
	
    @field:Schema(description = "交易费用[0.000153345 BNB]",example = "0.000153345 BNB")
    @field:Comment("交易费用[0.000153345 BNB]")
    var  transactionFee:String?=null,
	
    @field:Schema(description = "gas费用[0.000000003 BNB]",example = "0.000000003 BNB")
    @field:Comment("gas费用[0.000000003 BNB]")
    var  gasPrice:String?=null,
	
    @field:Schema(description = "BNB价格[$318.04 / BNB]",example = "$318.04 / BNB")
    @field:Comment("BNB价格[$318.04 / BNB]")
    var  bnbPrice:String?=null,
	
    @field:Schema(description = "备注[交易失败,BNB不足]",example = "交易失败,BNB不足")
    @field:Comment("备注[交易失败,BNB不足]")
    var  remark:String?=null,
	
) : BaseEntity() {
 
 
    override fun properties(): List<PropertyMetadata> {
        return  listOf(
				 PropertyMetadata("id", Long::class,this.id),
				 PropertyMetadata("userId", Long::class,this.userId),
				 PropertyMetadata("type", Int::class,this.type),
				 PropertyMetadata("transactionHash", String::class,this.transactionHash),
				 PropertyMetadata("transactionStatus", String::class,this.transactionStatus),
				 PropertyMetadata("transactionBlock", Int::class,this.transactionBlock),
				 PropertyMetadata("transactionTimestamp", LocalDateTime::class,this.transactionTimestamp),
				 PropertyMetadata("fromHash", String::class,this.fromHash),
				 PropertyMetadata("toHash", String::class,this.toHash),
				 PropertyMetadata("contract", String::class,this.contract),
				 PropertyMetadata("tokens", BigDecimal::class,this.tokens),
				 PropertyMetadata("value", String::class,this.value),
				 PropertyMetadata("transactionFee", String::class,this.transactionFee),
				 PropertyMetadata("gasPrice", String::class,this.gasPrice),
				 PropertyMetadata("bnbPrice", String::class,this.bnbPrice),
				 PropertyMetadata("remark", String::class,this.remark),
				 PropertyMetadata("createdAt", LocalDateTime::class,this.createdAt),
				 PropertyMetadata("updatedAt", LocalDateTime::class,this.updatedAt),
				 PropertyMetadata("deletedAt", LocalDateTime::class,this.deletedAt),
		        )
    }
	
}
