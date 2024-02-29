package com.bbbang.luck.controller;



import com.bbbang.luck.domain.dto.LuckBotDTO
import com.bbbang.luck.domain.dto.LuckBotPageDTO
import com.bbbang.luck.domain.type.LuckUserRoleType
import com.bbbang.luck.domain.vo.LuckBotVO
import com.bbbang.luck.domain.vo.LuckRoleVO
import com.bbbang.luck.mapper.LuckBotMapper
import com.bbbang.luck.service.LuckBotService
import com.bbbang.luck.service.LuckUserRoleService
import com.bbbang.parent.controller.BaseController
import com.bbbang.parent.entities.PageableDTO
import com.bbbang.parent.entities.Rsp
import com.bbbang.parent.entities.RspPagination
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.async.annotation.SingleResult
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.validation.Validated
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import reactor.core.publisher.Mono
import io.micronaut.core.annotation.Nullable
import io.micronaut.security.authentication.Authentication
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import com.bbbang.parent.rule.SecurityRules
import io.swagger.v3.oas.annotations.Hidden

@Controller("/v1/role")
@Tag(name = "luck_role", description = "Luck角色")
@Introspected
@Validated
@Secured(value = [SecurityRules.IS_AUTHENTICATED])
 class LuckRoleController(
  private val luckUserRoleService: LuckUserRoleService
 ) : BaseController() {



	/**
	* 权限:admin
	*/
    @Operation(summary ="[查询]查询所有记录")
    @Get("/all/list")
	@Secured(value = [SecurityRules.IS_ANONYMOUS])
    fun  getList():Mono<Rsp<List<LuckRoleVO>>> {
        val result=luckUserRoleService.getRoleList()
        return Rsp.success(result)
    }
	



}

