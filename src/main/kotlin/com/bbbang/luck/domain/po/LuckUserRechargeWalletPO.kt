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
@Table(name = "luck_user_recharge_wallet", schema = "\"luck\"")
@Comment("用户充值钱包")
@Introspected
@DynamicUpdate
@DynamicInsert
@AttributeOverride(name = "id", column =Column(name = "id"))
data class LuckUserRechargeWalletPO(
    @field:Schema(description = "用户ID[1541693333435453411]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("用户ID[1541693333435453411]")
    var  userId:Long?=null,
	
    @field:Schema(description = "博弈组ID[112333222]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("博弈组ID[112333222]")
    var  groupId:Long?=null,
	
    @field:Schema(description = "用户钱包地址[TYMjeFcgSMdj1rXryzcBXiUpLwYmdVpcvh]",example = "TYMjeFcgSMdj1rXryzcBXiUpLwYmdVpcvh")
    @field:Comment("用户钱包地址[TYMjeFcgSMdj1rXryzcBXiUpLwYmdVpcvh]")
    var  address:String?=null,
	
    @field:Schema(description = "网络类型[1-TRC20,2-ERC20]",example = "1")
    @field:Comment("网络类型[1-TRC20,2-ERC20]")
    var  type:Int?=null,
	
) : BaseEntity() {
 
 
    override fun properties(): List<PropertyMetadata> {
        return  listOf(
		 PropertyMetadata("id", Long::class,this.id),
		 PropertyMetadata("userId", Long::class,this.userId),
		 PropertyMetadata("groupId", Long::class,this.groupId),
		 PropertyMetadata("address", String::class,this.address),
		 PropertyMetadata("type", Int::class,this.type),
		 PropertyMetadata("createdAt", LocalDateTime::class,this.createdAt),
		 PropertyMetadata("updatedAt", LocalDateTime::class,this.updatedAt),
		 PropertyMetadata("deletedAt", LocalDateTime::class,this.deletedAt),
        )
    }
	
}
