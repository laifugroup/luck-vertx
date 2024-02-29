package com.bbbang.luck.service.bot.service

import com.bbbang.luck.api.bot.i18n.I18nConstants

import com.bbbang.luck.api.bot.type.CreditLogType
import com.bbbang.luck.api.bot.type.SendLuckType
import com.bbbang.luck.configuration.properties.LuckProperties
import com.bbbang.luck.domain.po.LuckGoodLuckPO
import com.bbbang.luck.domain.po.LuckCreditLogPO
import com.bbbang.luck.domain.vo.LuckSendLuckVO
import com.bbbang.luck.event.DivideRedPackEvent
import com.bbbang.luck.service.LuckWalletService
import com.bbbang.luck.service.LuckGoodLuckService
import com.bbbang.luck.service.LuckUserService
import com.bbbang.luck.service.bot.bots.LuckBot
import com.bbbang.parent.exception.BusinessException
import io.micronaut.context.MessageSource
import jakarta.inject.Singleton
import org.hibernate.SessionFactory
import org.hibernate.reactive.stage.Stage
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList


@Singleton
class DivideRedPackService (private val luckBot: LuckBot,
                            sessionFactory: SessionFactory,
                            private val luckProperties: LuckProperties,
                            private val luckWalletService: LuckWalletService,
                            private val messageSource: MessageSource,
                            private val luckRunningService:LuckRunningService,
                            private val luckGoodLuckService: LuckGoodLuckService,
                            private val luckUserService: LuckUserService
    ){

    private var sessionFactoryStage: Stage.SessionFactory = sessionFactory.unwrap(Stage.SessionFactory::class.java)

    fun divideRedPack(event: DivideRedPackEvent?){
        //1.查询订单
       val sendRedPackVO=  event?.sendRedPackVO
       val redPackId=sendRedPackVO?.id ?:throw BusinessException("[redPackId]红包ID参数不能为空")
       val sendGoodLuckUserId=sendRedPackVO?.userId ?:throw BusinessException("[userId]发红包用户ID参数不能为空")
       val amount=event?.sendRedPackVO?.credit ?:throw BusinessException("[amount]金额参数不能为空")
       val numbers=event?.sendRedPackVO?.redPackNumbers?:throw BusinessException("[numbers]红包个数不能为空")
       val oddsCredit=event?.oddsCredit?:throw BusinessException("[oddsCredit]保证金参数不能为空")


        luckGoodLuckService.findByLuckRedPackId(redPackId)
            .map {
                luckRunningService.divide(goodLuck=it, amount=amount,numbers=numbers)
            }.flatMap { goodLuckList->
                val boomUserList=luckUserService.findByIdInList(goodLuckList.filter { it.boomNumber==it.lastNumber }.map {goodLuck-> goodLuck.userId })

                val userWallet=  boomUserList.filter { it.inviterUserId!=null }.map {user-> user.inviterUserId }.defaultIfEmpty(0).collectList().flatMap {
                    luckWalletService.findByUserIdInList(goodLuckList.map { goodLuck-> goodLuck.userId }+sendGoodLuckUserId+it)
                }

                Mono.zip(Mono.just(goodLuckList),userWallet,boomUserList.collectList(),Mono.just(sendGoodLuckUserId))
            }.flatMap {
                tuple->
                val goodLuckList=tuple.t1
                //包含
                val userWallet=tuple.t2
                val boomUserList=tuple.t3
                val sendGoodLuckUserId=tuple.t4


                val creditLogPOList=ArrayList<LuckCreditLogPO>()

                goodLuckList.forEach{  goodLuck ->
                    //+抢到的红包金额
                    val luckUsersWallet= userWallet.first { it.userId == goodLuck.userId }
                    val luckLuckCreditLog= LuckCreditLogPO().apply {
                        this.credit = goodLuck.credit
                        this.userId = goodLuck.userId
                        this.type = CreditLogType.GRAB_RED_PACK.code
                        this.remark = "${CreditLogType.GRAB_RED_PACK.desc}[$redPackId]"
                        this.creditBefore = luckUsersWallet.credit?.add(BigDecimal.ZERO)
                        this.creditAfter = this.creditBefore?.plus(goodLuck.credit?:BigDecimal.ZERO)
                        this.groupId=luckUsersWallet.groupId
                    }
                    //修改余额
                    luckUsersWallet.credit=luckLuckCreditLog.creditAfter?.add(BigDecimal.ZERO)

                    creditLogPOList.add(luckLuckCreditLog)

                    if (goodLuck.boomNumber==goodLuck.lastNumber){
                       //- 中雷用户赔付
                        val boomLuckCreditLog=LuckCreditLogPO().apply {
                            this.credit = -oddsCredit
                            this.userId = goodLuck.userId
                            this.type = CreditLogType.BOOM.code
                            this.remark = "${CreditLogType.BOOM.desc}[$redPackId]"
                            this.creditBefore = luckUsersWallet.credit?.add(BigDecimal.ZERO)
                            this.creditAfter = this.creditBefore?.plus(this.credit?:BigDecimal.ZERO)
                            this.groupId=luckUsersWallet.groupId
                        }
                        //修改余额
                        luckUsersWallet.credit=boomLuckCreditLog.creditAfter?.add(BigDecimal.ZERO)
                        creditLogPOList.add(boomLuckCreditLog)

                        //+ 我发包,中雷赔付给我
                        val hostUserWallet= userWallet.first { it.userId == sendGoodLuckUserId }
                            val boomGiveMeCreditLog=LuckCreditLogPO().apply {
                                this.credit = oddsCredit
                                this.userId = hostUserWallet.userId
                                this.type = CreditLogType.COMPENSATION.code
                                this.remark = "${CreditLogType.COMPENSATION.desc}[$redPackId]"
                                this.creditBefore = hostUserWallet.credit?.add(BigDecimal.ZERO)
                                this.creditAfter = this.creditBefore?.plus(this.credit?: BigDecimal.ZERO)
                                this.groupId=hostUserWallet.groupId
                            }
                        //有可能多次中雷赔付 重新给hostUserWallet赋值
                        hostUserWallet.credit=boomGiveMeCreditLog.creditAfter?.add(BigDecimal.ZERO)
                            creditLogPOList.add(boomGiveMeCreditLog)

                        val luckBoomPlatformWater=oddsCredit.multiply(luckProperties.platformWater).divide(BigDecimal.valueOf(100.00)).setScale(2, RoundingMode.DOWN)
                        val luckBoomAgentWater=oddsCredit.multiply(luckProperties.agentWater).divide(BigDecimal.valueOf(100.00)).setScale(2, RoundingMode.DOWN)

                            //-平台抽成
                            val boomLuckCreditPlatformWater=LuckCreditLogPO().apply {
                                this.credit = -luckBoomPlatformWater
                                this.userId = hostUserWallet.userId
                                this.type = CreditLogType.PLATFORM_WATER.code
                                this.remark = "${CreditLogType.PLATFORM_WATER.desc}[$redPackId]"
                                this.creditBefore =hostUserWallet.credit?.add(BigDecimal.ZERO)
                                this.creditAfter = this.creditBefore?.plus(this.credit?: BigDecimal.ZERO)
                                this.groupId=hostUserWallet.groupId
                            }
                            hostUserWallet.credit=boomLuckCreditPlatformWater.creditAfter?.add(BigDecimal.ZERO)
                            creditLogPOList.add(boomLuckCreditPlatformWater)
                            //-代理抽成
                            val boomLuckCreditAgentWater=LuckCreditLogPO().apply {
                                this.credit = -luckBoomAgentWater
                                this.userId = hostUserWallet.userId
                                this.type = CreditLogType.AGENT_WATER.code
                                this.remark = "${CreditLogType.AGENT_WATER.desc}[$redPackId]"
                                this.creditBefore = hostUserWallet.credit?.add(BigDecimal.ZERO)
                                this.creditAfter = this.creditBefore?.plus(this.credit?: BigDecimal.ZERO)
                                this.groupId=hostUserWallet.groupId
                            }
                            hostUserWallet.credit=boomLuckCreditAgentWater.creditAfter?.add(BigDecimal.ZERO)
                            creditLogPOList.add(boomLuckCreditAgentWater)

                        //+ 下级中雷分成(即 -代理抽成的金额) 1. 如果没有邀请代理，不分。
                        val inviteeUserId=boomUserList.first{ boomUser-> boomUser.id==goodLuck.userId }.inviterUserId
                        inviteeUserId?.let {
                            val agentWallet=userWallet.first{  it.userId==inviteeUserId}
                            val getBoomLuckCreditAgentWater=LuckCreditLogPO().apply {
                                this.credit = luckBoomAgentWater
                                this.userId = agentWallet.userId
                                this.type = CreditLogType.CHILD_BOOM_REBATE.code
                                this.remark = "${CreditLogType.CHILD_BOOM_REBATE.desc}[$redPackId]"
                                this.creditBefore = agentWallet.credit?.add(BigDecimal.ZERO)
                                this.creditAfter = this.creditBefore?.plus(this.credit?: BigDecimal.ZERO)
                                this.groupId=agentWallet.groupId
                            }
                            agentWallet.credit=getBoomLuckCreditAgentWater.creditAfter?.add(BigDecimal.ZERO)
                            creditLogPOList.add(getBoomLuckCreditAgentWater)

                        }

                    }
                }

                //事务更新钱包记录
                Mono.fromCompletionStage(sessionFactoryStage.withTransaction { session ->
                    //更新分红包记录
                    goodLuckList.forEach { luckUser->
                        session.createQuery<Long>("UPDATE LuckGoodLuckPO t SET credit = :credit,lastNumber = :lastNumber where id = :id")
                            .setParameter("credit", luckUser.credit)
                            .setParameter("id", luckUser.id)
                            .setParameter("lastNumber", luckUser.lastNumber)
                            .executeUpdate()
                    }

                    //更新红包记录状态
                    session.createQuery<Long>("UPDATE LuckSendLuckPO t SET status = :status where id = :id")
                        .setParameter("status", SendLuckType.TERMED.code)
                        .setParameter("id", redPackId)
                        .executeUpdate()

                    //更新钱包余额
                    userWallet.forEach {luckWallet->
                        session.createQuery<Long>("UPDATE LuckWalletPO t SET credit = :credit where userId = :userId")
                            .setParameter("credit", luckWallet.credit)
                            .setParameter("userId", luckWallet.userId)
                            .executeUpdate()
                    }
                    //更新明细记录
                    session.persist(*creditLogPOList.toTypedArray()).thenApply {
                        goodLuckList
                    }
                    //CompletableFuture.completedFuture()
                })
            }.map {goodLuckList->
                //send message
                event?.callbackQuery?.let {
                    sendLuckMessage(luckBot,it,oddsCredit,sendRedPackVO,goodLuckList)
                }
            }.subscribe()

    }

    private  fun sendLuckMessage(absSender: LuckBot, callbackQuery: CallbackQuery?, odds:BigDecimal, sendRedPack: LuckSendLuckVO?, goodLuckList:List<LuckGoodLuckPO>) {
        val locale = callbackQuery?.from?.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH
        val decimalFormat=DecimalFormat("#.00")
        val luckResultOver = messageSource.getMessage(
            "luck.result.over",
            locale,
            sendRedPack?.firstName ?: "",
            sendRedPack?.userId,
            sendRedPack?.credit,
            luckProperties.redPackNumbers,
            luckProperties.odds,
            sendRedPack?.boomNumber
        ).orElse(I18nConstants.constants)

        //2.
        val luckResultItems = StringBuilder()
        luckResultItems.append("`")
        val boomFlag=messageSource.getMessage("luck.result.item.boom", locale, ).orElse(I18nConstants.constants)
        val usdFlag=messageSource.getMessage("luck.result.item.usd", locale, ).orElse(I18nConstants.constants)

        goodLuckList.forEach{goodLuck ->
            val boom=goodLuck?.lastNumber==sendRedPack?.boomNumber
            val   luckResultItem=messageSource.getMessage(
                "luck.result.item",locale,
                if (boom)  boomFlag  else usdFlag,
                decimalFormat.format(goodLuck.credit),
                if (goodLuck.firstName?.length?:0 <=5) goodLuck.firstName else "${goodLuck.firstName?.substring(0,5)}..",
            ).orElse(I18nConstants.constants)
            luckResultItems.append(luckResultItem)
        }
        luckResultItems.append("`")
        //3.
        val sendUserLuck= goodLuckList.filter { it.boomNumber == it.lastNumber }.sumOf { odds }
        val cost = sendRedPack?.credit
        val amountReceived = sendUserLuck.minus(cost!!)
        val luckResultCost = messageSource.getMessage("luck.result.cost", locale, sendUserLuck, "-${cost}", amountReceived,goodLuckList.map { it.lastNumber }.joinToString("-")).orElse(I18nConstants.constants)

        val replayLuckMessage = "$luckResultOver$luckResultItems$luckResultCost"
        val replyMarkup = callbackQuery?.message?.replyMarkup
        replyMarkup?.keyboard?.removeAt(0)

        val editMessage = EditMessageCaption.builder()
            .chatId(callbackQuery?.message?.chatId)
            .messageId(callbackQuery?.message?.messageId)
            .caption(replayLuckMessage)
            .parseMode(ParseMode.MARKDOWN)
            .replyMarkup(replyMarkup)
            .build()

        val result = absSender.executeAsync(editMessage)

    }


}