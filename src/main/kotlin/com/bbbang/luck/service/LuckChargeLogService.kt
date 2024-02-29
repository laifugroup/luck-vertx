package com.bbbang.luck.service;

import com.bbbang.luck.repository.LuckChargeLogRepository
import com.bbbang.luck.domain.vo.LuckChargeLogVO
import com.bbbang.luck.domain.bo.LuckChargeLogBO
import com.bbbang.luck.domain.dto.LuckChargeLogDTO
import com.bbbang.luck.domain.dto.LuckChargeLogPageDTO
import com.bbbang.luck.domain.po.LuckChargeLogPO
import com.bbbang.luck.mapper.LuckChargeLogMapper
import com.bbbang.parent.service.impl.BaseServiceImpl
import jakarta.inject.Singleton


@Singleton
open class LuckChargeLogService(private val repository: LuckChargeLogRepository)
:BaseServiceImpl<LuckChargeLogDTO, LuckChargeLogPageDTO, LuckChargeLogBO, LuckChargeLogPO, LuckChargeLogVO>(repository, LuckChargeLogMapper.MAPPER){



}
