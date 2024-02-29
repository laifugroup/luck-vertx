package com.bbbang.luck.domain.vo;

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
data class LuckInviteLogVO(
    @field:Schema(description = "用户ID[1541693333435453411]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  userId:Long?=null,
	
    @field:Schema(description = "邀请链接[https://t.me/+33lIub2gs-44YTg1]",example = "https://t.me/+33lIub2gs-44YTg1",required=true)
    var  inviteUrl:String?=null,
	
    @field:Schema(description = "邀请链接Hash[adea241183d46ecfbf829ba50165129b]",example = "adea241183d46ecfbf829ba50165129b",required=true)
    var  urlHash:String?=null,
	
    @field:Schema(description = "博弈组ID[112333222]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  groupId:Long?=null,
	
    @field:Schema(description = "被邀请人用户Id[1541693333435453411]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  inviteeUserId:Long?=null,
	
    @field:Schema(description = "备注[封禁用户]",example = "封禁用户",required=true)
    var  remark:String?=null,
	
) : BaseEntity() {
 
}
