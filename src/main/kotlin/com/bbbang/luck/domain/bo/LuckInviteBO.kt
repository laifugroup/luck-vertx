package com.bbbang.luck.domain.bo;

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
data class LuckInviteBO (
    @field:Schema(description = "用户ID[1541693333435453411]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  userId:Long?=null,
	
    @field:Schema(description = "博弈组ID[112333222]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  groupId:Long?=null,
	
    @field:Schema(description = "邀请链接[https://t.me/+33lIub2gs-44YTg1]",example = "https://t.me/+33lIub2gs-44YTg1")
    var  url:String?=null,
	
    @field:Schema(description = "邀请链接Hash[adea241183d46ecfbf829ba50165129b]",example = "adea241183d46ecfbf829ba50165129b")
    var  urlHash:String?=null,
	
    @field:Schema(description = "备注[封禁用户]",example = "封禁用户")
    var  remark:String?=null,
	
    @field:Schema(description = "状态[1-正常,2-封禁中]",example = "1")
    var  status:Int?=null,
	
    @field:Schema(description = "过期时间",type = "string" ,example = "2025-02-20 20:20:20",required=true)
    @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/shanghai")
    var  expiredAt:LocalDateTime?=null,
	
):BaseEntity() {
 
}
