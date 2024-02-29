package com.bbbang.luck.repository;
import com.bbbang.luck.domain.po.LuckUserHourPO
import com.bbbang.luck.domain.po.LuckUserPO
import com.bbbang.luck.domain.po.LuckWalletPO
import com.bbbang.parent.repository.BaseReactorPageableRepository
import io.micronaut.core.annotation.NonNull
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.annotation.RepositoryConfiguration
import io.micronaut.data.model.query.builder.jpa.JpaQueryBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Repository
@RepositoryConfiguration(queryBuilder = JpaQueryBuilder::class)
interface LuckUserRepository: BaseReactorPageableRepository<LuckUserPO>   {

   fun findByBotUserIdAndGroupId(@NonNull botUserId: Long,groupId:Long): Mono<LuckUserPO>

   /**
    * 更新用户邀请人信息
    */
   fun updateInviterUserIdById(id:Long?,inviterUserId:Long?): Mono<Int?>

   /**
    * 变更角色信息
    */
   fun updateRolesById(id:Long?,roles:String?): Mono<Int?>


   fun findByIdInList(idList: List<Long?>): Flux<LuckUserPO>

   //用户分析
   //统计新增用户数
  // @Query("SELECT  COUNT(id) from LuckUserPO where createdAt between :startTime and :endTime")
   //
//   @Query("select hour(createdAt) as hours, count (id)  as counts from LuckUserPO   where createdAt between :startTime and :endTime group by  hours")
//   fun finByHour(startTime:LocalDateTime,endTime:LocalDateTime):Flux<LuckUserHourPO>


   fun countIdByGroupIdAndCreatedAtBetween(groupId: Long?,startTime:LocalDateTime,endTime:LocalDateTime):Mono<Int>

}

