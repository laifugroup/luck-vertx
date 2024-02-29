package com.bbbang.luck.domain.po;

import com.bbbang.parent.entities.BaseEntity
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import io.micronaut.core.annotation.Introspected
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.Date
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.Comment
import com.bbbang.parent.entities.*
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column

@Entity
@Table(name = "luck_credit_log", schema = "\"luck\"")
@Comment("上下分日志记录")
@Introspected
@DynamicUpdate
@DynamicInsert
@AttributeOverride(name = "id", column =Column(name = "id"))
data class LuckCreditLogPO(
    @field:Schema(description = "用户ID[1541693333435453411]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("用户ID[1541693333435453411]")
    var  userId:Long?=null,
	
    @field:Schema(description = "博弈组ID[-1001977552617]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("博弈组ID[-1001977552617]")
    var  groupId:Long?=null,
	
    @field:Schema(description = "之前积分[100]",example = "1")
    @field:Comment("之前积分[100]")
    var  creditBefore:BigDecimal?=null,
	
    @field:Schema(description = "积分[100]",example = "1")
    @field:Comment("积分[100]")
    var  credit:BigDecimal?=null,
	
    @field:Schema(description = "之后积分[200]",example = "1")
    @field:Comment("之后积分[200]")
    var  creditAfter:BigDecimal?=null,
	
    @field:Schema(description = "类型[1-充值上分,2-信用上分,3-抢到红包上分,4-奖励上分,5-中雷下分,6-提现下分,7-抢红包保证金下分,8-未中雷上分,9-调账]",example = "1")
    @field:Comment("类型[1-充值上分,2-信用上分,3-抢到红包上分,4-奖励上分,5-中雷下分,6-提现下分,7-抢红包保证金下分,8-未中雷上分,9-调账]")
    var  type:Int?=null,
	
    @field:Schema(description = "备注[封禁用户]",example = "封禁用户")
    @field:Comment("备注[封禁用户]")
    var  remark:String?=null,
	
) : BaseEntity() {
 
 
    override fun properties(): List<PropertyMetadata> {
        return  listOf(
		 PropertyMetadata("id", Long::class,this.id),
		 PropertyMetadata("userId", Long::class,this.userId),
		 PropertyMetadata("groupId", Long::class,this.groupId),
		 PropertyMetadata("creditBefore", BigDecimal::class,this.creditBefore),
		 PropertyMetadata("credit", BigDecimal::class,this.credit),
		 PropertyMetadata("creditAfter", BigDecimal::class,this.creditAfter),
		 PropertyMetadata("type", Int::class,this.type),
		 PropertyMetadata("remark", String::class,this.remark),
		 PropertyMetadata("createdAt", LocalDateTime::class,this.createdAt),
		 PropertyMetadata("updatedAt", LocalDateTime::class,this.updatedAt),
		 PropertyMetadata("deletedAt", LocalDateTime::class,this.deletedAt),
        )
    }
	
}
