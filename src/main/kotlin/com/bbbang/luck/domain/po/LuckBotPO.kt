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
@Table(name = "luck_bot", schema = "\"luck\"")
@Comment("Luck机器人")
@Introspected
@DynamicUpdate
@DynamicInsert
@AttributeOverride(name = "id", column =Column(name = "id"))
data class LuckBotPO(
    @field:Schema(description = "机器人ID[223345455]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("机器人ID[223345455]")
    var  botId:Long?=null,
	
    @field:Schema(description = "机器人名称[红包助手]",example = "红包助手")
    @field:Comment("机器人名称[红包助手]")
    var  name:String?=null,
	
    @field:Schema(description = "机器人编码[RedPackBoomBot]",example = "RedPackBoomBot")
    @field:Comment("机器人编码[RedPackBoomBot]")
    var  code:String?=null,
	
    @field:Schema(description = "机器人密钥[6372507883:AAGZVdhtJdkkesfc2h_zlSgROyXl6a9NcRo]",example = "6372507883:AAGZVdhtJdkkesfc2h_zlSgROyXl6a9NcRo")
    @field:Comment("机器人密钥[6372507883:AAGZVdhtJdkkesfc2h_zlSgROyXl6a9NcRo]")
    var  token:String?=null,
	
    @field:Schema(description = "机器人描述[自动开奖红包机器人]",example = "自动开奖红包机器人")
    @field:Comment("机器人描述[自动开奖红包机器人]")
    var  description:String?=null,
	
    @field:Schema(description = "状态[1-启用中,2-未启用]",example = "1")
    @field:Comment("状态[1-启用中,2-未启用]")
    var  status:Int?=null,
	
    @field:Schema(description = "截止时间[2099-02-20 20:20:20]",type = "string" ,example = "2025-02-20 20:20:20",required=true)
    @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/shanghai")
    @field:Comment("截止时间[2099-02-20 20:20:20]")
    var  expiredAt:LocalDateTime?=null,
	
) : BaseEntity() {
 
 
    override fun properties(): List<PropertyMetadata> {
        return  listOf(
				 PropertyMetadata("id", Long::class,this.id),
				 PropertyMetadata("botId", Long::class,this.botId),
				 PropertyMetadata("name", String::class,this.name),
				 PropertyMetadata("code", String::class,this.code),
				 PropertyMetadata("token", String::class,this.token),
				 PropertyMetadata("description", String::class,this.description),
				 PropertyMetadata("status", Int::class,this.status),
				 PropertyMetadata("expiredAt", LocalDateTime::class,this.expiredAt),
				 PropertyMetadata("createdAt", LocalDateTime::class,this.createdAt),
				 PropertyMetadata("updatedAt", LocalDateTime::class,this.updatedAt),
				 PropertyMetadata("deletedAt", LocalDateTime::class,this.deletedAt),
		        )
    }
	
}
