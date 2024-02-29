package com.bbbang.luck.mapper;

import com.bbbang.luck.domain.dto.LuckCreditApplyDTO
import com.bbbang.luck.domain.dto.LuckCreditApplyPageDTO
import com.bbbang.luck.domain.vo.LuckCreditApplyVO
import com.bbbang.luck.domain.bo.LuckCreditApplyBO
import com.bbbang.luck.domain.po.LuckCreditApplyPO
import com.bbbang.parent.mapper.BasicMapperExtend
import org.mapstruct.*
import org.mapstruct.factory.Mappers


@MapperConfig(unmappedTargetPolicy=ReportingPolicy.IGNORE)
@Mapper(builder = Builder(disableBuilder = true),
  unmappedSourcePolicy= ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface LuckCreditApplyMapper:BasicMapperExtend<LuckCreditApplyDTO,LuckCreditApplyPageDTO,LuckCreditApplyBO,LuckCreditApplyPO,LuckCreditApplyVO> {

  companion object {
    val  MAPPER:LuckCreditApplyMapper = Mappers.getMapper(LuckCreditApplyMapper::class.java)
  }
}

