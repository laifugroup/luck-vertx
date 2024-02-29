package com.bbbang.luck.mapper;

import com.bbbang.luck.domain.dto.LuckInviteLogDTO
import com.bbbang.luck.domain.dto.LuckInviteLogPageDTO
import com.bbbang.luck.domain.vo.LuckInviteLogVO
import com.bbbang.luck.domain.bo.LuckInviteLogBO
import com.bbbang.luck.domain.po.LuckInviteLogPO
import com.bbbang.parent.mapper.BasicMapperExtend
import org.mapstruct.*
import org.mapstruct.factory.Mappers


@MapperConfig(unmappedTargetPolicy=ReportingPolicy.IGNORE)
@Mapper(builder = Builder(disableBuilder = true),
  unmappedSourcePolicy= ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface LuckInviteLogMapper:BasicMapperExtend<LuckInviteLogDTO,LuckInviteLogPageDTO,LuckInviteLogBO,LuckInviteLogPO,LuckInviteLogVO> {

  companion object {
    val  MAPPER:LuckInviteLogMapper = Mappers.getMapper(LuckInviteLogMapper::class.java)
  }
}

