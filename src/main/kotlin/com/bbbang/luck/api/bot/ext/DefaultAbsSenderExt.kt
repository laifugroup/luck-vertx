package com.bbbang.luck.api.bot.ext

import org.telegram.telegrambots.meta.api.methods.GetMe
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.bots.AbsSender


fun AbsSender.simpleMessage(chatId: String, message:String,replyKeyboard: ReplyKeyboard?=null) {
    val sendMessage= SendMessage().apply {
        this.chatId=chatId
    }
    sendMessage.text=message
    sendMessage.enableMarkdownV2(true)
    sendMessage.enableMarkdown(true)
    replyKeyboard?.let {
        sendMessage.replyMarkup=it
    }
    this.executeAsync(sendMessage)
}


fun AbsSender.simpleMessage(chatId: Long?, message:String,replyKeyboard: ReplyKeyboard?=null) {
    val sendMessage= SendMessage().apply {
        this.chatId=chatId.toString()
    }
    sendMessage.text=message
    sendMessage.enableMarkdownV2(true)
    sendMessage.enableMarkdown(true)
    replyKeyboard?.let {
        sendMessage.replyMarkup=it
    }
    this.executeAsync(sendMessage)
}



fun AbsSender.replyMessage(chatId: Long?, messageId:Int, replyMessage:String,replyKeyboard: ReplyKeyboard?=null) {
    val sendMessage= SendMessage().apply {
        this.chatId=chatId.toString()
    }
    sendMessage.replyToMessageId=messageId
    sendMessage.text=replyMessage
    replyKeyboard?.let {
        sendMessage.replyMarkup=it
    }
    sendMessage.enableMarkdownV2(true)
    sendMessage.enableMarkdown(true)
    this.executeAsync(sendMessage)
}




//示例
fun AbsSender.getMe() {
    val getMe= GetMe()
    this.executeAsync(getMe)
}

fun AbsSender.sendPoll(chatId: Long?) {
    val options = listOf("Yes","No")
// Let's just assume we get the chatMessage as a parameter. For example from the message received, or from a database
    val sendPoll = SendPoll(chatId.toString(), "Some Question", options)
    this.executeAsync(sendPoll)
}


fun AbsSender.sendChatAction () {
    val sendChatAction= SendChatAction()
    this.executeAsync(sendChatAction)
}



