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
@Table(name = "luck_user", schema = "\"luck\"")
@Comment("幸运用户")
@Introspected
@DynamicUpdate
@DynamicInsert
@AttributeOverride(name = "id", column =Column(name = "id"))
data class LuckUserPO(
    @field:Schema(description = "BOT用户ID[430713401]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("BOT用户ID[430713401]")
    var  botUserId:Long?=null,
	
    @field:Schema(description = "博弈组ID[-1001977552617]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("博弈组ID[-1001977552617]")
    var  groupId:Long?=null,
	
    @field:Schema(description = "角色[admin,,user]",example = "admin,,user")
    @field:Comment("角色[admin,,user]")
    var  roles:String?=null,
	
    @field:Schema(description = "名字[san]",example = "san")
    @field:Comment("名字[san]")
    var  firstName:String?=null,
	
    @field:Schema(description = "姓[zhang]",example = "zhang")
    @field:Comment("姓[zhang]")
    var  lastName:String?=null,
	
    @field:Schema(description = "用户名[laowang]",example = "laowang")
    @field:Comment("用户名[laowang]")
    var  userName:String?=null,
	
    @field:Schema(description = "备注[封禁用户]",example = "封禁用户")
    @field:Comment("备注[封禁用户]")
    var  remark:String?=null,
	
    @field:Schema(description = "状态[1-正常,2-封禁中]",example = "1")
    @field:Comment("状态[1-正常,2-封禁中]")
    var  status:Int?=null,
	
    @field:Schema(description = "邀请人用户Id[1541693333435453411]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("邀请人用户Id[1541693333435453411]")
    var  inviterUserId:Long?=null,
	
) : BaseEntity() {
 
 
    override fun properties(): List<PropertyMetadata> {
        return  listOf(
		 PropertyMetadata("id", Long::class,this.id),
		 PropertyMetadata("botUserId", Long::class,this.botUserId),
		 PropertyMetadata("groupId", Long::class,this.groupId),
		 PropertyMetadata("roles", String::class,this.roles),
		 PropertyMetadata("firstName", String::class,this.firstName),
		 PropertyMetadata("lastName", String::class,this.lastName),
		 PropertyMetadata("userName", String::class,this.userName),
		 PropertyMetadata("remark", String::class,this.remark),
		 PropertyMetadata("status", Int::class,this.status),
		 PropertyMetadata("inviterUserId", Long::class,this.inviterUserId),
		 PropertyMetadata("createdAt", LocalDateTime::class,this.createdAt),
		 PropertyMetadata("updatedAt", LocalDateTime::class,this.updatedAt),
		 PropertyMetadata("deletedAt", LocalDateTime::class,this.deletedAt),
        )
    }
	
}
