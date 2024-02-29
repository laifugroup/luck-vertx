package com.bbbang.luck.service;

import com.bbbang.luck.repository.LuckUserRepository
import com.bbbang.luck.domain.vo.LuckUserVO
import com.bbbang.luck.domain.bo.LuckUserBO
import com.bbbang.luck.domain.dto.LuckUserDTO
import com.bbbang.luck.domain.dto.LuckUserPageDTO
import com.bbbang.luck.domain.po.LuckGoodLuckPO
import com.bbbang.luck.domain.po.LuckUserPO
import com.bbbang.luck.mapper.LuckUserMapper
import com.bbbang.parent.service.impl.BaseServiceImpl
import jakarta.inject.Singleton
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


@Singleton
open class LuckUserService(private val repository: LuckUserRepository) 
:BaseServiceImpl<LuckUserDTO, LuckUserPageDTO, LuckUserBO, LuckUserPO, LuckUserVO>(repository, LuckUserMapper.MAPPER){

    fun findByBotUserId(botUserId: Long,groupId:Long?): Mono<LuckUserVO> {
        return repository.findByBotUserIdAndGroupId(botUserId,groupId!!).map {
            LuckUserMapper.MAPPER.po2vo(it)
        }
    }






    fun findByIdInList(idList: List<Long?>): Flux<LuckUserPO>{
        return  repository.findByIdInList(idList)
    }

    fun yesterdayUsers(yesterday:LocalDate,groupId: Long?):Mono<Int>{
        val startTime=LocalDateTime.of(yesterday, LocalTime.MIN)
        val endTime=LocalDateTime.of(yesterday, LocalTime.MAX)
        return repository.countIdByGroupIdAndCreatedAtBetween(groupId, startTime =startTime,endTime=endTime )
    }

}
