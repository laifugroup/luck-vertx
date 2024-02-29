package com.bbbang.luck.service;

import com.bbbang.luck.api.bot.type.CreditLogType
import com.bbbang.luck.repository.LuckCreditApplyRepository
import com.bbbang.luck.service.LuckCreditApplyService
import com.bbbang.luck.domain.vo.LuckCreditApplyVO
import com.bbbang.luck.domain.bo.LuckCreditApplyBO
import com.bbbang.luck.domain.dto.LuckCreditApplyAuditDTO
import com.bbbang.luck.domain.dto.LuckCreditApplyDTO
import com.bbbang.luck.domain.dto.LuckCreditApplyPageDTO
import com.bbbang.luck.domain.po.LuckCreditApplyPO
import com.bbbang.luck.domain.po.LuckCreditLogPO
import com.bbbang.luck.domain.type.UpDownCreditStatus
import com.bbbang.luck.domain.type.UpDownCreditType
import com.bbbang.luck.mapper.LuckCreditApplyMapper
import com.bbbang.parent.exception.BusinessException
import com.bbbang.parent.service.impl.BaseServiceImpl
import com.bbbang.parent.utils.BigDecimalUtils
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.hibernate.SessionFactory
import org.hibernate.reactive.stage.Stage
import reactor.core.publisher.Mono
import java.math.BigDecimal


@Singleton
open class LuckCreditApplyService(private val repository: LuckCreditApplyRepository,
                                  private val luckWalletService: LuckWalletService,
                                  sessionFactory: SessionFactory
                                )
:BaseServiceImpl<LuckCreditApplyDTO, LuckCreditApplyPageDTO, LuckCreditApplyBO, LuckCreditApplyPO, LuckCreditApplyVO>(repository, LuckCreditApplyMapper.MAPPER){

    private var sessionFactoryStage: Stage.SessionFactory = sessionFactory.unwrap(Stage.SessionFactory::class.java)


    fun audit(auditDTO: LuckCreditApplyAuditDTO):Mono<Unit>{
        if (auditDTO.status != UpDownCreditStatus.PASS.code && auditDTO.status != UpDownCreditStatus.REJECT.code){
            return Mono.error(BusinessException("[错误]处理状态只能是通过|拒绝。"))
        }


    return  repository.findById(auditDTO.id).flatMap { it ->
          if (it.status!=UpDownCreditStatus.APPLY.code){
              return@flatMap Mono.error(BusinessException("[错误]本申请已处理,不能重复处理。"))
          }
          if (it.type==UpDownCreditType.UP.code && auditDTO.credit?.compareTo(BigDecimal.ZERO)==BigDecimalUtils.SMALL){
              return@flatMap Mono.error(BusinessException("[错误]上分金额必须为正数。"))
          }else if (it.type==UpDownCreditType.DOWN.code && auditDTO.credit?.compareTo(BigDecimal.ZERO)==BigDecimalUtils.BIG){
              return@flatMap Mono.error(BusinessException("[错误]下分金额必须为负数。"))
          }
            repository.update(it.apply {
                this.credit=auditDTO.credit
                this.fromAddress=auditDTO.fromAddress
                this.toAddress=auditDTO.toAddress
                this.txnHash=auditDTO.txnHash
                this.status=auditDTO.status
                this.remark=auditDTO.remark ?: UpDownCreditStatus.values().first { it.code==auditDTO.status }.message
            })
        }.flatMap {luckCreditApply->
        //     上分     下分
        //同意  ++       empty
        //拒绝  empty    ++
            if ((luckCreditApply.type==UpDownCreditType.UP.code && luckCreditApply.status== UpDownCreditStatus.PASS.code)
                ||
                luckCreditApply.type==UpDownCreditType.DOWN.code && luckCreditApply.status== UpDownCreditStatus.REJECT.code
            ){
                val userWallet= luckWalletService.findWalletByUserId(luckCreditApply.userId,luckCreditApply.groupId)
                return@flatMap Mono.zip(Mono.just(luckCreditApply),userWallet)
            }
              return@flatMap Mono.empty()
            }
        .flatMap<Unit?> {
            val luckCreditApply=it.t1
            val userWallet=it.t2
            //上分 +分
            if (luckCreditApply.type==UpDownCreditType.UP.code && luckCreditApply.status== UpDownCreditStatus.PASS.code){
                //更新钱包和明细
                return@flatMap Mono.fromCompletionStage(sessionFactoryStage.withTransaction { session ->
                    session.createQuery<Long>("UPDATE LuckWalletPO t SET credit = credit + :credit where userId = :userId")
                        .setParameter("credit", luckCreditApply.credit)
                        .setParameter("userId", luckCreditApply.userId)
                        .executeUpdate()

                    val luckCreditLogPO= LuckCreditLogPO().apply {
                        this.credit = luckCreditApply.credit
                        this.userId = luckCreditApply.userId
                        this.type = CreditLogType.CREDIT_UP_PASS.code
                        this.remark = "${CreditLogType.CREDIT_UP_PASS.desc}[${luckCreditApply.id}]"
                        this.creditBefore = userWallet.credit
                        this.creditAfter = this.creditBefore?.plus(this.credit?: BigDecimal.ZERO)
                        this.groupId=userWallet.groupId
                    }
                    session.persist(luckCreditLogPO).thenApply {
                        Unit
                    }
                })

            } else  if ( luckCreditApply.type==UpDownCreditType.DOWN.code && luckCreditApply.status== UpDownCreditStatus.REJECT.code){
                //下分 拒绝 +分(因为在提交的时候，就预扣了)
                return@flatMap Mono.fromCompletionStage(sessionFactoryStage.withTransaction { session ->
                    session.createQuery<Long>("UPDATE LuckWalletPO t SET credit = credit + :credit where userId = :userId")
                        .setParameter("credit", luckCreditApply.credit?.abs())
                        .setParameter("userId", luckCreditApply.userId)
                        .executeUpdate()

                    val luckCreditLogPO= LuckCreditLogPO().apply {
                        this.credit = luckCreditApply.credit?.abs()
                        this.userId = luckCreditApply.userId
                        this.type = CreditLogType.CREDIT_DOWN_REJECT.code
                        this.remark = "${CreditLogType.CREDIT_DOWN_REJECT.desc}[${luckCreditApply.id}]"
                        this.creditBefore = userWallet.credit
                        this.creditAfter = this.creditBefore?.plus(this.credit?: BigDecimal.ZERO)
                        this.groupId=userWallet.groupId
                    }
                    session.persist(luckCreditLogPO).thenApply {
                        Unit
                    }
                })
            }
            return@flatMap Mono.empty()
        }
    }

}
