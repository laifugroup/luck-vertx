package com.bbbang.luck.service;

import com.bbbang.luck.repository.LuckWalletRepository
import com.bbbang.luck.domain.vo.LuckWalletVO
import com.bbbang.luck.domain.bo.LuckWalletBO
import com.bbbang.luck.domain.dto.LuckWalletDTO
import com.bbbang.luck.domain.dto.LuckWalletPageDTO
import com.bbbang.luck.domain.po.LuckWalletPO
import com.bbbang.luck.domain.type.WalletStatus
import com.bbbang.luck.mapper.LuckWalletMapper
import com.bbbang.parent.service.impl.BaseServiceImpl
import io.micronaut.core.annotation.NonNull
import jakarta.inject.Singleton
import reactor.core.publisher.Mono
import java.math.BigDecimal


@Singleton
open class LuckWalletService(private val repository: LuckWalletRepository) 
:BaseServiceImpl<LuckWalletDTO, LuckWalletPageDTO, LuckWalletBO, LuckWalletPO, LuckWalletVO>(repository, LuckWalletMapper.MAPPER){


    fun findWalletByUserId(userId:Long?,groupId:Long?) : Mono<LuckWalletVO> {
     return repository.findByUserId(userId?:0)
         .switchIfEmpty(
             repository .save(LuckWalletPO().apply {
                 this.userId=userId
                 this.credit= BigDecimal.ZERO
                 this.status= WalletStatus.ENABLE.code
                 this.groupId=groupId
             })
         )
         .map { LuckWalletMapper.MAPPER.po2vo(it) }
    }

    fun findByUserIdInList(@NonNull userIdList: List<Long?>): Mono<List<LuckWalletPO>>{
        return repository.findByUserIdInList(userIdList).collectList()
    }

}
