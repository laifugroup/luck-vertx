package com.bbbang.luck.domain.dto

import io.micronaut.core.annotation.Introspected
import io.swagger.v3.oas.annotations.media.Schema


@Schema()
@Introspected
data class PublicOpinionAnalysisDTO(val quota:String) {
}