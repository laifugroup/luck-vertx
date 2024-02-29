package com.bbbang.luck.service.bot.callback


import com.bbbang.luck.api.bot.action.SettingActions
import com.bbbang.luck.api.bot.ext.simpleMessage
import com.bbbang.luck.mapper.LuckUserRechargeWalletMapper
import com.bbbang.luck.service.LuckUserRechargeWalletService
import com.bbbang.luck.service.wrapper.LuckUserServiceWrapper
import io.micronaut.context.MessageSource
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard


@Singleton
class SettingWalletActionHandler(private var luckUserService: LuckUserServiceWrapper,
                                 private val luckUserRechargeWalletService: LuckUserRechargeWalletService) {
    @Inject
    lateinit var messageSource: MessageSource

     fun handler(absSender: DefaultAbsSender,callbackQuery: CallbackQuery){

         luckUserService.findByBotUser(callbackQuery).flatMap {
             luckUserRechargeWalletService.findByUserId(it.id,it.groupId)
         }.map {
             if (it.address.isNullOrEmpty()){
                 val settingWalletAddress = SettingActions.SETTING_WALLET_ADDRESS
                 val sendMessage=SendMessage().apply {
                     this.text=settingWalletAddress
                     this.chatId=callbackQuery.message.chat.id.toString()
                     this.replyMarkup= ForceReplyKeyboard(true,true,"请输入TRC20钱包地址")
                 }
                 absSender.executeAsync(sendMessage)
             }else{
                 val sendMessage=SendMessage().apply {
                     this.text="已绑定:${it?.address}\n如需解绑请联系客服"
                     this.chatId=callbackQuery.message.chat.id.toString()
                 }
                 absSender.executeAsync(sendMessage)
             }
         }.subscribe()

    }



    fun  bindingWalletAddress(absSender: DefaultAbsSender,update: Update){
            //验证钱包地址是否合格
        val walletAddress= update.message.text
        luckUserService.findByBotUser(update)
            .flatMap {
                luckUserRechargeWalletService.findByUserId(it.id,it.groupId)
            }
            .flatMap { it ->
                val rechargeWallet=LuckUserRechargeWalletMapper.MAPPER.vo2bo(it).also {
                    it.address=walletAddress
                }
                it?.id?.let { it1 -> luckUserRechargeWalletService.update(it1,rechargeWallet) }
            }.map {
                //绑定成功
                absSender.simpleMessage(update.message.chatId,"绑定成功:${it?.address}")
            }.subscribe()

    }


}