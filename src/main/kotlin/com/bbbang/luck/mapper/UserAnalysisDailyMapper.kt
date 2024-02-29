package com.bbbang.luck.mapper;

import com.bbbang.luck.domain.dto.UserAnalysisDailyDTO
import com.bbbang.luck.domain.dto.UserAnalysisDailyPageDTO
import com.bbbang.luck.domain.vo.UserAnalysisDailyVO
import com.bbbang.luck.domain.bo.UserAnalysisDailyBO
import com.bbbang.luck.domain.po.UserAnalysisDailyPO
import com.bbbang.parent.mapper.BasicMapperExtend
import org.mapstruct.*
import org.mapstruct.factory.Mappers


@MapperConfig(unmappedTargetPolicy=ReportingPolicy.IGNORE)
@Mapper(builder = Builder(disableBuilder = true),
  unmappedSourcePolicy= ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserAnalysisDailyMapper:BasicMapperExtend<UserAnalysisDailyDTO,UserAnalysisDailyPageDTO,UserAnalysisDailyBO,UserAnalysisDailyPO,UserAnalysisDailyVO> {

  companion object {
    val  MAPPER:UserAnalysisDailyMapper = Mappers.getMapper(UserAnalysisDailyMapper::class.java)
  }
}

