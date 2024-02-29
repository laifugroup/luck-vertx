package com.bbbang.luck.helper

import com.bbbang.luck.api.bot.type.CallBackActionsType
import com.bbbang.luck.configuration.properties.ServiceProperties
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

class ReplyMarkUpHelper {

        companion object{

            fun getStartReplyKeyboardMarkup1(): ReplyKeyboard {
                val group1=KeyboardRow(listOf(
                    KeyboardButton().apply {
                    this.text=CallBackActionsType.WITHDRAWAL.desc
                    },
                    KeyboardButton().apply {
                        this.text=CallBackActionsType.RECHARGE.desc
                    },
                    KeyboardButton().apply {
                        this.text=CallBackActionsType.PLAY_RULE.desc
                    },
                    KeyboardButton().apply {
                        this.text=CallBackActionsType.BALANCE.desc
                    }
                ))
                val group2=KeyboardRow(listOf(
                    KeyboardButton().apply {
                        this.text=CallBackActionsType.INVITE_LINK.desc
                    },
                    KeyboardButton().apply {
                        this.text=CallBackActionsType.INVITE_QUERY.desc
                    },
                    KeyboardButton().apply {
                        this.text=CallBackActionsType.WATER_REPORT.desc
                    },
                    KeyboardButton().apply {
                        this.text=CallBackActionsType.GAME_REPORT.desc
                    }
                ))
                val group3=KeyboardRow(listOf(
                    KeyboardButton().apply {
                        this.text=CallBackActionsType.FINANCE.desc
                    },
                    KeyboardButton().apply {
                        this.text=CallBackActionsType.CUSTOMER_SERVICE.desc
                    },
                ))


                val list= listOf(group1,group2,group3)
                val replyKeyboardMarkup=ReplyKeyboardMarkup().apply {
                    //是否每次显示键盘都需要关闭并重新触
                    this.oneTimeKeyboard=false
                    this.keyboard= list
                    this.resizeKeyboard=true
                    //是否固定到底部
                    this.isPersistent=true
                    this.inputFieldPlaceholder="选择按钮开启幸运之旅"
                }
                return replyKeyboardMarkup
            }
            fun getFinanceAuditReplyKeyboardMarkup(): InlineKeyboardMarkup {
                val keyboardMarkup = InlineKeyboardMarkup()
                val keyboardButtonsRow1 =  listOf(
                    InlineKeyboardButton().apply {
                        this.text=CallBackActionsType.AUDIT_PASS.desc
                        this.callbackData=CallBackActionsType.AUDIT_PASS.code
                    },
                    InlineKeyboardButton().apply {
                        this.text=CallBackActionsType.AUDIT_REJECT.desc
                        this.callbackData=CallBackActionsType.AUDIT_REJECT.code
                    },
                )
                val keyboardButtons = ArrayList<List<InlineKeyboardButton>>()
                keyboardButtons.addAll(listOf(keyboardButtonsRow1))
                keyboardMarkup.keyboard = keyboardButtons
                return keyboardMarkup
            }


            fun getGameKeyboardMarkup(serviceProperties: ServiceProperties?=null):InlineKeyboardMarkup{
                val keyboardMarkup = InlineKeyboardMarkup()
                val keyboardButtonsRow1 =  listOf(
                    InlineKeyboardButton().apply {
                        this.text=CallBackActionsType.WITHDRAWAL.desc
                        this.callbackData=CallBackActionsType.WITHDRAWAL.code
                        this.url=serviceProperties?.finance
                    },
                    InlineKeyboardButton().apply {
                        this.text=CallBackActionsType.RECHARGE.desc
                        this.callbackData=CallBackActionsType.RECHARGE.code
                        this.url=serviceProperties?.finance
                    },
                    InlineKeyboardButton().apply {
                        this.text=CallBackActionsType.PLAY_RULE.desc
                        this.callbackData=CallBackActionsType.PLAY_RULE.code
                        this.url=serviceProperties?.playRule
                    },
                    InlineKeyboardButton().apply {
                        this.text=CallBackActionsType.BALANCE.desc
                        this.callbackData=CallBackActionsType.BALANCE.code
                    }
                )

                val keyboardButtonsRow2 =  listOf(
                    InlineKeyboardButton().apply {
                        this.text=CallBackActionsType.INVITE_LINK.desc
                        this.callbackData=CallBackActionsType.INVITE_LINK.code
                    },
                    InlineKeyboardButton().apply {
                        this.text=CallBackActionsType.INVITE_QUERY.desc
                        this.callbackData=CallBackActionsType.INVITE_QUERY.code
                    },
                    InlineKeyboardButton().apply {
                        this.text=CallBackActionsType.WATER_REPORT.desc
                        this.callbackData=CallBackActionsType.WATER_REPORT.code
                    },
                    InlineKeyboardButton().apply {
                        this.text= CallBackActionsType.GAME_REPORT.desc
                        this.callbackData=CallBackActionsType.GAME_REPORT.code
                    }
                )

                val keyboardButtonsRow3 = listOf(
                    InlineKeyboardButton().apply {
                        this.text=CallBackActionsType.FINANCE.desc
                        this.callbackData=CallBackActionsType.FINANCE.code
                        this.url=serviceProperties?.finance
                    },
                    InlineKeyboardButton().apply {
                        this.text=CallBackActionsType.CUSTOMER_SERVICE.desc
                        this.callbackData=CallBackActionsType.CUSTOMER_SERVICE.code
                        this.url=serviceProperties?.customerService
                    },
                )
                val keyboardButtons = ArrayList<List<InlineKeyboardButton>>()
                keyboardButtons.addAll(listOf(keyboardButtonsRow1,keyboardButtonsRow2,keyboardButtonsRow3))
                keyboardMarkup.keyboard = keyboardButtons

                return  keyboardMarkup
            }
        }
}