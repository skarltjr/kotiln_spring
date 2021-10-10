package com.example.demo.memo.controller.dtos

import java.time.LocalDateTime


data class MemoResDto(
    var id: Long? = null,
    var title:String,
    var text:String,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime? = null
)