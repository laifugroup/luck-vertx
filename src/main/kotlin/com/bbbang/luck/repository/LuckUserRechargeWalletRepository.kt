package com.bbbang.luck.repository;
import com.bbbang.luck.domain.po.LuckUserRechargeWalletPO
import com.bbbang.parent.repository.BaseReactorPageableRepository
import io.micronaut.core.annotation.NonNull
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.Repository
import io.micronaut.data.annotation.Where
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.model.Sort
import io.micronaut.data.annotation.RepositoryConfiguration
import io.micronaut.data.model.query.builder.jpa.JpaQueryBuilder
import reactor.core.publisher.Mono

@Repository
@RepositoryConfiguration(queryBuilder = JpaQueryBuilder::class)
interface LuckUserRechargeWalletRepository: BaseReactorPageableRepository<LuckUserRechargeWalletPO>   {

   fun findByUserId(userId:Long?): Mono<LuckUserRechargeWalletPO>

}

