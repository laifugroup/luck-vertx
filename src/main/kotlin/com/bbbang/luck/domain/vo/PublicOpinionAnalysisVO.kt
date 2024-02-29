package com.bbbang.luck.domain.vo

import io.micronaut.core.annotation.Introspected
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal



@Schema()
@Introspected
data class PublicOpinionAnalysisVO2(val count:BigDecimal, val growth:BigDecimal, val chartData:List<ChartDataRecordShare>) {
}

@Schema()
@Introspected
data class PublicOpinionAnalysisVO(val count:BigDecimal, val growth:BigDecimal, val chartData:List<ChartDataRecord>) {
}


@Schema()
@Introspected
data class ChartDataRecord(val x:String, val y:BigDecimal, val name:Int) {
}


@Schema()
@Introspected
data class ChartDataRecordShare(val name:String, val value:BigDecimal, val itemStyle:ItemStyle) {
}

@Schema()
@Introspected
data class ItemStyle(val color:String) {
}

//{ name: '文本类', value: 25, itemStyle: { color: '#8D4EDA' } }



