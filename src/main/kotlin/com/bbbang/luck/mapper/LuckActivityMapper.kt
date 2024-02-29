package com.bbbang.luck.mapper;

import com.bbbang.luck.domain.dto.LuckActivityDTO
import com.bbbang.luck.domain.dto.LuckActivityPageDTO
import com.bbbang.luck.domain.vo.LuckActivityVO
import com.bbbang.luck.domain.bo.LuckActivityBO
import com.bbbang.luck.domain.po.LuckActivityPO
import com.bbbang.parent.mapper.BasicMapperExtend
import org.mapstruct.*
import org.mapstruct.factory.Mappers


@MapperConfig(unmappedTargetPolicy=ReportingPolicy.IGNORE)
@Mapper(builder = Builder(disableBuilder = true),
  unmappedSourcePolicy= ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface LuckActivityMapper:BasicMapperExtend<LuckActivityDTO,LuckActivityPageDTO,LuckActivityBO,LuckActivityPO,LuckActivityVO> {

  companion object {
    val  MAPPER:LuckActivityMapper = Mappers.getMapper(LuckActivityMapper::class.java)
  }
}

