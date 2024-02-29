package com.bbbang.luck.mapper;

import com.bbbang.luck.domain.dto.LuckGoodLuckDTO
import com.bbbang.luck.domain.dto.LuckGoodLuckPageDTO
import com.bbbang.luck.domain.vo.LuckGoodLuckVO
import com.bbbang.luck.domain.bo.LuckGoodLuckBO
import com.bbbang.luck.domain.po.LuckGoodLuckPO
import com.bbbang.parent.mapper.BasicMapperExtend
import org.mapstruct.*
import org.mapstruct.factory.Mappers


@MapperConfig(unmappedTargetPolicy=ReportingPolicy.IGNORE)
@Mapper(builder = Builder(disableBuilder = true),
  unmappedSourcePolicy= ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface LuckGoodLuckMapper:BasicMapperExtend<LuckGoodLuckDTO,LuckGoodLuckPageDTO,LuckGoodLuckBO,LuckGoodLuckPO,LuckGoodLuckVO> {

  companion object {
    val  MAPPER:LuckGoodLuckMapper = Mappers.getMapper(LuckGoodLuckMapper::class.java)
  }
}

