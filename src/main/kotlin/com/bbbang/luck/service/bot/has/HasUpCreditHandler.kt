package com.bbbang.luck.service.bot.has


import com.bbbang.luck.api.bot.ext.replyMessage
import com.bbbang.luck.api.bot.ext.simpleMessage
import com.bbbang.luck.api.bot.i18n.I18nConstants
import com.bbbang.luck.configuration.TronConfiguration
import com.bbbang.luck.configuration.properties.ServiceProperties
import com.bbbang.luck.domain.bo.LuckCreditApplyBO
import com.bbbang.luck.domain.type.UpDownCreditStatus
import com.bbbang.luck.domain.type.UpDownCreditType
import com.bbbang.luck.helper.ReplyMarkUpHelper
import com.bbbang.luck.service.LuckCreditApplyService
import com.bbbang.luck.service.LuckUserRechargeWalletService
import com.bbbang.luck.service.LuckWalletService
import com.bbbang.luck.service.wrapper.LuckUserServiceWrapper
import io.micronaut.context.MessageSource
import jakarta.inject.Singleton
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.util.*

@Singleton
class HasUpCreditHandler(
                        private  val luckUserServiceWrapper: LuckUserServiceWrapper,
                        private  val luckWalletService: LuckWalletService,
                        private  val luckCreditApplyService: LuckCreditApplyService,
                        private  val luckUserRechargeWalletService: LuckUserRechargeWalletService,
                        private  val messageSource: MessageSource,
                        private  val tronConfiguration: TronConfiguration,
                        private  val serviceProperties: ServiceProperties
) {

    fun handler(absSender: DefaultAbsSender, update: Update) {
        luckUserServiceWrapper.findByBotUser(update)
        .onErrorResume {
            val errorMessage = it.message?:""
            val sendMessage= SendMessage(update.message.chat?.id.toString(),errorMessage)
            absSender?.executeAsync(sendMessage)
            return@onErrorResume Mono.empty()
        }
        .flatMap {
            luckWalletService.findWalletByUserId(it.id,it.groupId)
        }.flatMap {
            luckCreditApplyService.save(LuckCreditApplyBO().apply {
                this.userId=it.userId
                //this.credit= BigDecimal.ZERO
                this.groupId=it.groupId
                this.type= UpDownCreditType.UP.code
                this.status= UpDownCreditStatus.APPLY.code
                this.remark=UpDownCreditType.UP.message
            })
        }.flatMap {
            luckUserRechargeWalletService.findByUserId(it.userId!!,it.groupId)
        }
        .map {

            val locale = update.message.from?.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH

            //用户提醒
            val creditUpTips = messageSource.getMessage("credit.up.tips", locale,tronConfiguration.rechargeAddress).orElse(I18nConstants.constants)

            val creditUpTipsBound = messageSource.getMessage("credit.up.tips.bound", locale).orElse(I18nConstants.constants)
            val creditUpTipsUnbound = messageSource.getMessage("credit.up.tips.unbound", locale).orElse(I18nConstants.constants)
            val tips=if (it.address!=null) "$creditUpTips$creditUpTipsBound" else "$creditUpTips$creditUpTipsUnbound"
            absSender.replyMessage(update.message.chatId, update.message.messageId, tips)

            //财务提醒 审核通过或拒绝
            val groupId=update.message.chatId
            val botUserId=update.message.from.id
            val trc20=it.address?:"未绑定"
            val up=UpDownCreditType.UP.message

            val financeMessage = messageSource.getMessage("credit.audit", locale,groupId,botUserId,trc20,up).orElse(I18nConstants.constants)
            //ReplyMarkUpHelper.getFinanceAuditReplyKeyboardMarkup()
            absSender.simpleMessage(serviceProperties.financeBotUserId, financeMessage)
        }
        .subscribe()
    }




}