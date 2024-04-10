package com.bbbang.luck.service.bot.bots

import com.bbbang.luck.api.bot.command.ChatPatternCommands
import com.bbbang.luck.api.bot.type.CallBackActionsType
import com.bbbang.luck.api.bot.type.MyActionsType
import com.bbbang.luck.configuration.properties.BotProperties
import com.bbbang.luck.service.bot.callback.*
import com.bbbang.luck.service.bot.command.*
import com.bbbang.luck.service.bot.has.*
import io.micronaut.context.MessageSource
import jakarta.annotation.PostConstruct
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramWebhookCommandBot
import org.telegram.telegrambots.meta.api.objects.Update


@Singleton
class LuckWebhookCommandBot(options: DefaultBotOptions,private val botProperties: BotProperties) :
    TelegramWebhookCommandBot(options, true, botProperties.token) {


    @Inject
    lateinit var messageSource: MessageSource

    @Inject
    lateinit var initCommand: InitCommand

    @Inject
    lateinit var startCommand: StartCommand

    @Inject
    lateinit var reportCommand: ReportCommand



    //消息handler
    @Inject
    lateinit var hasSendLuckMessageHandler: HasSendLuckMessageHandler
    @Inject
    lateinit var hasChatMemberHandler: HasChatMemberHandler
    @Inject
    lateinit var hasBalanceHandler: HasBalanceHandler

    @Inject
    lateinit var hasDownCreditHandler: HasDownCreditHandler

    @Inject
    lateinit var hasUpCreditHandler: HasUpCreditHandler

    @Inject
    lateinit var hasMyHandler: HasAgentHandler


    @Inject
    lateinit var hasPrivateChatBotHandler: HasPrivateChatBotHandler


    @Inject
    lateinit var settingWalletActionHandler : SettingWalletActionHandler



    //动作handler
    @Inject
    lateinit var balanceActionHandler : BalanceActionHandler

    @Inject
    lateinit var inviteLinkActionHandler : InviteLinkActionHandler
    @Inject
    lateinit var inviteQueryActionHandler : InviteQueryActionHandler
    @Inject
    lateinit var dayReportActionHandler : DayReportActionHandler
    @Inject
    lateinit var waterReportActionHandler : WaterReportActionHandler
    @Inject
    lateinit var grabRedPackActionHandler : GrabRedPackActionHandler
    @Inject
    lateinit var grabRedPackActionHandler1 : GrabRedPackActionHandler1
    @Inject
    lateinit var auditActionHandler : AuditActionHandler





    @PostConstruct
    fun init() {
        register(initCommand)
        register(startCommand)
        register(reportCommand)
    }

    override fun getBotToken(): String {
        return botProperties.token
    }

    override fun getBotUsername(): String {
        return botProperties.username
    }

    override fun getBotPath(): String {
        return botProperties.username
    }

    override fun processNonCommandUpdate(update: Update?) {
        update?.let { updates ->
            when {
                updates.hasMessage() && update.message.hasText() -> {
                    //不允许私聊BOT
                    if (updates.message.chatId==update.message.from.id){
                        hasPrivateChatBotHandler.handler(this@LuckWebhookCommandBot,update)
                    }else if (//BotMessageType.SUPERGROUP.code == update.message.chat.type &&
                        updates.message.text.matches( ChatPatternCommands.SEND_LUCK.toRegex())) {
                        //发红包
                        hasSendLuckMessageHandler.handler(this@LuckWebhookCommandBot, updates)
                    }else if (update.message.text.matches(ChatPatternCommands.BALANCE.toRegex())){
                        //余额查询
                        hasBalanceHandler.handler(this@LuckWebhookCommandBot,update)
                    }else if (update.message.text.matches(ChatPatternCommands.UP_CREDIT.toRegex())){
                        //上分
                        hasUpCreditHandler.handler(this@LuckWebhookCommandBot,update)
                    }else if (update.message.text.matches(ChatPatternCommands.DOWN_CREDIT.toRegex())){
                        //下分
                        hasDownCreditHandler.handler(this@LuckWebhookCommandBot,update)
                    }else if (update.message.text.matches(ChatPatternCommands.MY.toRegex())){
                        //设置固定输入菜单栏-即底部菜单输入 我的
                        hasMyHandler.handler(this@LuckWebhookCommandBot,update)
                    }else if (update.message.text.matches(ChatPatternCommands.GAME.toRegex())){
                        //设置固定输入菜单栏-即底部菜单输入 游戏
                        hasMyHandler.handler(this@LuckWebhookCommandBot,update)
                    }else if (update.message.text.matches(ChatPatternCommands.PARTNER.toRegex())){
                        //设置固定输入菜单栏-即底部菜单输入 合伙人
                        hasMyHandler.handler(this@LuckWebhookCommandBot,update)
                    }else{
//                        // 是设置类消息
//                       if (updates.message.chatId!=update.message.from.id && updates.message.replyToMessage!=null){
////                            if (SettingActions.SETTING_WALLET_ADDRESS == update.message.replyToMessage.text){
////                                //绑定钱包地址
////                                settingWalletActionHandler.bindingWalletAddress(this@LuckWebhookCommandBot,update)
////                            }
//                       }

                    }
                }
                updates.hasCallbackQuery() -> {
                    val callbackQuery = updates.callbackQuery
                    //val chatId = callbackQuery.message.chatId
                    //val messageId = callbackQuery.message.messageId
                    when (val data=callbackQuery.data) {
                        CallBackActionsType.BALANCE.code -> balanceActionHandler.handler(
                            this@LuckWebhookCommandBot,
                            callbackQuery
                        )
                        CallBackActionsType.INVITE_LINK.code -> inviteLinkActionHandler.handler(
                            this@LuckWebhookCommandBot,
                            callbackQuery
                        )
                        CallBackActionsType.INVITE_QUERY.code -> inviteQueryActionHandler.handler(
                            this@LuckWebhookCommandBot,
                            callbackQuery
                        )
                        CallBackActionsType.GAME_REPORT.code -> dayReportActionHandler.handler(
                            this@LuckWebhookCommandBot,
                            callbackQuery
                        )
                        CallBackActionsType.WATER_REPORT.code -> waterReportActionHandler.handler(
                            this@LuckWebhookCommandBot,
                            callbackQuery
                        )
                        CallBackActionsType.AUDIT_PASS.code -> auditActionHandler.pass(
                            this@LuckWebhookCommandBot,
                            callbackQuery
                        )
                        CallBackActionsType.AUDIT_REJECT.code -> auditActionHandler.reject(
                            this@LuckWebhookCommandBot,
                            callbackQuery
                        )
                        MyActionsType.BINDING_WALLET.code -> settingWalletActionHandler.handler(
                            this@LuckWebhookCommandBot,
                            callbackQuery
                        )

                        else -> {
                          //抢红包给增加了额外的数据
                            if (data.startsWith(CallBackActionsType.GRAB_RED_PACK.code)) {
                                grabRedPackActionHandler1.grabRedPackHandlerDisruptor(this@LuckWebhookCommandBot, callbackQuery)
                            }
                        }
                    }
                }
                updates.hasChatMember() -> hasChatMemberHandler.handler(this@LuckWebhookCommandBot, update)
                updates.hasMyChatMember() -> println("myChatMember-->${updates.myChatMember}")
                updates.hasInlineQuery() -> println("inlineQuery-->")
                updates.hasChosenInlineQuery() -> println("chosenInlineQuery-->")
                updates.hasEditedMessage() -> println("editedMessage-->")
                updates.hasChannelPost() -> println("channelPost-->")
                updates.hasEditedChannelPost() -> println("editedChannelPost-->")
                updates.hasShippingQuery() -> println("shippingQuery-->")
                updates.hasPreCheckoutQuery() -> println("preCheckoutQuery-->")
                updates.hasPoll() -> println("poll-->")
                updates.hasPollAnswer() -> println("pollAnswer-->")
                updates.hasChatJoinRequest() -> println("chatJoinRequest-->${updates.chatJoinRequest}")
            }
        }
    }
    override fun processInvalidCommandUpdate(update: Update?) {
        super.processInvalidCommandUpdate(update)
        //无效指令
    }




}