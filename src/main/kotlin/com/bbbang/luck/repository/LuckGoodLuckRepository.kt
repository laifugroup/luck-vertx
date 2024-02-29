package com.bbbang.luck.repository;
import com.bbbang.luck.domain.po.LuckGoodLuckPO
import com.bbbang.parent.repository.BaseReactorPageableRepository
import io.micronaut.data.annotation.Repository
import io.micronaut.data.annotation.RepositoryConfiguration
import io.micronaut.data.model.query.builder.jpa.JpaQueryBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
@RepositoryConfiguration(queryBuilder = JpaQueryBuilder::class)
interface LuckGoodLuckRepository: BaseReactorPageableRepository<LuckGoodLuckPO>   {

   fun existsByLuckRedPackIdAndUserId(luckRedPackId: Long?, userId: Long?): Mono<Boolean>

   fun countByLuckRedPackId(luckRedPackId: Long?): Mono<Int>

   fun findByLuckRedPackId(luckRedPackId: Long?): Flux<LuckGoodLuckPO>


}

