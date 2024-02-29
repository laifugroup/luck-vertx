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
@Table(name = "luck_activity", schema = "\"luck\"")
@Comment("活动")
@Introspected
@DynamicUpdate
@DynamicInsert
@AttributeOverride(name = "id", column =Column(name = "id"))
data class LuckActivityPO(
    @field:Schema(description = "活动编码[RECHARGE]",example = "RECHARGE")
    @field:Comment("活动编码[RECHARGE]")
    var  activityCode:String?=null,
	
    @field:Schema(description = "积分[100]",example = "1")
    @field:Comment("积分[100]")
    var  credit:BigDecimal?=null,
	
    @field:Schema(description = "赠送[10]",example = "1")
    @field:Comment("赠送[10]")
    var  sendCredit:BigDecimal?=null,
	
    @field:Schema(description = "备注[充值100送10]",example = "充值100送10")
    @field:Comment("备注[充值100送10]")
    var  remark:String?=null,
	
) : BaseEntity() {
 
 
    override fun properties(): List<PropertyMetadata> {
        return  listOf(
		 PropertyMetadata("id", Long::class,this.id),
		 PropertyMetadata("activityCode", String::class,this.activityCode),
		 PropertyMetadata("credit", BigDecimal::class,this.credit),
		 PropertyMetadata("sendCredit", BigDecimal::class,this.sendCredit),
		 PropertyMetadata("remark", String::class,this.remark),
		 PropertyMetadata("createdAt", LocalDateTime::class,this.createdAt),
		 PropertyMetadata("updatedAt", LocalDateTime::class,this.updatedAt),
		 PropertyMetadata("deletedAt", LocalDateTime::class,this.deletedAt),
        )
    }
	
}
