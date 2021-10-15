package com.example.demo.memo.controller.dtos


import com.example.demo.memo.model.Memo
import java.time.LocalDateTime

data class MemoResponse(
    val memo:Memo
)