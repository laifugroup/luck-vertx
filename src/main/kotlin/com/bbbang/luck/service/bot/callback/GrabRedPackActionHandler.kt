package com.bbbang.luck.service.bot.callback


import com.bbbang.luck.api.bot.ext.simpleMessage
import com.bbbang.luck.api.bot.i18n.I18nConstants
import com.bbbang.luck.api.bot.type.*
import com.bbbang.luck.configuration.properties.LuckProperties
import com.bbbang.luck.configuration.properties.TronProperties
import com.bbbang.luck.domain.bo.LuckGoodLuckBO
import com.bbbang.luck.event.DivideRedPackEvent
import com.bbbang.luck.service.*
import com.bbbang.luck.service.wrapper.LuckUserServiceWrapper
import com.bbbang.parent.utils.BigDecimalUtils
import com.lmax.disruptor.EventTranslator
import com.lmax.disruptor.dsl.Disruptor
import io.micronaut.context.MessageSource
import jakarta.inject.Singleton
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import reactor.core.publisher.Mono
import java.io.Serializable
import java.util.*
import java.util.concurrent.CompletableFuture


@Singleton
class GrabRedPackActionHandler(
    private val luckProperties: LuckProperties,
    private val tronProperties: TronProperties,
    // private val sessionFactory: SessionFactory,
    private val luckWalletService: LuckWalletService,
    private val messageSource: MessageSource,
    private val luckUserServiceWrapper: LuckUserServiceWrapper,
    //private val creditLogService: LuckCreditLogService,
    private val sendRedPackService: LuckSendLuckService,
    private val luckGoodLuckService: LuckGoodLuckService,
    private val disruptor: Disruptor<DivideRedPackEvent>,
) {


    //private var sessionFactoryStage: Stage.SessionFactory = sessionFactory.unwrap(Stage.SessionFactory::class.java)


    fun grabRedPackHandler(absSender: DefaultAbsSender, callbackQuery: CallbackQuery) {
        val botUser = callbackQuery.from
        val locale = callbackQuery.from?.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH
        val chatId=callbackQuery.message.chatId
        val messageId=callbackQuery.message.messageId

        val redPackMessage = callbackQuery.message.replyToMessage.text
        val pattern = "[-|/]".toRegex()
        val split = redPackMessage.split(pattern)
        val total = split[0]
        val boomNumber = split[1]
        //val dollar=Integer.valueOf(total)*100
        val redPackDataStr = callbackQuery.data.split("|")
        val redPackId=redPackDataStr[1].toLong()
        val sendLuckUserId=redPackDataStr[2].toLong()
        val oddsCredit = luckProperties.odds.multiply(total.toBigDecimal())

        //1.查询用户，如果用户不存在就新建用户
        //2.验证余额是否足够倍数
        //3.查询是否有抢红包记录，如果无抢空包记录，扣除额度并增加记录
        //4.查询抢红包合计数量，并推送显示
        //5.计算红包结果，并更新抢红包结果。返回扣除额度，并根据结果更新余额分值
        luckUserServiceWrapper.findByBotUser(callbackQuery)
            .flatMap { luckUser->
                //自己不能抢自己的红包
//                if (luckUser.id==sendLuckUserId){
//                    return@flatMap Mono.empty()
//                }
                luckGoodLuckService.countByLuckRedPackId(redPackId).flatMap {
                    if (it>=luckProperties.redPackNumbers){
                        //红包已经抢完
                        return@flatMap Mono.empty()
                    }
                    Mono.just(luckUser)
                }
            }.flatMap {
                return@flatMap luckWalletService.findWalletByUserId(it.id,it.groupId)
            }.flatMap { wallet->
                if (wallet.credit?.compareTo(oddsCredit)==BigDecimalUtils.SMALL) {
                    val luckInsufficientBalance = messageSource.getMessage("luck.insufficient.balance", locale,botUser.firstName,botUser.id,wallet.credit,tronProperties.rechargeAddress)
                        .orElse(I18nConstants.constants)
                    absSender.simpleMessage(callbackQuery.message.chatId, luckInsufficientBalance)
                    return@flatMap Mono.empty()
                }
                Mono.just(wallet)
            }.flatMap {wallet->
                luckGoodLuckService.existsByLuckRedPackIdAndUserId(redPackId,wallet.userId).flatMap{ hasLuck->
                    if (hasLuck){//已经抢过了，不处理
                        return@flatMap  Mono.empty()
                    }
                    //不扣保证金版本
                    return@flatMap sendRedPackService.findById(redPackId).flatMap { sendLuck->
                        luckGoodLuckService.save(LuckGoodLuckBO().apply {
                            this.luckRedPackId = redPackId
                            this.userId = wallet.userId
                            this.boomNumber = boomNumber.toInt()
                            this.credit = null
                            this.firstName=botUser.firstName
                            this.lastName=botUser.lastName
                            this.userName=botUser.userName
                            this.sendRedPackUserId=sendLuck?.userId
                            this.groupId=chatId
                        }).map {
                            sendLuck
                        }
                    }.flatMap {sendLuck->
                        luckGoodLuckService.countByLuckRedPackId(sendLuck?.id).map { counts->
                        val sendMessageResult=    grabRedPackActionMessage(absSender, callbackQuery, chatId, messageId, counts)
                            // 必须变更完成后，再执行开奖 注意：定时任务,巡查没有开奖 未作
                            sendMessageResult.whenComplete { _, ex ->
                                    if (counts==luckProperties.redPackNumbers){//抢红包到达6个，开奖
                                        disruptor.publishEvent(EventTranslator<DivideRedPackEvent> { event, _ ->
                                            event.oddsCredit=oddsCredit
                                            event.callbackQuery=callbackQuery
                                            event.sendRedPackVO=sendLuck
                                        })
                                    }
                            }
                        }
                    }
                }
            }.subscribe()
    //方法结束
   }


    /**
     * 变更抢红包的人数
     */
    private fun grabRedPackActionMessage(
        absSender: DefaultAbsSender,
        callbackQuery: CallbackQuery,
        chatId: Long,
        messageId: Int,
        addGrabNumber: Int? = 0,
    ): CompletableFuture<Serializable> {
        callbackQuery.message.replyToMessage
        val fromId=callbackQuery.message.replyToMessage.from.id
        val firstName=callbackQuery.message.replyToMessage.from.firstName
        //val lastName=callbackQuery.message.replyToMessage.from.lastName

        val locale = callbackQuery.from?.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH

        val redPack=callbackQuery.message.replyToMessage.text
        val pattern = "[-|/]".toRegex()
        val split=redPack.split(pattern)
        val total=split[0]
        val boomNumber=split[1]

        val maxNumber=luckProperties.redPackNumbers
        val grabMessage=messageSource.getMessage("luck.grab.message",locale,maxNumber,addGrabNumber,total,boomNumber).orElse(
            I18nConstants.constants)

        val replyMarkup=callbackQuery.message.replyMarkup
        replyMarkup.keyboard[0][0].text=grabMessage
        //不使用这个文字，因为丢失了样式
        //val cation=callbackQuery.message.caption
        val replayLuckMessage=messageSource.getMessage("luck.grab.replay",locale,firstName,fromId,total).orElse(
            I18nConstants.constants)

        val editMessage = EditMessageCaption.builder()
            .chatId(chatId)
            .messageId(messageId)
            .caption(replayLuckMessage)
            .parseMode(ParseMode.MARKDOWN)
            .replyMarkup(replyMarkup)
            .build()

        val result:CompletableFuture<Serializable> =absSender.executeAsync(editMessage)

        return result
    }


}




