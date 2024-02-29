package com.bbbang.luck.domain.vo;

import com.bbbang.parent.entities.BaseEntity
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.BeanDescription
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import io.micronaut.core.annotation.Introspected
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.Date
import java.math.BigDecimal

@Schema()
@Introspected
data class LuckRoleVO(

    @field:Schema(description = "角色[user]",example = "user",required=true)
    var  code:String?=null,
	
    @field:Schema(description = "用户[user]",example = "用户",required=true)
    var  description:String?=null,
	

) : BaseEntity() {
 
}
