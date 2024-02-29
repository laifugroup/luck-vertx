package com.bbbang.luck.service;

import com.bbbang.luck.domain.bo.LuckInviteBO
import com.bbbang.luck.domain.dto.LuckInviteDTO
import com.bbbang.luck.domain.dto.LuckInvitePageDTO
import com.bbbang.luck.domain.po.LuckInvitePO
import com.bbbang.luck.domain.vo.LuckInviteVO
import com.bbbang.luck.mapper.LuckInviteMapper
import com.bbbang.luck.repository.LuckInviteRepository
import com.bbbang.parent.service.impl.BaseServiceImpl
import jakarta.inject.Singleton
import reactor.core.publisher.Mono


@Singleton
open class LuckInviteService(private val repository: LuckInviteRepository)
:BaseServiceImpl<LuckInviteDTO, LuckInvitePageDTO, LuckInviteBO, LuckInvitePO, LuckInviteVO>(repository, LuckInviteMapper.MAPPER){

    fun findByUserId(userId:Long):Mono<LuckInviteVO>{
        return repository.findByUserId(userId).map {
            LuckInviteMapper.MAPPER.po2vo(it)
        }
    }

    fun findByUrlHash(urlHash:String):Mono<LuckInviteVO>{
        return repository.findByUrlHash(urlHash).map {
            LuckInviteMapper.MAPPER.po2vo(it)
        }
    }

}
