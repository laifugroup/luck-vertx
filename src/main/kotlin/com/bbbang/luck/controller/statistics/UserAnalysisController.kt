package com.bbbang.luck.controller.statistics;



import com.bbbang.luck.domain.dto.LuckCreditApplyAuditDTO
import com.bbbang.luck.domain.dto.LuckCreditApplyDTO
import com.bbbang.luck.domain.dto.LuckCreditApplyPageDTO
import com.bbbang.luck.domain.po.LuckUserHourPO
import com.bbbang.luck.domain.vo.ContentDataVO
import com.bbbang.luck.domain.vo.LuckCreditApplyVO
import com.bbbang.luck.domain.vo.UserAnalysisDailyVO
import com.bbbang.luck.domain.vo.UserAnalysisHourVO
import com.bbbang.luck.mapper.LuckCreditApplyMapper
import com.bbbang.luck.service.DashboardService
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


@Controller("/v1/user/analysis")
@Tag(name = "userAnalysis", description = "userAnalysis")
@Introspected
@Validated
@Secured(value = [SecurityRules.IS_AUTHENTICATED])
 class UserAnalysisController(
 ) : BaseController() {


    @Operation(summary ="[更新]日新增统计")
    @Get("/today")
    @SingleResult
    @Secured(value = [SecurityRules.IS_ANONYMOUS])
    fun today(): Mono<Rsp<UserAnalysisDailyVO>> {
        return Rsp.success(UserAnalysisDailyVO())
    }


}

