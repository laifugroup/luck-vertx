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
@Table(name = "luck_invite_log", schema = "\"luck\"")
@Comment("邀请记录")
@Introspected
@DynamicUpdate
@DynamicInsert
@AttributeOverride(name = "id", column =Column(name = "id"))
data class LuckInviteLogPO(
    @field:Schema(description = "用户ID[1541693333435453411]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("用户ID[1541693333435453411]")
    var  userId:Long?=null,
	
    @field:Schema(description = "邀请链接[https://t.me/+33lIub2gs-44YTg1]",example = "https://t.me/+33lIub2gs-44YTg1")
    @field:Comment("邀请链接[https://t.me/+33lIub2gs-44YTg1]")
    var  inviteUrl:String?=null,
	
    @field:Schema(description = "邀请链接Hash[adea241183d46ecfbf829ba50165129b]",example = "adea241183d46ecfbf829ba50165129b")
    @field:Comment("邀请链接Hash[adea241183d46ecfbf829ba50165129b]")
    var  urlHash:String?=null,
	
    @field:Schema(description = "博弈组ID[112333222]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("博弈组ID[112333222]")
    var  groupId:Long?=null,
	
    @field:Schema(description = "被邀请人用户Id[1541693333435453411]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("被邀请人用户Id[1541693333435453411]")
    var  inviteeUserId:Long?=null,
	
    @field:Schema(description = "备注[封禁用户]",example = "封禁用户")
    @field:Comment("备注[封禁用户]")
    var  remark:String?=null,
	
) : BaseEntity() {
 
 
    override fun properties(): List<PropertyMetadata> {
        return  listOf(
		 PropertyMetadata("id", Long::class,this.id),
		 PropertyMetadata("userId", Long::class,this.userId),
		 PropertyMetadata("inviteUrl", String::class,this.inviteUrl),
		 PropertyMetadata("urlHash", String::class,this.urlHash),
		 PropertyMetadata("groupId", Long::class,this.groupId),
		 PropertyMetadata("inviteeUserId", Long::class,this.inviteeUserId),
		 PropertyMetadata("remark", String::class,this.remark),
		 PropertyMetadata("createdAt", LocalDateTime::class,this.createdAt),
		 PropertyMetadata("updatedAt", LocalDateTime::class,this.updatedAt),
		 PropertyMetadata("deletedAt", LocalDateTime::class,this.deletedAt),
        )
    }
	
}
