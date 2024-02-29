package com.bbbang.luck.mapper;

import com.bbbang.luck.domain.dto.LuckUserDTO
import com.bbbang.luck.domain.dto.LuckUserPageDTO
import com.bbbang.luck.domain.vo.LuckUserVO
import com.bbbang.luck.domain.bo.LuckUserBO
import com.bbbang.luck.domain.po.LuckUserPO
import com.bbbang.parent.mapper.BasicMapperExtend
import org.mapstruct.*
import org.mapstruct.factory.Mappers


@MapperConfig(unmappedTargetPolicy=ReportingPolicy.IGNORE)
@Mapper(builder = Builder(disableBuilder = true),
  unmappedSourcePolicy= ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface LuckUserMapper:BasicMapperExtend<LuckUserDTO,LuckUserPageDTO,LuckUserBO,LuckUserPO,LuckUserVO> {

  companion object {
    val  MAPPER:LuckUserMapper = Mappers.getMapper(LuckUserMapper::class.java)
  }
}

