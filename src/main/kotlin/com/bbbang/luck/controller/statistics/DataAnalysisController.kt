package com.bbbang.luck.controller.statistics;



import com.bbbang.luck.domain.dto.*
import com.bbbang.luck.domain.vo.*
import com.bbbang.luck.mapper.LuckCreditApplyMapper
import com.bbbang.luck.service.DashboardService
import com.bbbang.luck.service.DataAnalysisService
import com.bbbang.luck.service.LuckCreditApplyService
import com.bbbang.parent.controller.BaseController
import com.bbbang.parent.entities.PageableDTO
import com.bbbang.parent.entities.Rsp
import com.bbbang.parent.entities.RspPagination
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.async.annotation.SingleResult
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.validation.Validated
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import reactor.core.publisher.Mono
import io.micronaut.core.annotation.Nullable
import io.micronaut.security.authentication.Authentication
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import com.bbbang.parent.rule.SecurityRules


@Controller("/v1/dataAnalysis")
@Tag(name = "dataAnalysis", description = "dataAnalysis")
@Introspected
@Validated
@Secured(value = [SecurityRules.IS_AUTHENTICATED])
 class DataAnalysisController(
  private val dataAnalysisService: DataAnalysisService
 ) : BaseController() {


    @Operation(summary ="[统计]内容发布比例")
    @Get("/api/content-publish")
    @SingleResult
    @Secured(value = [SecurityRules.IS_ADMIN])
    fun contentData(): Mono<Rsp<List<ContentDataVO>>> {
        val result=dataAnalysisService.findContentData()
        return Rsp.success(result)
    }


    @Operation(summary ="[统计]热门作者榜单")
    @Get("/api/popular-author/list")
    @SingleResult
    @Secured(value = [SecurityRules.IS_ADMIN])
    fun popularAuthor(): Mono<Rsp<PopularAuthorWraperVO>> {
        val result = dataAnalysisService.popularAuthor()
        return Rsp.success(result)
    }


    @Operation(summary ="[统计]内容时段分析")
    @Post("/api/content-period-analysis")
    @SingleResult
    @Secured(value = [SecurityRules.IS_ADMIN])
    fun contentPeriodAnalysis(): Mono<Rsp<PopularAuthorWraperVO>> {
        val result = dataAnalysisService.popularAuthor()
        return Rsp.success(result)
    }


    @Operation(summary ="[统计]舆情分析")
    @Post("/api/public-opinion-analysis")
    @SingleResult
    @Secured(value = [SecurityRules.IS_ADMIN])
    fun publicOpinionAnalysis(@Valid @Body publicOpinionAnalysisDTO: PublicOpinionAnalysisDTO): Mono<Rsp<Any>> {
        val result = dataAnalysisService.publicOpinionAnalysis(publicOpinionAnalysisDTO)
        return Rsp.success(result)
    }




}

