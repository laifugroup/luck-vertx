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
@Table(name = "luck_credit_apply", schema = "\"luck\"")
@Comment("上下分申请记录")
@Introspected
@DynamicUpdate
@DynamicInsert
@AttributeOverride(name = "id", column =Column(name = "id"))
data class LuckCreditApplyPO(
    @field:Schema(description = "用户ID[1541693333435453411]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("用户ID[1541693333435453411]")
    var  userId:Long?=null,
	
    @field:Schema(description = "博弈组ID[-1001977552617]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("博弈组ID[-1001977552617]")
    var  groupId:Long?=null,
	
    @field:Schema(description = "积分[100]",example = "1")
    @field:Comment("积分[100]")
    var  credit:BigDecimal?=null,
	
    @field:Schema(description = "来源地址[TTG2XCjhtzdCXgKCytVjyJmfkk3ypBVZEG]",example = "TTG2XCjhtzdCXgKCytVjyJmfkk3ypBVZEG")
    @field:Comment("来源地址[TTG2XCjhtzdCXgKCytVjyJmfkk3ypBVZEG]")
    var  fromAddress:String?=null,
	
    @field:Schema(description = "去处地址[TYMjeFcgSMdj1rXryzcBXiUpLwYmdVpcvh]",example = "TYMjeFcgSMdj1rXryzcBXiUpLwYmdVpcvh")
    @field:Comment("去处地址[TYMjeFcgSMdj1rXryzcBXiUpLwYmdVpcvh]")
    var  toAddress:String?=null,
	
    @field:Schema(description = "交易HASH[fd6c5f19be50fdbe45bab2f6117da4c2aa89042fa5306ae9e879c3645edc8549]",example = "fd6c5f19be50fdbe45bab2f6117da4c2aa89042fa5306ae9e879c3645edc8549")
    @field:Comment("交易HASH[fd6c5f19be50fdbe45bab2f6117da4c2aa89042fa5306ae9e879c3645edc8549]")
    var  txnHash:String?=null,
	
    @field:Schema(description = "备注[驳回原因：未达到一倍流水]",example = "驳回原因：未达到一倍流水")
    @field:Comment("备注[驳回原因：未达到一倍流水]")
    var  remark:String?=null,
	
    @field:Schema(description = "类型[1-上分,2-下分]",example = "1")
    @field:Comment("类型[1-上分,2-下分]")
    var  type:Int?=null,
	
    @field:Schema(description = "类型[1-已申请,2-已通过,待交易,3-交易失败,4-交易成功,5-已驳回]",example = "1")
    @field:Comment("类型[1-已申请,2-已通过,待交易,3-交易失败,4-交易成功,5-已驳回]")
    var  status:Int?=null,
	
) : BaseEntity() {
 
 
    override fun properties(): List<PropertyMetadata> {
        return  listOf(
				 PropertyMetadata("id", Long::class,this.id),
				 PropertyMetadata("userId", Long::class,this.userId),
				 PropertyMetadata("groupId", Long::class,this.groupId),
				 PropertyMetadata("credit", BigDecimal::class,this.credit),
				 PropertyMetadata("fromAddress", String::class,this.fromAddress),
				 PropertyMetadata("toAddress", String::class,this.toAddress),
				 PropertyMetadata("txnHash", String::class,this.txnHash),
				 PropertyMetadata("remark", String::class,this.remark),
				 PropertyMetadata("type", Int::class,this.type),
				 PropertyMetadata("status", Int::class,this.status),
				 PropertyMetadata("createdAt", LocalDateTime::class,this.createdAt),
				 PropertyMetadata("updatedAt", LocalDateTime::class,this.updatedAt),
				 PropertyMetadata("deletedAt", LocalDateTime::class,this.deletedAt),
		        )
    }
	
}
