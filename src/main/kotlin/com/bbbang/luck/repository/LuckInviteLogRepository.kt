package com.bbbang.luck.repository;
import com.bbbang.luck.domain.po.LuckInviteLogPO
import com.bbbang.parent.repository.BaseReactorPageableRepository
import io.micronaut.data.annotation.Repository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.annotation.RepositoryConfiguration
import io.micronaut.data.model.query.builder.jpa.JpaQueryBuilder
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Repository
@RepositoryConfiguration(queryBuilder = JpaQueryBuilder::class)
interface LuckInviteLogRepository: BaseReactorPageableRepository<LuckInviteLogPO>   {

    fun existsByInviteeUserId(inviteeUserId:Long?): Mono<Boolean>

    fun countByUserIdAndCreatedAtBetween(userId: Long, startDate: LocalDateTime, endDate: LocalDateTime):Mono<Int>


    fun countByUserId(userId: Long):Mono<Int>


    fun findByUserId(userId: Long,pageable: Pageable): Mono<Page<LuckInviteLogPO>>
}

