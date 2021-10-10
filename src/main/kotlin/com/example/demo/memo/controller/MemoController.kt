package com.example.demo.memo.controller

import com.example.demo.memo.controller.dtos.MemoDeletedResDto
import com.example.demo.memo.controller.dtos.MemoDto
import com.example.demo.memo.controller.dtos.MemoResponseDto
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
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@Api(description = "Memo")
@RestController
@RequestMapping("/memos",produces = arrayOf(MediaTypes.HAL_JSON_VALUE))
class MemoController(
    private val memoService: MemoService
) {

    @ApiOperation(value = "메모 생성")
    @PostMapping
    fun createMemo(@RequestBody memoDto: MemoDto): ResponseEntity<EntityModel<MemoResponseDto>> {
        return memoService.createMemo(memoDto)
    }

    @ApiOperation(value = "메모 업데이트")
    @PatchMapping("/{id}")
    fun updateMemo(@PathVariable id: Long, @RequestBody memoDto: MemoDto): ResponseEntity<EntityModel<MemoResponseDto>> {
        return memoService.updateMemo(id, memoDto)
    }

    @ApiOperation(value = "메모 삭제")
    @DeleteMapping("/{id}")
    fun deleteMemo(@PathVariable id: Long): ResponseEntity<MemoDeletedResDto> {
        return memoService.deleteMemo(id)
    }

    @ApiOperation(value = "메모 단건 조회")
    @GetMapping("/{id}")
    fun getMemo(@PathVariable id: Long): ResponseEntity<EntityModel<MemoResponseDto>> {
        return memoService.getMemo(id)
    }

    @ApiOperation(value = "특정 날짜 메모 조회")
    @GetMapping
    fun getMemos(
        @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate,
        @PageableDefault(size = 5, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable,
        assembler:PagedResourcesAssembler<Memo>
    ): PagedModel<EntityModel<MemoResponseDto>> {
        return memoService.queryMemos(date,pageable,assembler)
    }
}