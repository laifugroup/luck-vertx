package com.bbbang.luck.repository;
import com.bbbang.luck.domain.po.LuckCreditLogPO
import com.bbbang.parent.repository.BaseReactorPageableRepository
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.annotation.RepositoryConfiguration
import io.micronaut.data.model.query.builder.jpa.JpaQueryBuilder
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDateTime

@Repository
@RepositoryConfiguration(queryBuilder = JpaQueryBuilder::class)
interface LuckCreditLogRepository: BaseReactorPageableRepository<LuckCreditLogPO>   {


   @Query("select COALESCE(sum(abs(c.credit) ), 0) from LuckCreditLogPO c where c.userId = :userId and c.type = :type and  c.createdAt  between :start and :end")
   fun findSumCreditByUserIdAndTypeAndCreatedAtBetween(userId: Long, type: Int,start:LocalDateTime,end:LocalDateTime): Mono<BigDecimal?>


   @Query("select COALESCE(sum(abs(c.credit) ), 0) from LuckCreditLogPO c where c.groupId = :groupId and c.type = :type and  c.createdAt  between :start and :end")
   fun findSumCreditByGroupIdAndTypeAndCreatedAtBetween(groupId: Long?, type: Int,start:LocalDateTime,end:LocalDateTime): Mono<BigDecimal?>

}

