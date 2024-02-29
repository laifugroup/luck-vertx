package com.bbbang.luck.domain.dto;

import com.bbbang.parent.entities.BaseEntity
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import io.micronaut.core.annotation.Introspected
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.Date
import java.math.BigDecimal

@Schema()
@Introspected
data class LuckBotPageDTO(
    @field:Schema(description = "id",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  id:Long?=null,
	
    @field:Schema(description = "机器人ID[223345455]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  botId:Long?=null,
	
    @field:Schema(description = "机器人名称[红包助手]",example = "红包助手")
    var  name:String?=null,
	
    @field:Schema(description = "机器人编码[RedPackBoomBot]",example = "RedPackBoomBot")
    var  code:String?=null,
	
    @field:Schema(description = "机器人密钥[6372507883:AAGZVdhtJdkkesfc2h_zlSgROyXl6a9NcRo]",example = "6372507883:AAGZVdhtJdkkesfc2h_zlSgROyXl6a9NcRo")
    var  token:String?=null,
	
    @field:Schema(description = "机器人描述[自动开奖红包机器人]",example = "自动开奖红包机器人")
    var  description:String?=null,
	
    @field:Schema(description = "状态[1-启用中,2-未启用]",example = "1")
    var  status:Int?=null,
	
    @field:Schema(description = "截止时间[2099-02-20 20:20:20]",type = "string" ,example = "2025-02-20 20:20:20",required=true)
    @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/shanghai")
    var  expiredAt:LocalDateTime?=null,
	
)  {
 
}
