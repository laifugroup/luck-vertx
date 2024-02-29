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
data class LuckGoodLuckVO(
    @field:Schema(description = "红包ID[1541693333435453411]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  luckRedPackId:Long?=null,
	
    @field:Schema(description = "发红包人[1541693333435453411]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  sendRedPackUserId:Long?=null,
	
    @field:Schema(description = "用户ID[1541693333435453411]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  userId:Long?=null,
	
    @field:Schema(description = "博弈组ID[112333222]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  groupId:Long?=null,
	
    @field:Schema(description = "名字[san]",example = "san",required=true)
    var  firstName:String?=null,
	
    @field:Schema(description = "姓[zhang]",example = "zhang",required=true)
    var  lastName:String?=null,
	
    @field:Schema(description = "用户名[laowang]",example = "laowang",required=true)
    var  userName:String?=null,
	
    @field:Schema(description = "抢红包金额分[100]",example = "1",required=true)
    var  credit:BigDecimal?=null,
	
    @field:Schema(description = "抢红包个数[1]",example = "1",required=true)
    var  numbers:Int?=null,
	
    @field:Schema(description = "尾数[2]",example = "1",required=true)
    var  lastNumber:Int?=null,
	
    @field:Schema(description = "雷数[3]",example = "1",required=true)
    var  boomNumber:Int?=null,
	
) : BaseEntity() {
 
}
