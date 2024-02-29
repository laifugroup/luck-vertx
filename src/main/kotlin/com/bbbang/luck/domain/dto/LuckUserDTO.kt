package com.bbbang.luck.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import io.micronaut.core.annotation.Introspected
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import jakarta.validation.constraints.*
import java.math.BigDecimal

@Schema()
@Introspected
data class LuckUserDTO(

    @field:Schema(description = "BOT用户ID[430713401]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  botUserId:Long?=null,
	

    @field:Schema(description = "博弈组ID[-1001977552617]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  groupId:Long?=null,
	

    @field:Schema(description = "角色[admin,,user]",example = "admin,,user",required=true)
	@field:NotBlank(message = "[角色]不能为空")
	@field:Pattern(regexp = "\\S*",message="[角色]不符合规则")
    @field:Size(min=2, max = 64, message = "[角色]长度范围2-64")
    var  roles:String?=null,
	

    @field:Schema(description = "名字[san]",example = "san",required=true)
	@field:NotBlank(message = "[名字]不能为空")
	@field:Pattern(regexp = "\\S*",message="[名字]不符合规则")
    @field:Size(min=2, max = 64, message = "[名字]长度范围2-64")
    var  firstName:String?=null,
	

    @field:Schema(description = "姓[zhang]",example = "zhang",required=true)
	@field:NotBlank(message = "[姓]不能为空")
	@field:Pattern(regexp = "\\S*",message="[姓]不符合规则")
    @field:Size(min=2, max = 64, message = "[姓]长度范围2-64")
    var  lastName:String?=null,
	

    @field:Schema(description = "用户名[laowang]",example = "laowang",required=true)
	@field:NotBlank(message = "[用户名]不能为空")
	@field:Pattern(regexp = "\\S*",message="[用户名]不符合规则")
    @field:Size(min=2, max = 64, message = "[用户名]长度范围2-64")
    var  userName:String?=null,
	

    @field:Schema(description = "备注[封禁用户]",example = "封禁用户",required=true)
	@field:NotBlank(message = "[备注]不能为空")
	@field:Pattern(regexp = "\\S*",message="[备注]不符合规则")
    @field:Size(min=2, max = 64, message = "[备注]长度范围2-64")
    var  remark:String?=null,
	

    @field:Schema(description = "状态[1-正常,2-封禁中]",example = "1",required=true)
    var  status:Int?=null,
	

    @field:Schema(description = "邀请人用户Id[1541693333435453411]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  inviterUserId:Long?=null,
	
) {
 
}
