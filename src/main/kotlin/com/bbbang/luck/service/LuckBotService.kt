package com.bbbang.luck.service;

import com.bbbang.luck.repository.LuckBotRepository
import com.bbbang.luck.domain.bo.LuckBotBO
import com.bbbang.luck.domain.dto.LuckBotDTO
import com.bbbang.luck.domain.dto.LuckBotPageDTO
import com.bbbang.luck.domain.po.LuckBotPO
import com.bbbang.luck.domain.vo.LuckBotVO
import com.bbbang.luck.mapper.LuckBotMapper
import com.bbbang.parent.service.impl.BaseServiceImpl
import jakarta.inject.Singleton


@Singleton
open class LuckBotService(private val repository: LuckBotRepository)
:BaseServiceImpl<LuckBotDTO, LuckBotPageDTO, LuckBotBO, LuckBotPO, LuckBotVO>(repository, LuckBotMapper.MAPPER){



}
