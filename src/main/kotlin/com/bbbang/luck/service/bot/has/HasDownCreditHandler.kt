package com.bbbang.luck.service.bot.has


import com.bbbang.luck.api.bot.ext.replyMessage
import com.bbbang.luck.api.bot.i18n.I18nConstants
import com.bbbang.luck.api.bot.type.CreditLogType
import com.bbbang.luck.configuration.TronConfiguration
import com.bbbang.luck.configuration.properties.LuckProperties
import com.bbbang.luck.domain.bo.LuckCreditApplyBO
import com.bbbang.luck.domain.po.LuckCreditLogPO
import com.bbbang.luck.domain.type.UpDownCreditStatus
import com.bbbang.luck.domain.type.UpDownCreditType
import com.bbbang.luck.service.LuckCreditApplyService
import com.bbbang.luck.service.LuckUserRechargeWalletService
import com.bbbang.luck.service.LuckWalletService
import com.bbbang.luck.service.wrapper.LuckUserServiceWrapper
import com.bbbang.parent.utils.BigDecimalUtils
import io.micronaut.context.MessageSource
import jakarta.inject.Singleton
import org.hibernate.SessionFactory
import org.hibernate.reactive.stage.Stage
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import kotlin.text.Typography.dollar

@Singleton
class HasDownCreditHandler(
    private  val luckUserServiceWrapper: LuckUserServiceWrapper,
    private  val luckWalletService: LuckWalletService,
    private  val luckCreditApplyService: LuckCreditApplyService,
    private  val luckUserRechargeWalletService: LuckUserRechargeWalletService,
    private  val messageSource: MessageSource,
    private  val tronConfiguration: TronConfiguration,
    private  val luckProperties: LuckProperties,
    private val sessionFactory: SessionFactory
) {

    private var sessionFactoryStage: Stage.SessionFactory = sessionFactory.unwrap(Stage.SessionFactory::class.java)


    fun handler(absSender: DefaultAbsSender, update: Update) {

        val fromId = update.message.from.id
        val botUser=update.message.from

        val matchResult =  Regex("(\\d+(\\.\\d+)?)").find(update.message.text)
        val withdrawalCredit=if (matchResult!=null) matchResult.groups[0]?.value?.toBigDecimal()?.abs()?.setScale(2, RoundingMode.DOWN) else BigDecimal.ZERO
        //提现金额限制
        if (withdrawalCredit?.compareTo(luckProperties.withdrawalLimit)==BigDecimalUtils.SMALL){
            val locale = update.message.from?.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH
            val luckWithdrawalAmount = messageSource.getMessage("luck.withdrawal.amount", locale,luckProperties.withdrawalLimit).orElse(
                I18nConstants.constants)
            absSender.replyMessage(update.message.chatId, update.message.messageId,luckWithdrawalAmount)
            return
        }
        luckUserServiceWrapper.findByBotUser(botUser,update.message.chat)
            .onErrorResume {
                val errorMessage = it.message?:""
                val sendMessage= SendMessage(update.message.chat?.id.toString(),errorMessage)
                absSender.executeAsync(sendMessage)
                return@onErrorResume Mono.empty()
            }
            .flatMap {
                luckWalletService.findWalletByUserId(it.id,it.groupId)
        }.flatMap {wallet->
            //余额是否足够提现
            if (withdrawalCredit?.compareTo(wallet.credit)==BigDecimalUtils.BIG){
                absSender.replyMessage(update.message.chatId, update.message.messageId, "余额不足,请检查余额")
                return@flatMap   Mono.empty()
            }
            //扣余额
            Mono.fromCompletionStage(sessionFactoryStage.withTransaction { session ->

                session.createQuery<Long>("UPDATE LuckWalletPO t SET credit = :credit where id = :id")
                    .setParameter("credit", wallet.credit?.subtract(withdrawalCredit))
                    .setParameter("id", wallet.id)
                    .executeUpdate()

                val luckCreditLog= LuckCreditLogPO().apply {
                    this.credit = -withdrawalCredit!!
                    this.userId = wallet.userId
                    this.type = CreditLogType.CREDIT_DOWN.code
                    this.remark = "${CreditLogType.CREDIT_DOWN.desc}[${update.message.text}]"
                    this.creditBefore = wallet.credit
                    this.creditAfter = wallet.credit?.plus(this.credit?: BigDecimal.ZERO)
                    this.groupId=wallet.groupId
                }
                session.persist(luckCreditLog)
                    .thenApply { luckCreditLog }
            })
        }
        .flatMap {
            luckCreditApplyService.save(LuckCreditApplyBO().apply {
                this.userId=it.userId
                this.credit= it.credit
                this.groupId=update.message.chatId
                this.type= UpDownCreditType.DOWN.code
                this.status= UpDownCreditStatus.APPLY.code
                this.remark=UpDownCreditType.DOWN.message
            })
        }.flatMap {
            luckUserRechargeWalletService.findByUserId(it.userId,it.groupId)
        }
        .map {
            val locale = update.message.from?.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH

            val creditDownTips = messageSource.getMessage("credit.down.tips",locale).orElse(I18nConstants.constants)
            val creditDownTipsUnbound = messageSource.getMessage("credit.down.tips.unbound", locale).orElse(I18nConstants.constants)
            val tips=if (it.address!=null) "$creditDownTips" else "$creditDownTips$creditDownTipsUnbound"
            absSender.replyMessage(update.message.chatId, update.message.messageId, tips)
        }
        .subscribe()
    }




}