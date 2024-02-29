package com.bbbang.luck.service.bot.has


import com.bbbang.luck.api.bot.i18n.I18nConstants

import com.bbbang.luck.api.bot.type.ChatMembersType
import com.bbbang.luck.api.bot.type.CreditLogType
import com.bbbang.luck.configuration.properties.LuckProperties
import com.bbbang.luck.domain.bo.LuckInviteLogBO
import com.bbbang.luck.domain.po.LuckCreditLogPO
import com.bbbang.luck.helper.SassIdHelper
import com.bbbang.luck.mapper.LuckWalletMapper
import com.bbbang.luck.service.*
import com.bbbang.luck.service.wrapper.LuckUserServiceWrapper
import com.bbbang.parent.utils.BigDecimalUtils
import io.micronaut.context.MessageSource
import jakarta.inject.Singleton
import org.apache.commons.codec.digest.DigestUtils
import org.bouncycastle.pqc.math.linearalgebra.BigIntUtils
import org.hibernate.SessionFactory
import org.hibernate.reactive.stage.Stage
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

@Singleton
class HasChatMemberHandler(private val luckProperties: LuckProperties,
                           private val sessionFactory: SessionFactory,
                           private val luckUserServiceWrapper: LuckUserServiceWrapper,
                           private val inviteService: LuckInviteService,
                           private val inviteLogService: LuckInviteLogService,
                           private val luckUserService: LuckUserService,
                           private val messageSource: MessageSource,
                           private val luckWalletService: LuckWalletService,
                            private val luckActivityService: LuckActivityService
) {
    private var sessionFactoryStage: Stage.SessionFactory = sessionFactory.unwrap(Stage.SessionFactory::class.java)



    fun handler(absSender: DefaultAbsSender, update: Update) {

        val chatMember = update.chatMember
        if (chatMember.newChatMember.status != ChatMembersType.MEMBER.code) {
            return
        }
        //防止反复邀请导致重复
        val inviteLink = chatMember.inviteLink?.inviteLink ?: return

        luckUserServiceWrapper.findByBotUser(chatMember.from,chatMember.chat)
            .flatMap { inviteeUser ->
                val md5 = DigestUtils.md5Hex(inviteLink.toByteArray(Charsets.UTF_8))
                //var md5="8fdd9252ac42a58adbeffe21906347f6"
                inviteService.findByUrlHash(md5).flatMap {inviteInfo->
                    //邀请关系记录
                    val luckInviteLogBO= LuckInviteLogBO().apply {
                        this.userId = inviteInfo.userId
                        this.inviteUrl = inviteInfo.url
                        this.urlHash = inviteInfo.urlHash
                        this.groupId = inviteInfo.groupId
                        this.inviteeUserId = inviteeUser.id
                        this.remark=inviteeUser.firstName
                    }
                    //查询邀请记录是否存在 存在不执行后面步骤
                    inviteLogService.existsByInviteeUserId(inviteeUser.id).flatMap {
                      if (it){//存在邀请管理-不执行后面的步骤
                           Mono.empty()
                      }else{
                          //不存在邀请关系 执行后面的步骤
                         val sassId= SassIdHelper.getSassId(inviteeUser.groupId,inviteeUser.botUserId)
                          luckUserServiceWrapper.updateInviterUserIdById(inviteeUser.id, sassId,inviteInfo.userId)
                              .flatMap{ inviteLogService.save(luckInviteLogBO)}
                      }
                    }
                }
            }.flatMap {inviteLog->
                //新用户赠送金
                //邀请人
                val inviterUserWallet=luckWalletService.findWalletByUserId(inviteLog.userId,inviteLog.groupId)
                //被邀请人
                val inviteeUserWallet=luckWalletService.findWalletByUserId(inviteLog.inviteeUserId,inviteLog.groupId)
                //活动
                val activity=luckActivityService.findByNewUserActivity()
                Mono.zip(inviterUserWallet,inviteeUserWallet,activity,Mono.just(inviteLog))
            }.flatMap {tuple->
                val inviterUserWallet=tuple.t1
                val inviteeUserWallet=tuple.t2
                val activity=tuple.t3
                val inviteLog=tuple.t4

                //邀请金0.1U
                Mono.fromCompletionStage(sessionFactoryStage.withTransaction { session ->
                    val luckCreditLogList=ArrayList<LuckCreditLogPO>()
                    //邀请人增加余额和明细
                    session.createQuery<Long>("UPDATE LuckWalletPO t SET credit = credit + :credit where userId = :userId")
                        .setParameter("credit", luckProperties.inviteRebate)
                        .setParameter("userId", inviterUserWallet.userId)
                        .executeUpdate()

                    val luckCreditLog= LuckCreditLogPO().apply {
                        this.credit = luckProperties.inviteRebate
                        this.userId = inviterUserWallet.userId
                        this.type = CreditLogType.INVITE.code
                        this.remark = "${CreditLogType.INVITE.desc}[${ inviteeUserWallet.userId}]"
                        this.creditBefore = inviterUserWallet.credit
                        this.creditAfter =  this.creditBefore?.add(this.credit)
                        this.groupId=inviterUserWallet.groupId
                    }
                    luckCreditLogList.add(luckCreditLog)
                    //被邀请人-活动赠金5U
                    if (activity.sendCredit?.compareTo(BigDecimal.ZERO)==BigDecimalUtils.BIG){
                        session.createQuery<Long>("UPDATE LuckWalletPO t SET credit = credit + :credit where userId = :userId")
                            .setParameter("credit", activity.sendCredit)
                            .setParameter("userId", inviteeUserWallet.userId)
                            .executeUpdate()

                        val luckCreditLog= LuckCreditLogPO().apply {
                            this.credit = activity.sendCredit
                            this.userId = inviteeUserWallet.userId
                            this.type = CreditLogType.ACTIVITY.code
                            this.remark = "${CreditLogType.ACTIVITY.desc}[${activity.remark}]"
                            this.creditBefore = inviteeUserWallet.credit
                            this.creditAfter =  this.creditBefore?.add(this.credit)
                            this.groupId=inviteeUserWallet.groupId
                        }
                        luckCreditLogList.add(luckCreditLog)
                    }
                    session.persist(*luckCreditLogList.toTypedArray()).thenApply { inviteLog }
                })
            }
            .flatMap {
                //查询邀请人信息
                it.userId?.let { it1 -> luckUserService.findById(it1) }
            }
            .map {inviterUser->//邀请人信息-给邀请人发消息获得了奖励
                val locale = chatMember.from.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH
                val inviteSuccess = messageSource.getMessage("luck.invite.success", locale, chatMember.from.firstName,luckProperties.inviteRebate)
                    .orElse(I18nConstants.constants)

                val sendMessage= SendMessage(inviterUser?.botUserId.toString(), inviteSuccess)
                absSender.executeAsync(sendMessage)
            }
            .subscribe()
    }




}