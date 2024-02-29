package com.bbbang.luck.service;

import com.bbbang.luck.repository.LuckUserRechargeWalletRepository
import com.bbbang.luck.service.LuckUserRechargeWalletService
import com.bbbang.luck.domain.vo.LuckUserRechargeWalletVO
import com.bbbang.luck.domain.bo.LuckUserRechargeWalletBO
import com.bbbang.luck.domain.dto.LuckUserRechargeWalletDTO
import com.bbbang.luck.domain.dto.LuckUserRechargeWalletPageDTO
import com.bbbang.luck.domain.po.LuckUserRechargeWalletPO
import com.bbbang.luck.domain.type.UserRechargeWalletType
import com.bbbang.luck.mapper.LuckUserRechargeWalletMapper
import com.bbbang.parent.service.impl.BaseServiceImpl
import jakarta.inject.Inject
import jakarta.inject.Singleton
import reactor.core.publisher.Mono


@Singleton
open class LuckUserRechargeWalletService(private val repository: LuckUserRechargeWalletRepository) 
:BaseServiceImpl<LuckUserRechargeWalletDTO, LuckUserRechargeWalletPageDTO, LuckUserRechargeWalletBO, LuckUserRechargeWalletPO, LuckUserRechargeWalletVO>(repository, LuckUserRechargeWalletMapper.MAPPER){


    fun findByUserId(userId:Long?,groupId:Long?): Mono<LuckUserRechargeWalletVO> {
        return repository.findByUserId(userId).switchIfEmpty(
            repository.save(LuckUserRechargeWalletPO().apply {
                this.userId=userId
                this.groupId=groupId
                this.type= UserRechargeWalletType.TRC20.code
            })
        ).map {
            LuckUserRechargeWalletMapper.MAPPER.po2vo(it)
        }
    }

}
