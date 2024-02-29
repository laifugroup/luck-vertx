package com.bbbang.luck.service.wrapper

import com.bbbang.luck.api.bot.type.LuckUserType
import com.bbbang.luck.api.bot.type.BotMessageType
import com.bbbang.luck.domain.bo.LuckPlatformBO
import com.bbbang.luck.domain.bo.LuckUserBO
import com.bbbang.luck.domain.type.LuckUserRoleType
import com.bbbang.luck.domain.type.PlatformStatus
import com.bbbang.luck.domain.vo.LuckPlatformVO
import com.bbbang.luck.domain.vo.LuckUserVO
import com.bbbang.luck.service.LuckPlatformService
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
open class LuckPlatformServiceWrapper(private val luckPlatformService: LuckPlatformService) {

    fun findByGroupId(update: Update?): Mono<LuckPlatformVO> {
        return this.findByGroupId(update?.message?.chatId)
    }

    @Cacheable("#groupId")
    open fun findByGroupId(groupId: Long?): Mono<LuckPlatformVO> {
       return luckPlatformService.findByGroupId(groupId)
           .switchIfEmpty(Mono.error(BusinessException("未查询到数据")))
    }

    @CachePut("#groupId")
    open fun saveGroupId(botUser: User?,groupId: Long?): Mono<LuckPlatformVO> {
        return luckPlatformService.findByGroupId(groupId).switchIfEmpty(
            luckPlatformService.save(LuckPlatformBO(groupId=groupId, adminBotUserId = botUser?.id, status = PlatformStatus.ENABLE.code))
        )
    }






}