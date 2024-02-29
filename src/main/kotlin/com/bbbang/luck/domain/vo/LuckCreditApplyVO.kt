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
data class LuckCreditApplyVO(
    @field:Schema(description = "用户ID[1541693333435453411]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  userId:Long?=null,
	
    @field:Schema(description = "博弈组ID[-1001977552617]",type="string",example = "1339468674200637453",required=true)
    @field:JsonSerialize(using = ToStringSerializer::class)
    var  groupId:Long?=null,
	
    @field:Schema(description = "积分[100]",example = "1",required=true)
    var  credit:BigDecimal?=null,
	
    @field:Schema(description = "来源地址[TTG2XCjhtzdCXgKCytVjyJmfkk3ypBVZEG]",example = "TTG2XCjhtzdCXgKCytVjyJmfkk3ypBVZEG",required=true)
    var  fromAddress:String?=null,
	
    @field:Schema(description = "去处地址[TYMjeFcgSMdj1rXryzcBXiUpLwYmdVpcvh]",example = "TYMjeFcgSMdj1rXryzcBXiUpLwYmdVpcvh",required=true)
    var  toAddress:String?=null,
	
    @field:Schema(description = "交易HASH[fd6c5f19be50fdbe45bab2f6117da4c2aa89042fa5306ae9e879c3645edc8549]",example = "fd6c5f19be50fdbe45bab2f6117da4c2aa89042fa5306ae9e879c3645edc8549",required=true)
    var  txnHash:String?=null,
	
    @field:Schema(description = "备注[驳回原因：未达到一倍流水]",example = "驳回原因：未达到一倍流水",required=true)
    var  remark:String?=null,
	
    @field:Schema(description = "类型[1-上分,2-下分]",example = "1",required=true)
    var  type:Int?=null,
	
    @field:Schema(description = "类型[1-已申请,2-已通过,待交易,3-交易失败,4-交易成功,5-已驳回]",example = "1",required=true)
    var  status:Int?=null,
	
) : BaseEntity() {
 
}
