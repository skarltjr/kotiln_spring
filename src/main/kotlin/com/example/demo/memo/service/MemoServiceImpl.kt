package com.example.demo.memo.service

import com.example.demo.memo.controller.dtos.MemoDeletedResDto
import com.example.demo.memo.controller.dtos.MemoDto
import com.example.demo.memo.controller.dtos.MemoResponseDto
import com.example.demo.memo.model.Memo
import com.example.demo.memo.repository.MemoRepository
import javassist.NotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@Service
@Transactional
class MemoServiceImpl(
    private val memoRepository: MemoRepository
) : MemoService
{
    override fun createMemo(memoDto: MemoDto): ResponseEntity<MemoResponseDto> {
        val created = memoRepository.save(
            Memo(
                title = memoDto.title,
                text = memoDto.text,
                createdAt = LocalDateTime.now()
            )
        )

        return ResponseEntity.ok(MemoResponseDto(created.id, created.title, created.text, created.createdAt, created.updatedAt))
    }

    override fun updateMemo(id: Long, memoDto: MemoDto): ResponseEntity<MemoResponseDto> {
        val found = memoRepository.findById(id)
        if (found.isPresent) {
            val memo = found.get()
            memo.text = memoDto.text
            memo.title = memoDto.title
            memo.updatedAt = LocalDateTime.now()
            val saved = memoRepository.save(memo)
            return ResponseEntity.ok(MemoResponseDto(
                saved.id,saved.title,saved.text,saved.createdAt,saved.updatedAt
            ))
        } else {
            return ResponseEntity.notFound().build()
        }
    }

    override fun deleteMemo(id: Long): ResponseEntity<MemoDeletedResDto> {
        val found = memoRepository.findById(id)
        if (found.isPresent) {
            var tempId = found.get().id
            memoRepository.deleteById(id)
            return ResponseEntity.ok(MemoDeletedResDto(tempId))
        } else {
            return ResponseEntity.notFound().build()
        }
    }

    override fun getMemo(id: Long): ResponseEntity<MemoResponseDto> {
        val found = memoRepository.findById(id)
        if (found.isPresent) {
            val get = found.get()
            return ResponseEntity.ok(MemoResponseDto(get.id,get.title,get.text,get.createdAt,get.updatedAt))
        } else {
            return ResponseEntity.notFound().build()
        }
    }

    override fun queryMemos(date: LocalDate, pageable: Pageable): ResponseEntity<Page<Memo>> {
        var from:LocalDateTime =date.atStartOfDay()
        var to:LocalDateTime =from.plusDays(1)
        var memos:Page<Memo> = memoRepository.findByCreatedAtBetween(from,to,pageable)
        return ResponseEntity.ok(memos)
    }
}