package com.bbbang.luck.mapper;

import com.bbbang.luck.domain.dto.LuckPlatformDTO
import com.bbbang.luck.domain.dto.LuckPlatformPageDTO
import com.bbbang.luck.domain.vo.LuckPlatformVO
import com.bbbang.luck.domain.bo.LuckPlatformBO
import com.bbbang.luck.domain.po.LuckPlatformPO
import com.bbbang.parent.mapper.BasicMapperExtend
import org.mapstruct.*
import org.mapstruct.factory.Mappers


@MapperConfig(unmappedTargetPolicy=ReportingPolicy.IGNORE)
@Mapper(builder = Builder(disableBuilder = true),
  unmappedSourcePolicy= ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface LuckPlatformMapper:BasicMapperExtend<LuckPlatformDTO,LuckPlatformPageDTO,LuckPlatformBO,LuckPlatformPO,LuckPlatformVO> {

  companion object {
    val  MAPPER:LuckPlatformMapper = Mappers.getMapper(LuckPlatformMapper::class.java)
  }
}

