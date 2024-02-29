package com.bbbang.luck.service;

import com.bbbang.luck.repository.UserAnalysisHourRepository
import com.bbbang.luck.service.UserAnalysisHourService
import com.bbbang.luck.domain.vo.UserAnalysisHourVO
import com.bbbang.luck.domain.bo.UserAnalysisHourBO
import com.bbbang.luck.domain.dto.UserAnalysisHourDTO
import com.bbbang.luck.domain.dto.UserAnalysisHourPageDTO
import com.bbbang.luck.domain.po.UserAnalysisHourPO
import com.bbbang.luck.mapper.UserAnalysisHourMapper
import com.bbbang.parent.service.impl.BaseServiceImpl
import jakarta.inject.Inject
import jakarta.inject.Singleton


@Singleton
open class UserAnalysisHourService(private val repository: UserAnalysisHourRepository) 
:BaseServiceImpl<UserAnalysisHourDTO, UserAnalysisHourPageDTO, UserAnalysisHourBO, UserAnalysisHourPO, UserAnalysisHourVO>(repository, UserAnalysisHourMapper.MAPPER){



}
