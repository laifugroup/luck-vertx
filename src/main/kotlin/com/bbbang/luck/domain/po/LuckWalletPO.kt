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
@Table(name = "luck_wallet", schema = "\"luck\"")
@Comment("用户钱包")
@Introspected
@DynamicUpdate
@DynamicInsert
@AttributeOverride(name = "id", column =Column(name = "id"))
data class LuckWalletPO(
    @field:Schema(description = "用户ID[1541693333435453411]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("用户ID[1541693333435453411]")
    var  userId:Long?=null,
	
    @field:Schema(description = "群组ID[23334444]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("群组ID[23334444]")
    var  groupId:Long?=null,
	
    @field:Schema(description = "余额[100]",example = "1")
    @field:Comment("余额[100]")
    var  credit:BigDecimal?=null,
	
    @field:Schema(description = "状态[1-已启用,2-封存中,2-已废弃]",example = "1")
    @field:Comment("状态[1-已启用,2-封存中,2-已废弃]")
    var  status:Int?=null,
	
) : BaseEntity() {
 
 
    override fun properties(): List<PropertyMetadata> {
        return  listOf(
		 PropertyMetadata("id", Long::class,this.id),
		 PropertyMetadata("userId", Long::class,this.userId),
		 PropertyMetadata("groupId", Long::class,this.groupId),
		 PropertyMetadata("credit", BigDecimal::class,this.credit),
		 PropertyMetadata("status", Int::class,this.status),
		 PropertyMetadata("createdAt", LocalDateTime::class,this.createdAt),
		 PropertyMetadata("updatedAt", LocalDateTime::class,this.updatedAt),
		 PropertyMetadata("deletedAt", LocalDateTime::class,this.deletedAt),
        )
    }
	
}
