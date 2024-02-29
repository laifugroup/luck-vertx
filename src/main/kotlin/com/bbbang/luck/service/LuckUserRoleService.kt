package com.bbbang.luck.service

import com.bbbang.luck.domain.type.LuckUserRoleType
import com.bbbang.luck.domain.vo.LuckRoleVO
import jakarta.inject.Singleton
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Singleton
class LuckUserRoleService {

    fun getRoleList():Mono<List<LuckRoleVO>>{
      return  Flux.just(LuckUserRoleType.USER,LuckUserRoleType.ADMIN,LuckUserRoleType.FINANCE)
          .map {
              LuckRoleVO(it.code,it.message)
          }.collectList()
    }
}