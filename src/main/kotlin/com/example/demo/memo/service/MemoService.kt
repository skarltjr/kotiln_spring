package com.example.demo.memo.service

import com.example.demo.memo.controller.dtos.MemoDeletedResDto
import com.example.demo.memo.controller.dtos.MemoDto
import com.example.demo.memo.controller.dtos.MemoResponseDto
import com.example.demo.memo.model.Memo
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import java.time.LocalDate

interface MemoService {
    fun createMemo(memoDto: MemoDto): ResponseEntity<EntityModel<MemoResponseDto>>
    fun updateMemo(id: Long, memoDto: MemoDto): ResponseEntity<EntityModel<MemoResponseDto>>
    fun deleteMemo(id: Long): ResponseEntity<MemoDeletedResDto>
    fun getMemo(id: Long): ResponseEntity<EntityModel<MemoResponseDto>>
    fun queryMemos(date: LocalDate, pageable: Pageable, assembler: PagedResourcesAssembler<Memo>): PagedModel<EntityModel<MemoResponseDto>>
}