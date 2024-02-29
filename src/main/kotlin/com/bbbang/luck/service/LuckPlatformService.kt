package com.bbbang.luck.service;

import com.bbbang.luck.repository.LuckPlatformRepository
import com.bbbang.luck.service.LuckPlatformService
import com.bbbang.luck.domain.vo.LuckPlatformVO
import com.bbbang.luck.domain.bo.LuckPlatformBO
import com.bbbang.luck.domain.dto.LuckPlatformDTO
import com.bbbang.luck.domain.dto.LuckPlatformPageDTO
import com.bbbang.luck.domain.po.LuckPlatformPO
import com.bbbang.luck.mapper.LuckPlatformMapper
import com.bbbang.parent.service.impl.BaseServiceImpl
import jakarta.inject.Inject
import jakarta.inject.Singleton
import reactor.core.publisher.Mono


@Singleton
open class LuckPlatformService(private val repository: LuckPlatformRepository) 
:BaseServiceImpl<LuckPlatformDTO, LuckPlatformPageDTO, LuckPlatformBO, LuckPlatformPO, LuckPlatformVO>(repository, LuckPlatformMapper.MAPPER){


    fun findByGroupId(groupId:Long?): Mono<LuckPlatformVO> {
        return repository.findByGroupId(groupId).map {
            LuckPlatformMapper.MAPPER.po2vo(it)
        }
    }
}
