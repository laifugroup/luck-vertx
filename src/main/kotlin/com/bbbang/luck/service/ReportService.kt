package com.bbbang.luck.service

import com.bbbang.luck.domain.vo.ReportAnalysisDailyVO
import jakarta.inject.Singleton
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Singleton
class ReportService(private val luckUserService: LuckUserService,
    private val luckCreditLogService: LuckCreditLogService
    ) {

    fun getGroupReport(groupId:Long?): Mono<ReportAnalysisDailyVO> {
        val yesterday=LocalDate.now().minusDays(1)
        //用户
        val user= luckUserService.yesterdayUsers(yesterday,groupId)
        val agentDaysStatistics=  luckCreditLogService.findGameStatisticsByGroupId(yesterday,groupId)

        return Mono.zip(user,agentDaysStatistics).map {
            val  userCounts=it.t1
            val agentDaysStatistics=it.t2
            ReportAnalysisDailyVO(analysisDate = LocalDateTime.of(yesterday, LocalTime.MIN),
                userCounts = userCounts,
                gameCounts=agentDaysStatistics.sendSum?:BigDecimal.ZERO,
                boomCounts = agentDaysStatistics.boomSum?: BigDecimal.ZERO,
                profitCounts =agentDaysStatistics.agentWaterSum?: BigDecimal.ZERO
            )
        }
    }

}