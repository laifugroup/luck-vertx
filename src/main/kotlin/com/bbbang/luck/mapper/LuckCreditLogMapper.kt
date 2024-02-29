package com.bbbang.luck.mapper;

import com.bbbang.luck.domain.dto.LuckCreditLogDTO
import com.bbbang.luck.domain.dto.LuckCreditLogPageDTO
import com.bbbang.luck.domain.vo.LuckCreditLogVO
import com.bbbang.luck.domain.bo.LuckCreditLogBO
import com.bbbang.luck.domain.po.LuckCreditLogPO
import com.bbbang.parent.mapper.BasicMapperExtend
import org.mapstruct.*
import org.mapstruct.factory.Mappers


@MapperConfig(unmappedTargetPolicy=ReportingPolicy.IGNORE)
@Mapper(builder = Builder(disableBuilder = true),
  unmappedSourcePolicy= ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface LuckCreditLogMapper:BasicMapperExtend<LuckCreditLogDTO,LuckCreditLogPageDTO,LuckCreditLogBO,LuckCreditLogPO,LuckCreditLogVO> {

  companion object {
    val  MAPPER:LuckCreditLogMapper = Mappers.getMapper(LuckCreditLogMapper::class.java)
  }
}

