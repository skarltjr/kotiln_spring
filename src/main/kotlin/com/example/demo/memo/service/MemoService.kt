package com.example.demo.memo.service

import com.example.demo.memo.controller.dtos.MemoDeletedResDto
import com.example.demo.memo.controller.dtos.MemoDto
import com.example.demo.memo.controller.dtos.MemoResponseDto
import com.example.demo.memo.model.Memo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import java.time.LocalDate

interface MemoService {
    fun createMemo(memoDto: MemoDto): ResponseEntity<MemoResponseDto>
    fun updateMemo(id: Long, memoDto: MemoDto): ResponseEntity<MemoResponseDto>
    fun deleteMemo(id: Long): ResponseEntity<MemoDeletedResDto>
    fun getMemo(id: Long): ResponseEntity<MemoResponseDto>
    fun queryMemos(date: LocalDate, pageable: Pageable): ResponseEntity<Page<Memo>>
}