package com.example.demo.memo.service

import com.example.demo.commons.errors.ErrorResourceWithErrors
import com.example.demo.commons.errors.ErrorResponse
import com.example.demo.memo.controller.MemoController
import com.example.demo.memo.controller.dtos.MemoDeletedResponse
import com.example.demo.memo.controller.dtos.MemoDto
import com.example.demo.memo.controller.dtos.MemoResource
import com.example.demo.memo.controller.dtos.MemoResponse
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
import org.springframework.validation.Errors
import java.time.LocalDate
import java.time.LocalDateTime

@Service
@Transactional
class MemoServiceImpl(
    private val memoRepository: MemoRepository
) : MemoService
{
    override fun createMemo(memoDto: MemoDto): ResponseEntity<EntityModel<MemoResponse>> {
        val created = memoRepository.save(
            Memo(
                title = memoDto.title,
                text = memoDto.text,
                createdAt = LocalDateTime.now()
            )
        )
        val resource = createMemoResource(created)
        val toUri = linkTo(MemoController::class.java).slash(resource.content?.memo?.id).toUri()
        return ResponseEntity.created(toUri).body(resource)
    }


    override fun updateMemo(id: Long, memoDto: MemoDto): ResponseEntity<EntityModel<MemoResponse>> {
        if (checkIfExist(id)) {
            val found = memoRepository.findById(id).get()
            val memo = found
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

    override fun deleteMemo(id: Long): ResponseEntity<MemoDeletedResponse> {
        if (checkIfExist(id)) {
            memoRepository.deleteById(id)
            return ResponseEntity.ok(MemoDeletedResponse(id))
        }else {
            return ResponseEntity.notFound().build()
        }
    }

    override fun getMemo(id: Long): ResponseEntity<EntityModel<MemoResponse>> {
        if (checkIfExist(id)) {
            val found = memoRepository.findById(id).get()
            val resource = createMemoResource(found)
            return ResponseEntity.ok(resource)
        }else {
            return ResponseEntity.notFound().build()
        }
    }

    override fun queryMemos(date: LocalDate, pageable: Pageable, assembler: PagedResourcesAssembler<Memo>): PagedModel<EntityModel<MemoResponse>> {
        var from:LocalDateTime =date.atStartOfDay()
        var to:LocalDateTime =from.plusDays(1)
        var memos:Page<Memo> = memoRepository.findByCreatedAtBetween(from,to,pageable)

        return assembler.toModel(memos) { m: Memo -> MemoResource.modelOf(createMemoResponse(m)) }
    }

    override fun returnErrors(errors: Errors): ResponseEntity<*> {
        val model = ErrorResourceWithErrors.modelOf(errors)
        return ResponseEntity.badRequest().body(model)
    }

    fun createMemoResource(memo: Memo): EntityModel<MemoResponse> {
        val memoResponseDto = createMemoResponse(memo)
        return MemoResource.modelOf(memoResponseDto)
    }

    fun createMemoResponse(memo: Memo):MemoResponse {
        return MemoResponse(memo)
    }

    fun checkIfExist(id: Long): Boolean {
        if (memoRepository.existsById(id)) {
            return true
        }
        return false
    }
}