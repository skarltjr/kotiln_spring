package com.example.demo.memo.service

import com.example.demo.memo.controller.dtos.MemoDto
import com.example.demo.memo.controller.dtos.MemoResponseDto
import org.springframework.http.ResponseEntity

interface MemoService {
    fun createMemo(memoDto: MemoDto): ResponseEntity<MemoResponseDto>
    fun updateMemo(id: Long, memoDto: MemoDto): ResponseEntity<MemoResponseDto>
}