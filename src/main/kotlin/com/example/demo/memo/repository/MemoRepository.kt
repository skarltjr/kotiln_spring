package com.example.demo.memo.repository

import com.example.demo.memo.model.Memo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional(readOnly = true)
@Repository
interface MemoRepository : JpaRepository<Memo, Long> {
    fun findByCreatedAtBetween(from: LocalDateTime, to: LocalDateTime, pageable: Pageable): Page<Memo>
}