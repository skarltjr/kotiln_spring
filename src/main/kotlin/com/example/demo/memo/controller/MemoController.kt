package com.example.demo.memo.controller

import com.example.demo.memo.controller.dtos.MemoDeletedResDto
import com.example.demo.memo.controller.dtos.MemoDto
import com.example.demo.memo.controller.dtos.MemoResponseDto
import com.example.demo.memo.model.Memo
import com.example.demo.memo.service.MemoService
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

@RestController
@RequestMapping("/memos",produces = arrayOf(MediaTypes.HAL_JSON_VALUE))
class MemoController(
    private val memoService: MemoService
) {

    @PostMapping
    fun createMemo(@RequestBody memoDto: MemoDto): ResponseEntity<EntityModel<MemoResponseDto>> {
        return memoService.createMemo(memoDto)
    }

    @PatchMapping("/{id}")
    fun updateMemo(@PathVariable id: Long, @RequestBody memoDto: MemoDto): ResponseEntity<EntityModel<MemoResponseDto>> {
        return memoService.updateMemo(id, memoDto)
    }

    @DeleteMapping("/{id}")
    fun deleteMemo(@PathVariable id: Long): ResponseEntity<MemoDeletedResDto> {
        return memoService.deleteMemo(id)
    }

    @GetMapping("/{id}")
    fun getMemo(@PathVariable id: Long): ResponseEntity<EntityModel<MemoResponseDto>> {
        return memoService.getMemo(id)
    }

    @GetMapping
    fun getMemos(
        @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate,
        @PageableDefault(size = 5, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable,
        assembler:PagedResourcesAssembler<Memo>
    ): PagedModel<EntityModel<MemoResponseDto>> {
        return memoService.queryMemos(date,pageable,assembler)
    }
}