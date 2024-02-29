package com.bbbang.luck.mapper;

import com.bbbang.luck.domain.dto.LuckBotDTO
import com.bbbang.luck.domain.dto.LuckBotPageDTO
import com.bbbang.luck.domain.vo.LuckBotVO
import com.bbbang.luck.domain.bo.LuckBotBO
import com.bbbang.luck.domain.po.LuckBotPO
import com.bbbang.parent.mapper.BasicMapperExtend
import org.mapstruct.*
import org.mapstruct.factory.Mappers


@MapperConfig(unmappedTargetPolicy=ReportingPolicy.IGNORE)
@Mapper(builder = Builder(disableBuilder = true),
  unmappedSourcePolicy= ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface LuckBotMapper:BasicMapperExtend<LuckBotDTO,LuckBotPageDTO,LuckBotBO,LuckBotPO,LuckBotVO> {

  companion object {
    val  MAPPER:LuckBotMapper = Mappers.getMapper(LuckBotMapper::class.java)
  }
}

