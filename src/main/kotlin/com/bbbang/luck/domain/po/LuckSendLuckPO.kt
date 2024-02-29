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
@Table(name = "luck_send_luck", schema = "\"luck\"")
@Comment("发红包")
@Introspected
@DynamicUpdate
@DynamicInsert
@AttributeOverride(name = "id", column =Column(name = "id"))
data class LuckSendLuckPO(
    @field:Schema(description = "用户ID[1541693333435453411]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("用户ID[1541693333435453411]")
    var  userId:Long?=null,
	
    @field:Schema(description = "积分[100]",example = "1")
    @field:Comment("积分[100]")
    var  credit:BigDecimal?=null,
	
    @field:Schema(description = "红包个数[6]",example = "1")
    @field:Comment("红包个数[6]")
    var  redPackNumbers:Int?=null,
	
    @field:Schema(description = "中雷数字[1]",example = "1")
    @field:Comment("中雷数字[1]")
    var  boomNumber:Int?=null,
	
    @field:Schema(description = "博弈组ID[112333222]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("博弈组ID[112333222]")
    var  groupId:Long?=null,
	
    @field:Schema(description = "名字[san]",example = "san")
    @field:Comment("名字[san]")
    var  firstName:String?=null,
	
    @field:Schema(description = "姓[zhang]",example = "zhang")
    @field:Comment("姓[zhang]")
    var  lastName:String?=null,
	
    @field:Schema(description = "用户名[laowang]",example = "laowang")
    @field:Comment("用户名[laowang]")
    var  userName:String?=null,
	
    @field:Schema(description = "状态[1-已结算,2-未结算,3-已过期,4-已撤销]",example = "1")
    @field:Comment("状态[1-已结算,2-未结算,3-已过期,4-已撤销]")
    var  status:Int?=null,
	
) : BaseEntity() {
 
 
    override fun properties(): List<PropertyMetadata> {
        return  listOf(
				 PropertyMetadata("id", Long::class,this.id),
				 PropertyMetadata("userId", Long::class,this.userId),
				 PropertyMetadata("credit", BigDecimal::class,this.credit),
				 PropertyMetadata("redPackNumbers", Int::class,this.redPackNumbers),
				 PropertyMetadata("boomNumber", Int::class,this.boomNumber),
				 PropertyMetadata("groupId", Long::class,this.groupId),
				 PropertyMetadata("firstName", String::class,this.firstName),
				 PropertyMetadata("lastName", String::class,this.lastName),
				 PropertyMetadata("userName", String::class,this.userName),
				 PropertyMetadata("status", Int::class,this.status),
				 PropertyMetadata("createdAt", LocalDateTime::class,this.createdAt),
				 PropertyMetadata("updatedAt", LocalDateTime::class,this.updatedAt),
				 PropertyMetadata("deletedAt", LocalDateTime::class,this.deletedAt),
		        )
    }
	
}
