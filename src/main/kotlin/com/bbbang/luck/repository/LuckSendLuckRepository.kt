package com.bbbang.luck.repository;
import com.bbbang.luck.domain.po.LuckSendLuckPO
import com.bbbang.parent.repository.BaseReactorPageableRepository
import io.micronaut.data.annotation.Repository
import io.micronaut.data.annotation.RepositoryConfiguration
import io.micronaut.data.model.query.builder.jpa.JpaQueryBuilder

@Repository
@RepositoryConfiguration(queryBuilder = JpaQueryBuilder::class)
interface LuckSendLuckRepository: BaseReactorPageableRepository<LuckSendLuckPO>   {

   
}

