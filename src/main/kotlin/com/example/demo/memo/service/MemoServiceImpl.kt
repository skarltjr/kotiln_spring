package com.example.demo.memo.service

import com.example.demo.memo.controller.MemoController
import com.example.demo.memo.controller.dtos.MemoDeletedResDto
import com.example.demo.memo.controller.dtos.MemoDto
import com.example.demo.memo.controller.dtos.MemoResource
import com.example.demo.memo.controller.dtos.MemoResponseDto
import com.example.demo.memo.model.Memo
import com.example.demo.memo.repository.MemoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
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
    override fun createMemo(memoDto: MemoDto): ResponseEntity<EntityModel<MemoResponseDto>> {
        val created = memoRepository.save(
            Memo(
                title = memoDto.title,
                text = memoDto.text,
                createdAt = LocalDateTime.now()
            )
        )
        val resource = createMemoResource(created)
        val toUri = linkTo(MemoController::class.java).slash(resource.content?.id).toUri()
        return ResponseEntity.created(toUri).body(resource)
    }


    override fun updateMemo(id: Long, memoDto: MemoDto): ResponseEntity<EntityModel<MemoResponseDto>> {
        val found = memoRepository.findById(id)
        if (found.isPresent) {
            val memo = found.get()
            memo.text = memoDto.text
            memo.title = memoDto.title
            memo.updatedAt = LocalDateTime.now()
            val saved = memoRepository.save(memo)

            val resource = createMemoResource(saved)
            return ResponseEntity.ok(resource)
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

    override fun getMemo(id: Long): ResponseEntity<EntityModel<MemoResponseDto>> {
        val found = memoRepository.findById(id)
        if (found.isPresent) {
            val get = found.get()
            val resource = createMemoResource(get)
            return ResponseEntity.ok(resource)

        } else {
            return ResponseEntity.notFound().build()
        }
    }

    override fun queryMemos(date: LocalDate, pageable: Pageable, assembler: PagedResourcesAssembler<Memo>): PagedModel<EntityModel<MemoResponseDto>> {
        var from:LocalDateTime =date.atStartOfDay()
        var to:LocalDateTime =from.plusDays(1)
        var memos:Page<Memo> = memoRepository.findByCreatedAtBetween(from,to,pageable)

        return assembler.toModel(memos) { m: Memo -> MemoResource.modelOf(createMemoResponseDto(m)) }
    }

    fun createMemoResource(memo: Memo): EntityModel<MemoResponseDto> {
        val memoResponseDto = MemoResponseDto(
            id = memo.id,
            title = memo.title,
            text = memo.text,
            createdAt = memo.createdAt,
            updatedAt = memo.updatedAt
        )
        return MemoResource.modelOf(memoResponseDto)
    }

    fun createMemoResponseDto(memo: Memo):MemoResponseDto {
        val memoResponseDto = MemoResponseDto(
            id = memo.id,
            title = memo.title,
            text = memo.text,
            createdAt = memo.createdAt,
            updatedAt = memo.updatedAt
        )
        return memoResponseDto
    }
}