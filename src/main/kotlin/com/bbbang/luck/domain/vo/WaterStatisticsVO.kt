package com.bbbang.luck.domain.vo;

import com.bbbang.parent.entities.BaseEntity
import io.micronaut.core.annotation.Introspected
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal


@Schema()
@Introspected
data class WaterStatisticsVO(

    @field:Schema(description = "邀请返利",example = "1",required=true)
    var  inviteSum:BigDecimal?=null,

    @field:Schema(description = "下级中雷返点",example = "1",required=true)
    var  childBoomRebateSum:BigDecimal?=null,

) : BaseEntity() {
 
}
