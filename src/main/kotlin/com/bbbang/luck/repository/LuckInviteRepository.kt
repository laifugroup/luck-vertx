package com.bbbang.luck.repository;
import com.bbbang.luck.domain.po.LuckInvitePO
import com.bbbang.parent.repository.BaseReactorPageableRepository
import io.micronaut.data.annotation.Repository
import io.micronaut.data.annotation.RepositoryConfiguration
import io.micronaut.data.model.query.builder.jpa.JpaQueryBuilder
import reactor.core.publisher.Mono

@Repository
@RepositoryConfiguration(queryBuilder = JpaQueryBuilder::class)
interface LuckInviteRepository: BaseReactorPageableRepository<LuckInvitePO>   {

   fun findByUserId(userId: Long): Mono<LuckInvitePO>

   fun findByUrlHash(urlHash: String): Mono<LuckInvitePO>


}

