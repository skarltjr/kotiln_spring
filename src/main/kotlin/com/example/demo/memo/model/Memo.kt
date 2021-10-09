package com.example.demo.memo.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Lob


@Entity
class Memo(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var title:String,
    @Lob
    var text:String,
    var createdAt:LocalDateTime,
    var updatedAt:LocalDateTime? = null
)