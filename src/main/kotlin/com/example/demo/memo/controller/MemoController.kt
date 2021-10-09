package com.example.demo.memo.controller

import com.example.demo.memo.controller.dtos.MemoDto
import com.example.demo.memo.controller.dtos.MemoResponseDto
import com.example.demo.memo.service.MemoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/memos")
class MemoController(
    private val memoService: MemoService
){

    @PostMapping
    fun createMemo(@RequestBody memoDto: MemoDto): ResponseEntity<MemoResponseDto> {
        return memoService.createMemo(memoDto)
    }

    @PutMapping("/{id}")
    fun updateMemo(@PathVariable id: Long,@RequestBody memoDto: MemoDto): ResponseEntity<MemoResponseDto> {
        return memoService.updateMemo(id,memoDto)
    }
}