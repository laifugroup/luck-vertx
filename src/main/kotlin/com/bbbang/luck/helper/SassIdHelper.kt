package com.bbbang.luck.helper

import org.telegram.telegrambots.meta.api.objects.User

object SassIdHelper {


    fun getSassId(groupId:Long?,botUserId:Long?):String{
        return "${groupId}_${botUserId}"
    }

    fun getSassId(groupId:Long?,botUser:User?):String{
        return "${groupId}_${botUser?.id}"
    }


}