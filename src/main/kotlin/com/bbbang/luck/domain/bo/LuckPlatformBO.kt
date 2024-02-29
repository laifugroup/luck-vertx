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
data class LuckPlatformBO (
    @field:Schema(description = "博弈组ID[112333222]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  groupId:Long?=null,
	
    @field:Schema(description = "博弈组名称[众彩国际]",example = "众彩国际")
    var  groupName:String?=null,
	
    @field:Schema(description = "管理员用户ID[334454555656]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  adminBotUserId:Long?=null,
	
    @field:Schema(description = "状态[1-启用中,2-未启用]",example = "1")
    var  status:Int?=null,
	
):BaseEntity() {
 
}
