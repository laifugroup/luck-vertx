package com.bbbang.luck.domain.vo;

import com.bbbang.parent.entities.BaseEntity
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import io.micronaut.core.annotation.Introspected
import io.swagger.v3.oas.annotations.media.Schema


@Schema()
@Introspected
data class UserInviteStatisticsVO(
    @field:Schema(description = "当日邀请数[1]",type="String",example = "1",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  currentDay:Int?=null,
	
    @field:Schema(description = "当月邀请数[22]",type="22",example = "22",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  currentMonth:Int?=null,
	
    @field:Schema(description = "累计邀请数[1000]",example = "1000",required=true)
    var  total:Int?=null,

    @field:Schema(description = "最近10条",required=true)
    var  inviteLogList:List<LuckInviteLogVO>?=null,

) : BaseEntity() {
 
}
