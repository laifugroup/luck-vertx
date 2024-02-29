package com.bbbang.luck.service;

import com.bbbang.luck.repository.LuckActivityRepository
import com.bbbang.luck.service.LuckActivityService
import com.bbbang.luck.domain.vo.LuckActivityVO
import com.bbbang.luck.domain.bo.LuckActivityBO
import com.bbbang.luck.domain.dto.LuckActivityDTO
import com.bbbang.luck.domain.dto.LuckActivityPageDTO
import com.bbbang.luck.domain.po.LuckActivityPO
import com.bbbang.luck.domain.type.ActivityType
import com.bbbang.luck.mapper.LuckActivityMapper
import com.bbbang.parent.service.impl.BaseServiceImpl
import jakarta.inject.Inject
import jakarta.inject.Singleton
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal


@Singleton
open class LuckActivityService(private val repository: LuckActivityRepository) 
:BaseServiceImpl<LuckActivityDTO, LuckActivityPageDTO, LuckActivityBO, LuckActivityPO, LuckActivityVO>(repository, LuckActivityMapper.MAPPER){

    /**
     * 新用户活动
     */
    fun findByNewUserActivity(): Mono<LuckActivityPO> {
        val activityCode=ActivityType.NEW_USER_ACTIVITY.code
       return repository.findByActivityCodeOrderByIdDesc(activityCode).next().defaultIfEmpty(
           LuckActivityPO(activityCode, BigDecimal.ZERO,BigDecimal.ZERO)
       )
    }

}
