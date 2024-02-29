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
data class LuckUserVO(
    @field:Schema(description = "BOT用户ID[430713401]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  botUserId:Long?=null,
	
    @field:Schema(description = "博弈组ID[-1001977552617]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  groupId:Long?=null,
	
    @field:Schema(description = "角色[admin,,user]",example = "admin,,user",required=true)
    var  roles:String?=null,
	
    @field:Schema(description = "名字[san]",example = "san",required=true)
    var  firstName:String?=null,
	
    @field:Schema(description = "姓[zhang]",example = "zhang",required=true)
    var  lastName:String?=null,
	
    @field:Schema(description = "用户名[laowang]",example = "laowang",required=true)
    var  userName:String?=null,
	
    @field:Schema(description = "备注[封禁用户]",example = "封禁用户",required=true)
    var  remark:String?=null,
	
    @field:Schema(description = "状态[1-正常,2-封禁中]",example = "1",required=true)
    var  status:Int?=null,
	
    @field:Schema(description = "邀请人用户Id[1541693333435453411]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  inviterUserId:Long?=null,
	
) : BaseEntity() {
 
}
