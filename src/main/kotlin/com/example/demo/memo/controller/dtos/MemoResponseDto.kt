package com.example.demo.memo.controller.dtos


import java.time.LocalDateTime

data class MemoResponseDto(
    var id: Long?,
    var title: String,
    var text: String,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime? = null
)