package com.example.demo.memo.service

import com.example.demo.memo.controller.dtos.MemoDeletedResponse
import com.example.demo.memo.controller.dtos.MemoDto
import com.example.demo.memo.controller.dtos.MemoResponse
import com.example.demo.memo.model.Memo
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import java.time.LocalDate

interface MemoService {
    fun createMemo(memoDto: MemoDto): ResponseEntity<EntityModel<MemoResponse>>
    fun updateMemo(id: Long, memoDto: MemoDto): ResponseEntity<EntityModel<MemoResponse>>
    fun deleteMemo(id: Long): ResponseEntity<MemoDeletedResponse>
    fun getMemo(id: Long): ResponseEntity<EntityModel<MemoResponse>>
    fun queryMemos(date: LocalDate, pageable: Pageable, assembler: PagedResourcesAssembler<Memo>): PagedModel<EntityModel<MemoResponse>>
    fun returnErrors(errors: Errors): ResponseEntity<*>
}
