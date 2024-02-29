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
@Table(name = "user_analysis_daily", schema = "\"luck\"")
@Comment("新增用户日分析")
@Introspected
@DynamicUpdate
@DynamicInsert
@AttributeOverride(name = "id", column =Column(name = "id"))
data class UserAnalysisDailyPO(
    @field:Schema(description = "分析时间",type = "string" ,example = "2025-02-20 20:20:20",required=true)
    @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/shanghai")
    @field:Comment("分析时间")
    var  analysisDate:LocalDateTime?=null,
	
    @field:Schema(description = "统计数字[10]",type="string",example = "1339468674200637453")
    @field:JsonSerialize(using = ToStringSerializer::class)
    @field:Comment("统计数字[10]")
    var  counts:Long?=null,
	
) : BaseEntity() {
 
 
    override fun properties(): List<PropertyMetadata> {
        return  listOf(
		 PropertyMetadata("id", Long::class,this.id),
		 PropertyMetadata("analysisDate", LocalDateTime::class,this.analysisDate),
		 PropertyMetadata("counts", Long::class,this.counts),
		 PropertyMetadata("createdAt", LocalDateTime::class,this.createdAt),
		 PropertyMetadata("updatedAt", LocalDateTime::class,this.updatedAt),
		 PropertyMetadata("deletedAt", LocalDateTime::class,this.deletedAt),
        )
    }
	
}
