package com.bbbang.luck.service;

import com.bbbang.luck.repository.LuckSendLuckRepository
import com.bbbang.luck.domain.vo.LuckSendLuckVO
import com.bbbang.luck.domain.bo.LuckSendLuckBO
import com.bbbang.luck.domain.dto.LuckSendLuckDTO
import com.bbbang.luck.domain.dto.LuckSendLuckPageDTO
import com.bbbang.luck.domain.po.LuckSendLuckPO
import com.bbbang.luck.mapper.LuckSendLuckMapper
import com.bbbang.parent.service.impl.BaseServiceImpl
import jakarta.inject.Singleton


@Singleton
open class LuckSendLuckService(private val repository: LuckSendLuckRepository) 
:BaseServiceImpl<LuckSendLuckDTO, LuckSendLuckPageDTO, LuckSendLuckBO, LuckSendLuckPO, LuckSendLuckVO>(repository, LuckSendLuckMapper.MAPPER){



}
