package com.bbbang.luck.controller;



import com.bbbang.luck.domain.dto.LuckCreditApplyAuditDTO
import com.bbbang.luck.domain.dto.LuckCreditApplyDTO
import com.bbbang.luck.domain.dto.LuckCreditApplyPageDTO
import com.bbbang.luck.domain.vo.LuckCreditApplyVO
import com.bbbang.luck.mapper.LuckCreditApplyMapper
import com.bbbang.luck.service.LuckCreditApplyService
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

@Hidden
@Controller("/v1/credit_apply")
@Tag(name = "luck_credit_apply", description = "上下分申请记录")
@Introspected
@Validated
@Secured(value = [SecurityRules.IS_AUTHENTICATED])
 class LuckCreditApplyController(
  private val luckCreditApplyService: LuckCreditApplyService
 ) : BaseController() {


    @Operation(summary ="[更新]审核上下分申请")
    @Put("/audit")
    @SingleResult
    @Secured(value = [SecurityRules.IS_ADMIN])
    fun auditApplyCredit(@Valid @Body luckCreditApplyAuditDTO: LuckCreditApplyAuditDTO): Mono<Rsp<Unit>> {
        val result=luckCreditApplyService.audit(luckCreditApplyAuditDTO)
        return Rsp.success(result)
    }


    @Operation(summary ="[新增]创建单条记录")
    @Post()
    @SingleResult
	@Secured(value = [SecurityRules.IS_AUTHENTICATED])
    fun create(@Valid @Body luckCreditApplyDTO: LuckCreditApplyDTO): Mono<Rsp<LuckCreditApplyVO>> {
        val param=LuckCreditApplyMapper.MAPPER.dto2bo(luckCreditApplyDTO)
        val result=luckCreditApplyService.save(param)
        return Rsp.success(result)
    }
	
	@Operation(summary ="[新增]创建多条记录")
    @Post("/list")
    @SingleResult
	@Secured(value = [SecurityRules.IS_AUTHENTICATED])
    fun createList(@Valid @Body luckCreditApplyDTO: List<LuckCreditApplyDTO>): Mono<Rsp<List<LuckCreditApplyVO?>>> {
        val param=LuckCreditApplyMapper.MAPPER.dto2bo(luckCreditApplyDTO)
        val result=luckCreditApplyService.saveList(param)
        return Rsp.success(result)
    }
	
	
	/**
	* 权限:admin
	*/
    @Operation(summary ="[删除]彻底删除单条记录")
    @Delete()
    @SingleResult
	@Secured(value = [SecurityRules.IS_ADMIN])
    fun delete(@NotEmpty(message = "[id]不能为空")
			   @QueryValue("id")
			   @Parameter(description = "唯一ID",example = "1541693333435453441",required =  true)
			   id:Long):Mono<Rsp<Long>> {
        val result=luckCreditApplyService.delete(id)
        return Rsp.success(result)
    }
	/**
	* 权限:admin
	*/
	@Operation(summary ="[删除]彻底删除多条记录")
    @Delete("/list")
    @SingleResult
	@Secured(value = [SecurityRules.IS_ADMIN])
    fun delete(@NotEmpty(message = "[ids]不能为空")
			   @QueryValue
			   @Parameter(description = "唯一IDs",example = "1541693333435453441",required =  true)
			   ids:List<Long>):Mono<Rsp<Int>> {
        val result=luckCreditApplyService.delete(ids)
        return Rsp.success(result)
    }
	
    @Operation(summary ="[删除]逻辑删除单条记录")
    @Delete("/logic")
    @SingleResult
	@Secured(value = [SecurityRules.IS_AUTHENTICATED])
    fun deleteByLogic(@NotEmpty(message = "[id]不能为空")
			   @QueryValue("id")
			   @Parameter(description = "唯一ID",example = "1541693333435453441",required =  true)
			   id:Long):Mono<Rsp<Int>> {
        val result=luckCreditApplyService.deleteByLogic(id)
        return Rsp.success(result)
    }
	
	@Operation(summary ="[删除]逻辑删除多条记录")
    @Delete("/logic/list")
    @SingleResult
	@Secured(value = [SecurityRules.IS_AUTHENTICATED])
    fun deleteByLogic(@NotEmpty(message = "[ids]不能为空")
			   @QueryValue
			   @Parameter(description = "唯一IDs",example = "1541693333435453441",required =  true)
			   ids:List<Long>):Mono<Rsp<Int>> {
        val result=luckCreditApplyService.deleteByLogic(ids)
        return Rsp.success(result)
    }
	
	
	/**
	* 权限:admin
	*/
	@Operation(summary ="[更新]恢复逻辑删除记录")
    @Put("/logic")
    @SingleResult
	@Secured(value = [SecurityRules.IS_ADMIN])
    fun recoveryByLogic(@NotEmpty(message = "[id]不能为空")
			   @QueryValue("id")
			   @Parameter(description = "唯一ID",example = "1541693333435453441",required =  true)
			   id:Long):Mono<Rsp<Int>> {
        val result=luckCreditApplyService.recoveryByLogic(id)
        return Rsp.success(result)
    }
	/**
	* 权限:admin
	*/
	@Operation(summary ="[更新]恢复逻辑删除记录")
    @Put("/logic/list")
    @SingleResult
	@Secured(value = [SecurityRules.IS_ADMIN])
    fun recoveryByLogic(@NotEmpty(message = "[ids]不能为空")
			   @QueryValue
			   @Parameter(description = "唯一IDs",example = "1541693333435453441",required =  true)
			   ids:List<Long>):Mono<Rsp<Int>> {
        val result=luckCreditApplyService.recoveryByLogic(ids)
        return Rsp.success(result)
    }
	


    @Operation(summary ="[更新]更新记录")
    @Put
	@Secured(value = [SecurityRules.IS_AUTHENTICATED])
    fun update(
				@NotEmpty(message = "[id]不能为空")
			    @QueryValue
			    @Parameter(description = "唯一ID",example = "1541693333435453441",required =  true)
				id:Long,
				@Valid @Body luckCreditApplyPageDTO: LuckCreditApplyPageDTO): Mono<Rsp<LuckCreditApplyVO?>> {
        val param=LuckCreditApplyMapper.MAPPER.pageDto2bo(luckCreditApplyPageDTO)
           
        val result=luckCreditApplyService.update(id,param)
        return Rsp.success(result)
    }
	
	
	@Operation(summary ="[查询]id查询单条记录")
    @Get()
	@Secured(value = [SecurityRules.IS_AUTHENTICATED])
    fun getRecord(
				@NotEmpty(message = "[id]不能为空")
			    @QueryValue("id")
			    @Parameter(description = "唯一ID",example = "1541693333435453441",required =  true)
				id:Long):Mono<Rsp<LuckCreditApplyVO?>> {
        val result=luckCreditApplyService.findById(id)
        return Rsp.success(result)
    }



    @Operation(summary ="[查询]分页查询记录")
    @Get("/page{?pageDTO*}{?pageable*}")
	@Secured(value = [SecurityRules.IS_AUTHENTICATED])
    fun getPageList(@QueryValue pageable: PageableDTO,@QueryValue pageDTO:LuckCreditApplyPageDTO):Mono<Rsp<RspPagination<LuckCreditApplyVO?>>> {
        val param=LuckCreditApplyMapper.MAPPER.pageDto2bo(pageDTO)
        val result=luckCreditApplyService.findPage(param,pageable.into())
        return Rsp.success(result)
    }


	/**
	* 权限:admin
	*/
    @Operation(summary ="[查询]分页查询[含逻辑删除]记录")
    @Get("/page/logic{?pageDTO*}{?pageable*}")
	@Secured(value = [SecurityRules.IS_ADMIN])
    fun getPageLogicList(@QueryValue pageable:PageableDTO,@QueryValue  pageDTO:LuckCreditApplyPageDTO):Mono<Rsp<RspPagination<LuckCreditApplyVO?>>>  {
        val param=LuckCreditApplyMapper.MAPPER.pageDto2bo(pageDTO)
        val result=luckCreditApplyService.findUnLimitPage(param,pageable.into())
        return Rsp.success(result)
    }

	/**
	* 权限:admin
	*/
    @Operation(summary ="[查询]条件查询所有记录")
    @Get("/all/list{?pageDTO*}")
	@Secured(value = [SecurityRules.IS_ADMIN])
    fun  getList(@QueryValue pageDTO:LuckCreditApplyPageDTO):Mono<Rsp<List<LuckCreditApplyVO?>>> {
       val param=LuckCreditApplyMapper.MAPPER.pageDto2bo(pageDTO)
        val result=luckCreditApplyService.findLimitAll(param)
        return Rsp.success(result)
    }
	
	/**
	* 权限:admin
	*/
	@Operation(summary ="[查询]查询所有记录")
    @Get("/all")
	@Secured(value = [SecurityRules.IS_ADMIN])
    fun  getAll():Mono<Rsp<List<LuckCreditApplyVO?>>> {
        val result=luckCreditApplyService.findLimitAll()
        return Rsp.success(result)
    }

	/**
	* 权限:admin
	*/
    @Operation(summary ="[查询]查询所有[含逻辑删除]记录")
    @Get("/all/unLimit")
	@Secured(value = [SecurityRules.IS_ADMIN])
    fun getAllLogicList(@Nullable authentication:Authentication):Mono<Rsp<List<LuckCreditApplyVO?>> > {
        val result=luckCreditApplyService.findUnLimitAll()
        return Rsp.success(result)
    }


}

