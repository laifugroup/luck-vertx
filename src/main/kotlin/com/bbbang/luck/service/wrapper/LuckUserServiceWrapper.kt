package com.bbbang.luck.service.wrapper

import com.bbbang.luck.api.bot.type.LuckUserType
import com.bbbang.luck.api.bot.type.BotMessageType
import com.bbbang.luck.domain.bo.LuckUserBO
import com.bbbang.luck.domain.po.LuckUserPO
import com.bbbang.luck.domain.type.LuckUserRoleType
import com.bbbang.luck.domain.vo.LuckUserVO
import com.bbbang.luck.helper.SassIdHelper
import com.bbbang.luck.repository.LuckUserRepository
import com.bbbang.luck.service.LuckUserService
import com.bbbang.parent.exception.BusinessException
import io.micronaut.cache.annotation.CachePut
import io.micronaut.cache.annotation.Cacheable
import jakarta.inject.Singleton
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.User
import reactor.core.publisher.Mono


@Singleton
open class LuckUserServiceWrapper(private val luckUserService: LuckUserService,
                                  private val luckUserRepository: LuckUserRepository) {

    fun findByBotUser(callbackQuery: CallbackQuery): Mono<LuckUserVO> {
        return this.findByBotUser(callbackQuery.from,callbackQuery.message.chatId,callbackQuery.message.chat?.type)
    }


    fun findByBotUser(update: Update): Mono<LuckUserVO> {
        val botUser=update.message.from
        val chat=update.message.chat
        return this.findByBotUser(botUser,chat)
    }

    fun findByBotUser(botUser: User?,chat:Chat?): Mono<LuckUserVO> {
        return this.findByBotUser(botUser,chat?.id, chat?.type)
    }



    fun findByBotUser(botUser: User?,groupId: Long?,chatType:String?): Mono<LuckUserVO> {
        val sassId = SassIdHelper.getSassId(groupId,botUser)
        val result=this.findByBotUser(botUser,sassId ,groupId,chatType)
       return result//.map { println("sassId=$sassId  ${it.userName}" ); it }
    }





    @CachePut("#sassId")
    open fun updateRolesById(userId:Long?,sassId: String?,roles:String?): Mono<Int?>{
        if (sassId==null){
            return Mono.empty()
        }
        return luckUserRepository.updateRolesById(userId,roles)
    }

    /**
     * 这里更新邀请人信息，一般不影响查询
     */
    @CachePut("#sassId")
    open fun updateInviterUserIdById(userId:Long?,sassId: String?,inviterUserId:Long?): Mono<Int?>{
        if (sassId==null){
            return Mono.empty()
        }
        return luckUserRepository.updateInviterUserIdById(userId,inviterUserId)
    }

    @Cacheable("#sassId")
    open fun findByBotUser(botUser: User?,sassId:String ,groupId: Long?,chatType:String?): Mono<LuckUserVO> {
        return luckUserService.findByBotUserId(botUser?.id!!,groupId)
            .switchIfEmpty(
            luckUserService.save(LuckUserBO().apply {
                this.botUserId =botUser.id
                this.groupId= groupId
                this.firstName = botUser.firstName
                this.lastName = botUser.lastName
                this.userName = botUser.userName
                this.status = LuckUserType.ENABLE.code
                this.roles= LuckUserRoleType.USER.code
            }))
            .flatMap { user ->
                if (user.status==LuckUserType.DISABLE.code){
                    return@flatMap  Mono.error(BusinessException("用户[${user.firstName}]已经被禁用"))
                }
                if (user.groupId==null && BotMessageType.SUPERGROUP.code == chatType){
                    luckUserService.update(user.id!!,LuckUserBO().apply {
                        this.groupId=groupId
                    })
                }else {
                    Mono.just(user)
                }
            }.map {  println(it?.inviterUserId); it }
    }






}