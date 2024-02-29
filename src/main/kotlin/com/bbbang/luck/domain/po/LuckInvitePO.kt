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
@Table(name = "luck_invite", schema = "\"luck\"")
@Comment("邀请链接")
@Introspected
@DynamicUpdate
@DynamicInsert
@AttributeOverride(name = "id", column =Column(name = "id"))
data class LuckInvitePO(
    @field:Schema(description = "用户ID[1541693333435453411]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("用户ID[1541693333435453411]")
    var  userId:Long?=null,
	
    @field:Schema(description = "博弈组ID[112333222]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("博弈组ID[112333222]")
    var  groupId:Long?=null,
	
    @field:Schema(description = "邀请链接[https://t.me/+33lIub2gs-44YTg1]",example = "https://t.me/+33lIub2gs-44YTg1")
    @field:Comment("邀请链接[https://t.me/+33lIub2gs-44YTg1]")
    var  url:String?=null,
	
    @field:Schema(description = "邀请链接Hash[adea241183d46ecfbf829ba50165129b]",example = "adea241183d46ecfbf829ba50165129b")
    @field:Comment("邀请链接Hash[adea241183d46ecfbf829ba50165129b]")
    var  urlHash:String?=null,
	
    @field:Schema(description = "备注[封禁用户]",example = "封禁用户")
    @field:Comment("备注[封禁用户]")
    var  remark:String?=null,
	
    @field:Schema(description = "状态[1-正常,2-封禁中]",example = "1")
    @field:Comment("状态[1-正常,2-封禁中]")
    var  status:Int?=null,
	
    @field:Schema(description = "过期时间",type = "string" ,example = "2025-02-20 20:20:20",required=true)
    @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/shanghai")
    @field:Comment("过期时间")
    var  expiredAt:LocalDateTime?=null,
	
) : BaseEntity() {
 
 
    override fun properties(): List<PropertyMetadata> {
        return  listOf(
				 PropertyMetadata("id", Long::class,this.id),
				 PropertyMetadata("userId", Long::class,this.userId),
				 PropertyMetadata("groupId", Long::class,this.groupId),
				 PropertyMetadata("url", String::class,this.url),
				 PropertyMetadata("urlHash", String::class,this.urlHash),
				 PropertyMetadata("remark", String::class,this.remark),
				 PropertyMetadata("status", Int::class,this.status),
				 PropertyMetadata("expiredAt", LocalDateTime::class,this.expiredAt),
				 PropertyMetadata("createdAt", LocalDateTime::class,this.createdAt),
				 PropertyMetadata("updatedAt", LocalDateTime::class,this.updatedAt),
				 PropertyMetadata("deletedAt", LocalDateTime::class,this.deletedAt),
		        )
    }
	
}
