package com.bbbang.luck.mapper;

import com.bbbang.luck.domain.dto.UserAnalysisHourDTO
import com.bbbang.luck.domain.dto.UserAnalysisHourPageDTO
import com.bbbang.luck.domain.vo.UserAnalysisHourVO
import com.bbbang.luck.domain.bo.UserAnalysisHourBO
import com.bbbang.luck.domain.po.UserAnalysisHourPO
import com.bbbang.parent.mapper.BasicMapperExtend
import org.mapstruct.*
import org.mapstruct.factory.Mappers


@MapperConfig(unmappedTargetPolicy=ReportingPolicy.IGNORE)
@Mapper(builder = Builder(disableBuilder = true),
  unmappedSourcePolicy= ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserAnalysisHourMapper:BasicMapperExtend<UserAnalysisHourDTO,UserAnalysisHourPageDTO,UserAnalysisHourBO,UserAnalysisHourPO,UserAnalysisHourVO> {

  companion object {
    val  MAPPER:UserAnalysisHourMapper = Mappers.getMapper(UserAnalysisHourMapper::class.java)
  }
}

