package com.bbbang.luck.service;

import com.bbbang.luck.domain.vo.LuckInviteLogVO
import com.bbbang.luck.domain.bo.LuckInviteLogBO
import com.bbbang.luck.domain.dto.LuckInviteLogDTO
import com.bbbang.luck.domain.dto.LuckInviteLogPageDTO
import com.bbbang.luck.domain.po.LuckInviteLogPO
import com.bbbang.luck.domain.vo.UserInviteStatisticsVO
import com.bbbang.luck.mapper.LuckInviteLogMapper
import com.bbbang.luck.repository.LuckInviteLogRepository
import com.bbbang.parent.service.impl.BaseServiceImpl
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.model.Sort
import jakarta.inject.Singleton
import reactor.core.publisher.Mono
import java.time.LocalDate


@Singleton
open class LuckInviteLogService(private val repository: LuckInviteLogRepository)
:BaseServiceImpl<LuckInviteLogDTO, LuckInviteLogPageDTO, LuckInviteLogBO, LuckInviteLogPO, LuckInviteLogVO>(repository, LuckInviteLogMapper.MAPPER){

    fun existsByInviteeUserId(inviteeUserId:Long?):Mono<Boolean>{
        return repository.existsByInviteeUserId(inviteeUserId)
    }

     fun findUserInviteStatistics(userId: Long): Mono<UserInviteStatisticsVO> {
        //日合计
        val todayStart = LocalDate.now().atStartOfDay()
        val todayEnd = todayStart.plusDays(1)

        val countTodayMono = repository.countByUserIdAndCreatedAtBetween(userId, todayStart, todayEnd)
        //月合计
        val monthStart = LocalDate.now().withDayOfMonth(1).atStartOfDay()
        val monthEnd = monthStart.plusMonths(1)
        val countMonthMono = repository.countByUserIdAndCreatedAtBetween(userId, monthStart, monthEnd)
        //合计
        val totalMono=repository.countByUserId(userId)
        val top10Mono = getTop10(userId)

        return Mono.zip(countTodayMono, countMonthMono,totalMono,top10Mono)
            .map { tuple ->
                val countToday = tuple.t1
                val countMonth = tuple.t2
                val total = tuple.t3
                val top10=tuple.t4
                UserInviteStatisticsVO(
                    currentDay = countToday,
                    currentMonth = countMonth,
                    total = total,
                    inviteLogList = LuckInviteLogMapper.MAPPER.po2vo(top10.content)
                )
            }
    }


    private fun getTop10(userId: Long): Mono<Page<LuckInviteLogPO>> {
        val sort= Sort.of(Sort.Order.desc("id"))
        val pageable= Pageable.from(0,10,sort)
        return repository.findByUserId(userId, pageable)
    }


}
