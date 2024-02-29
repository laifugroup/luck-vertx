//package com.bbbang.luck.service
//
//import com.bbbang.luck.domain.bo.UserAnalysisDailyBO
//import com.bbbang.luck.domain.bo.UserAnalysisHourBO
//import com.bbbang.luck.domain.po.LuckUserHourPO
//import com.bbbang.luck.domain.vo.UserAnalysisDailyRateVO
//import com.bbbang.luck.domain.vo.UserAnalysisDailyVO
//import com.bbbang.luck.domain.vo.UserAnalysisHourVO
//import com.bbbang.luck.repository.LuckUserRepository
//import jakarta.inject.Singleton
//import org.mapdb.Atomic
//import reactor.core.publisher.Flux
//import reactor.core.publisher.Mono
//import java.math.BigDecimal
//import java.time.LocalDate
//import java.time.LocalDateTime
//import java.time.LocalTime
//
//
//@Singleton
//class UserAnalysisService(private val luckUserRepository: LuckUserRepository,
//    private val userAnalysisDailyService: UserAnalysisDailyService,
//    private val userAnalysisHourService: UserAnalysisHourService) {
//    /**
//     * 日新增用户数量
//     */
//    fun dailyNewUsers():Mono<UserAnalysisDailyVO>{
//        val yesterday= LocalDate.now().minusDays(1)
//        val startTime = LocalDateTime.of(yesterday, LocalTime.MIN)
//        val endTime = LocalDateTime.of(yesterday, LocalTime.MAX)
//       return luckUserRepository.finByHour(startTime,endTime)
//           .defaultIfEmpty (LuckUserHourPO(0,0))
//           .map {
//               UserAnalysisHourBO(startTime,it.hours,it.counts)
//           }.collectList().flatMap {data->
//              // Flux.range(1,3).reduce(Integer::sum)
//               userAnalysisHourService.saveList(data).map { it?.counts }.reduce{
//                       x, y -> (x?:0) + (y?:0)
//               }.flatMap {
//                   userAnalysisDailyService.save(UserAnalysisDailyBO(startTime,it))
//               }
//           }
//    }
//
//
//
//
//
//}