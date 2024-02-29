package com.bbbang.luck.mapper;

import com.bbbang.luck.domain.dto.LuckInviteDTO
import com.bbbang.luck.domain.dto.LuckInvitePageDTO
import com.bbbang.luck.domain.vo.LuckInviteVO
import com.bbbang.luck.domain.bo.LuckInviteBO
import com.bbbang.luck.domain.po.LuckInvitePO
import com.bbbang.parent.mapper.BasicMapperExtend
import org.mapstruct.*
import org.mapstruct.factory.Mappers


@MapperConfig(unmappedTargetPolicy=ReportingPolicy.IGNORE)
@Mapper(builder = Builder(disableBuilder = true),
  unmappedSourcePolicy= ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface LuckInviteMapper:BasicMapperExtend<LuckInviteDTO,LuckInvitePageDTO,LuckInviteBO,LuckInvitePO,LuckInviteVO> {

  companion object {
    val  MAPPER:LuckInviteMapper = Mappers.getMapper(LuckInviteMapper::class.java)
  }
}

