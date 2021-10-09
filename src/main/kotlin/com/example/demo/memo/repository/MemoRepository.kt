package com.example.demo.memo.repository

import com.example.demo.memo.model.Memo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Repository
interface MemoRepository : JpaRepository<Memo, Long> {
}