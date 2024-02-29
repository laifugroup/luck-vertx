package com.bbbang.luck.mapper;

import com.bbbang.luck.domain.dto.LuckWalletDTO
import com.bbbang.luck.domain.dto.LuckWalletPageDTO
import com.bbbang.luck.domain.vo.LuckWalletVO
import com.bbbang.luck.domain.bo.LuckWalletBO
import com.bbbang.luck.domain.po.LuckWalletPO
import com.bbbang.parent.mapper.BasicMapperExtend
import org.mapstruct.*
import org.mapstruct.factory.Mappers


@MapperConfig(unmappedTargetPolicy=ReportingPolicy.IGNORE)
@Mapper(builder = Builder(disableBuilder = true),
  unmappedSourcePolicy= ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface LuckWalletMapper:BasicMapperExtend<LuckWalletDTO,LuckWalletPageDTO,LuckWalletBO,LuckWalletPO,LuckWalletVO> {

  companion object {
    val  MAPPER:LuckWalletMapper = Mappers.getMapper(LuckWalletMapper::class.java)
  }
}

