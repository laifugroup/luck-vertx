package com.bbbang.luck.service.bot.has


import com.bbbang.luck.api.bot.ext.replyMessage
import com.bbbang.luck.api.bot.i18n.I18nConstants

import com.bbbang.luck.helper.ReplyMarkUpHelper
import com.bbbang.luck.api.bot.type.*
import com.bbbang.luck.configuration.properties.LuckProperties
import com.bbbang.luck.configuration.properties.ServiceProperties
import com.bbbang.luck.configuration.properties.TronProperties
import com.bbbang.luck.domain.po.LuckSendLuckPO
import com.bbbang.luck.domain.po.LuckCreditLogPO
import com.bbbang.luck.service.LuckWalletService
import com.bbbang.luck.service.wrapper.LuckUserServiceWrapper
import com.bbbang.parent.utils.BigDecimalUtils
import io.micronaut.context.MessageSource
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.hibernate.SessionFactory
import org.hibernate.reactive.stage.Stage
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.util.*
import java.util.concurrent.CompletableFuture


@Singleton
class HasSendLuckMessageHandler(private val luckProperties: LuckProperties,
                        private val serviceProperties: ServiceProperties,
                        private val tronProperties: TronProperties,
                          sessionFactory: SessionFactory ) {

    @Inject
    lateinit var messageSource: MessageSource

    @Inject
    lateinit var luckUserService: LuckUserServiceWrapper

    @Inject
    lateinit var luckWallService: LuckWalletService


    private var sessionFactoryStage: Stage.SessionFactory = sessionFactory.unwrap(Stage.SessionFactory::class.java)

    fun handler(absSender: DefaultAbsSender, update: Update) {
        val locale = update.message.from?.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH

        // Split the message text into total and boomNumber
        val (total, boomNumber) = update.message.text.split("[-|/]".toRegex()).take(2)

        // Check if the total amount is within the allowed range
        val dollar = BigDecimal.valueOf(total.toDouble())

        if (dollar.compareTo(luckProperties.reservePrice)==BigDecimalUtils.SMALL ||
            dollar.compareTo(luckProperties.limitBid)==BigDecimalUtils.BIG
            ) {
            val luckLimitAmount = messageSource.getMessage("luck.limit.amount", locale,luckProperties.reservePrice,luckProperties.limitBid).orElse(I18nConstants.constants)
            absSender.replyMessage(update.message.chatId, update.message.messageId, luckLimitAmount)
            return
        }

        luckUserService.findByBotUser(update)
            .onErrorResume {
                val errorMessage = it.message?:""
                val sendMessage= SendMessage(update.message.chat?.id.toString(),errorMessage)
                absSender.executeAsync(sendMessage)
                return@onErrorResume Mono.empty()
            }
            .flatMap { user ->
               return@flatMap luckWallService.findWalletByUserId(user.id,user.groupId).flatMap {wallet->
                   if (wallet.credit?.compareTo(dollar)==BigDecimalUtils.SMALL) {
                       // Insufficient balance
                       val luckInsufficientBalance = messageSource.getMessage("luck.insufficient.balance", locale,user.firstName,update.message.from.id,wallet.credit,tronProperties.rechargeAddress).orElse(I18nConstants.constants)
                       absSender.replyMessage(update.message.chatId, update.message.messageId, luckInsufficientBalance)
                       return@flatMap Mono.empty()
                   }
                   return@flatMap Mono.just(wallet)
               }
            }
            .flatMap { wallet ->
                    // Deduct the balance, add a credit log and a red pack sending record

                val balance = wallet.credit?.minus(dollar)

                 Mono.fromCompletionStage(sessionFactoryStage.withTransaction { session ->

                    session.createQuery<Long>("UPDATE LuckWalletPO t SET credit = :credit where id = :id")
                        .setParameter("credit", balance)
                        .setParameter("id", wallet.id)
                        .executeUpdate()
                     val sendLuckPO=LuckSendLuckPO().apply {
                         this.userId = wallet.userId
                         this.redPackNumbers = luckProperties.redPackNumbers
                         this.boomNumber = Integer.valueOf(boomNumber)
                         this.credit = dollar
                         this.firstName=update.message.from.firstName
                         this.lastName=update.message.from.lastName
                         this.userName=update.message.from.userName
                         this.groupId=update.message.chatId
                         this.status=SendLuckType.UNSETTLED.code
                     }

                     val luckCreditLog= LuckCreditLogPO().apply {
                         this.credit = -dollar
                         this.userId = wallet.userId
                         this.type = CreditLogType.SEND_RED_PACK.code
                         this.remark = "${CreditLogType.SEND_RED_PACK.desc}[${update.message.text}]"
                         this.creditBefore = wallet.credit
                         this.creditAfter = balance
                         this.groupId=wallet.groupId
                     }
                     session.persist(luckCreditLog,sendLuckPO)
                         .thenApply { sendLuckPO }
                })
            }
            .map {
                sendLuckMessageHandler(absSender, update, total, boomNumber,  it)
            }
            .subscribe{

            }
    }


    private fun sendLuckMessageHandler(absSender: DefaultAbsSender,it: Update, total:String, boomNumber:String, sendRedPack:LuckSendLuckPO): CompletableFuture<Message> {
        val locale = it.message.from?.languageCode?.let { Locale.forLanguageTag(it) } ?: Locale.ENGLISH
        val maxNumber=luckProperties.redPackNumbers
        val initGrabNumber=0

        val grabMessage=messageSource.getMessage("luck.grab.message",locale,maxNumber,initGrabNumber,total,boomNumber).orElse(I18nConstants.constants)
        val keyboardButtonsRow0 = ArrayList<InlineKeyboardButton>()

        val  luck= InlineKeyboardButton().apply {
            this.text=grabMessage
            this.callbackData="${CallBackActionsType.GRAB_RED_PACK.code}|${sendRedPack.id}|${sendRedPack.userId}|${sendRedPack.credit}|${sendRedPack.boomNumber}"
        }
        //println("callbackDataLength=${luck.callbackData.length}")

        keyboardButtonsRow0.add(luck)

        val keyboardMarkup = ReplyMarkUpHelper.getGameKeyboardMarkup(serviceProperties)
        keyboardMarkup.keyboard.add(0,keyboardButtonsRow0)
        //<a href='tg://user?id=${${it.message.from.userName}}'${it.message.from.firstName}  ${it.message.from.lastName}</a>
        val fromId=it.message.from.id
        val firstName=it.message.from.firstName

        val replayLuckMessage=messageSource.getMessage("luck.grab.replay",locale,firstName?:"-",fromId,total).orElse(I18nConstants.constants)

        val sendPhoto: SendPhoto = SendPhoto.builder()
            .chatId(it.message.chatId)
            .photo(InputFile(luckProperties.redPackUrl))
            .caption(replayLuckMessage)
            .parseMode(ParseMode.MARKDOWN)
            .replyMarkup(keyboardMarkup)
            .replyToMessageId(it.message.messageId)
            .build()
        val result= absSender.executeAsync(sendPhoto)
        //max=最大长度为 64 个字节
       // println("---->\n ${result.get()}")
        return result
    }






}