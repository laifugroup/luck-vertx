package com.bbbang.luck.service

import com.bbbang.luck.domain.vo.ContentDataVO
import com.bbbang.luck.domain.vo.LuckUserVO
import jakarta.inject.Singleton
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDate


@Singleton
class DashboardService {

    fun findContentData(): Mono<List<ContentDataVO>> {
        val now = LocalDate.now()
        val result=ArrayList<ContentDataVO>()
//        for (index in 0 until 10 step 1){
//            result.add(ContentDataVO(now.plusDays(-index.toLong()).toString(), BigDecimal.valueOf(index*10.0)))
//        }
        return Mono.just(result)
    }


}