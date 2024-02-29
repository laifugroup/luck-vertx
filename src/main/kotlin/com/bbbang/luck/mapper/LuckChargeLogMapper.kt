package com.bbbang.luck.mapper;

import com.bbbang.luck.domain.dto.LuckChargeLogDTO
import com.bbbang.luck.domain.dto.LuckChargeLogPageDTO
import com.bbbang.luck.domain.vo.LuckChargeLogVO
import com.bbbang.luck.domain.bo.LuckChargeLogBO
import com.bbbang.luck.domain.po.LuckChargeLogPO
import com.bbbang.parent.mapper.BasicMapperExtend
import org.mapstruct.*
import org.mapstruct.factory.Mappers


@MapperConfig(unmappedTargetPolicy=ReportingPolicy.IGNORE)
@Mapper(builder = Builder(disableBuilder = true),
  unmappedSourcePolicy= ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface LuckChargeLogMapper:BasicMapperExtend<LuckChargeLogDTO,LuckChargeLogPageDTO,LuckChargeLogBO,LuckChargeLogPO,LuckChargeLogVO> {

  companion object {
    val  MAPPER:LuckChargeLogMapper = Mappers.getMapper(LuckChargeLogMapper::class.java)
  }
}

