package com.example.demo.memo.controller

import com.example.demo.memo.controller.dtos.MemoDeletedResponse
import com.example.demo.memo.controller.dtos.MemoDto
import com.example.demo.memo.controller.dtos.MemoResponse
import com.example.demo.memo.controller.validator.MemoValidator
import com.example.demo.memo.model.Memo
import com.example.demo.memo.service.MemoService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors

import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import javax.validation.Valid

@Api(description = "Memo")
@RestController
@RequestMapping("/memos",produces = arrayOf(MediaTypes.HAL_JSON_VALUE))
class MemoController(
    private val memoService: MemoService,
    private val memoValidator: MemoValidator
) {

    @ApiOperation(value = "메모 생성")
    @PostMapping
    fun createMemo(@RequestBody @Valid memoDto: MemoDto,errors: Errors): ResponseEntity<*> {
        memoValidator.validate(memoDto,errors)
        if (errors.hasErrors()){return memoService.returnErrors(errors)}
        return memoService.createMemo(memoDto)
    }

    @ApiOperation(value = "메모 업데이트")
    @PatchMapping("/{id}")
    fun updateMemo(@PathVariable id: Long, @RequestBody @Valid memoDto: MemoDto, errors: Errors): ResponseEntity<*> {
        memoValidator.validate(memoDto,errors)
        if (errors.hasErrors()) { return memoService.returnErrors(errors) }
        return memoService.updateMemo(id, memoDto)
    }

    @ApiOperation(value = "메모 삭제")
    @DeleteMapping("/{id}")
    fun deleteMemo(@PathVariable id: Long): ResponseEntity<*> {
        return memoService.deleteMemo(id)
    }

    @ApiOperation(value = "메모 단건 조회")
    @GetMapping("/{id}")
    fun getMemo(@PathVariable id: Long): ResponseEntity<*> {
        return memoService.getMemo(id)
    }

    @ApiOperation(value = "특정 날짜 메모 조회")
    @GetMapping
    fun getMemos(
        @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate,
        @PageableDefault(size = 5, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable,
        assembler:PagedResourcesAssembler<Memo>
    ): PagedModel<*> {
        return memoService.queryMemos(date,pageable,assembler)
    }
}