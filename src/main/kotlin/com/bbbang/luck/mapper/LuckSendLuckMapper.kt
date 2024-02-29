package com.bbbang.luck.mapper;

import com.bbbang.luck.domain.dto.LuckSendLuckDTO
import com.bbbang.luck.domain.dto.LuckSendLuckPageDTO
import com.bbbang.luck.domain.vo.LuckSendLuckVO
import com.bbbang.luck.domain.bo.LuckSendLuckBO
import com.bbbang.luck.domain.po.LuckSendLuckPO
import com.bbbang.parent.mapper.BasicMapperExtend
import org.mapstruct.*
import org.mapstruct.factory.Mappers


@MapperConfig(unmappedTargetPolicy=ReportingPolicy.IGNORE)
@Mapper(builder = Builder(disableBuilder = true),
  unmappedSourcePolicy= ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface LuckSendLuckMapper:BasicMapperExtend<LuckSendLuckDTO,LuckSendLuckPageDTO,LuckSendLuckBO,LuckSendLuckPO,LuckSendLuckVO> {

  companion object {
    val  MAPPER:LuckSendLuckMapper = Mappers.getMapper(LuckSendLuckMapper::class.java)
  }
}

