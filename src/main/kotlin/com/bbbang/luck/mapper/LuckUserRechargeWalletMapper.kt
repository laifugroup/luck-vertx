package com.bbbang.luck.mapper;

import com.bbbang.luck.domain.dto.LuckUserRechargeWalletDTO
import com.bbbang.luck.domain.dto.LuckUserRechargeWalletPageDTO
import com.bbbang.luck.domain.vo.LuckUserRechargeWalletVO
import com.bbbang.luck.domain.bo.LuckUserRechargeWalletBO
import com.bbbang.luck.domain.po.LuckUserRechargeWalletPO
import com.bbbang.parent.mapper.BasicMapperExtend
import org.mapstruct.*
import org.mapstruct.factory.Mappers


@MapperConfig(unmappedTargetPolicy=ReportingPolicy.IGNORE)
@Mapper(builder = Builder(disableBuilder = true),
  unmappedSourcePolicy= ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface LuckUserRechargeWalletMapper:BasicMapperExtend<LuckUserRechargeWalletDTO,LuckUserRechargeWalletPageDTO,LuckUserRechargeWalletBO,LuckUserRechargeWalletPO,LuckUserRechargeWalletVO> {

  companion object {
    val  MAPPER:LuckUserRechargeWalletMapper = Mappers.getMapper(LuckUserRechargeWalletMapper::class.java)
  }
}

