package com.example.demo.memo.unit

import com.example.demo.memo.controller.dtos.MemoDto
import com.example.demo.memo.model.Memo
import com.example.demo.memo.repository.MemoRepository
import com.example.demo.memo.service.MemoServiceImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.BDDMockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.*
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.lang.Nullable
import org.springframework.web.util.UriComponents
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import org.springframework.web.util.UriComponentsBuilder





@ExtendWith(MockitoExtension::class)
class ServiceTest {
    @InjectMocks
    @Spy
    lateinit var memoService:MemoServiceImpl
    @Mock
    lateinit var memoRepository: MemoRepository


    @Test
    @DisplayName("createMemo")
    fun createMemo() {
        val memoDto = MemoDto(
            title = "test",
            text = "test"
        )
        val memo = Memo(
            id = 1L,
            title = memoDto.title,
            text = memoDto.text,
            createdAt = LocalDateTime.now(),
            updatedAt = null
        )

        // service => 사용하는 repository에 대해선 모킹을 해줘야한다
        given(memoRepository.save(BDDMockito.any())).willReturn(memo) // save에 대해
        given(memoRepository.findById(1)).willReturn(returnOptionalMemo(memo)) // find에 대해

        //when
        val createMemo = memoService.createMemo(memoDto)
        val findById = memoRepository.findById(1)
        //then
        assertThat(createMemo.body?.content?.id).isEqualTo(findById.get().id)
        assertThat(createMemo.body?.content?.id).isEqualTo(memo.id)

        assertThat(createMemo.body?.content?.title).isEqualTo(findById.get().title)
        assertThat(createMemo.body?.content?.title).isEqualTo(memo.title)

        assertThat(createMemo.body?.content?.text).isEqualTo(findById.get().text)
        assertThat(createMemo.body?.content?.text).isEqualTo(memo.text)
    }


    @Test
    @DisplayName("updateMemo")
    fun updateMemo() {
        val memoDto = MemoDto(
            title = "test",
            text = "test"
        )
        val memo = Memo(
            id = 1L,
            title = memoDto.title,
            text = memoDto.text,
            createdAt = LocalDateTime.now().minusDays(1),
            updatedAt = null
        )
        val updateDto = MemoDto(
            title = "update",
            text = "update"
        )
        val updatedMemo = Memo(
            id = 1L,
            title = updateDto.title,
            text = updateDto.text,
            createdAt = memo.createdAt,
            updatedAt = LocalDateTime.now()
        )

//        given(memoRepository.save(BDDMockito.eq(memo))).willReturn(memo)
        given(memoRepository.findById(1)).willReturn(returnOptionalMemo(memo)) // find에 대해
        given(memoRepository.save(BDDMockito.any())).willReturn(updatedMemo)


        val updated = memoService.updateMemo(1, updateDto)

        assertThat(updated.body?.content?.text).isEqualTo(updateDto.text)
        assertThat(updated.body?.content?.title).isEqualTo(updateDto.title)
        assertThat(updated.body?.content?.createdAt).isEqualTo(memo.createdAt)

    }

    @Test
    @DisplayName("deleteMemo")
    fun deleteMemo() {
        val memo = createTempMemo()

        given(memoRepository.findById(memo.id!!)).willReturn(returnOptionalMemo(memo))// find에 대해
        memoService.deleteMemo(memo.id!!)

        Mockito.verify(memoService, times(1)).deleteMemo(ArgumentMatchers.eq(memo.id)!!)
    }

    @Test
    @DisplayName("getMemo")
    fun getMemo() {
        val memo = createTempMemo()

        given(memoRepository.findById(memo.id!!)).willReturn(returnOptionalMemo(memo)) // find에 대해
        val found = memoService.getMemo(memo.id!!)

        assertThat(found.body?.content?.id).isEqualTo(memo.id)
        Mockito.verify(memoService, times(1)).getMemo(ArgumentMatchers.eq(memo.id!!))

    }

    @Test
    @DisplayName("queryMemos")
    fun queryMemos() {
        var memos: Page<Memo> = generateMemos()
        var from:LocalDateTime = LocalDate.now().atStartOfDay()
        var to:LocalDateTime = from.plusDays(1)
        val pageable: Pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending())
        given(memoRepository.findByCreatedAtBetween(from,to,pageable)).willReturn(memos)

        var date:LocalDate = LocalDate.now()

        val resolver: HateoasPageableHandlerMethodArgumentResolver = HateoasPageableHandlerMethodArgumentResolver()
        val baseUri = UriComponentsBuilder.fromUriString("http://localhost:8080").build()
        var assembler:PagedResourcesAssembler<Memo> = PagedResourcesAssembler<Memo>(resolver,baseUri)

        val result = memoService.queryMemos(date, pageable, assembler)

        assertThat(result.content.size).isEqualTo(5)
        val toMutableSet = result.content.toMutableSet()

        var index = 1
        for (x in toMutableSet) {
            x.content?.id = index.toLong()
            index++
        }
        assertThat(toMutableSet)
    }

    private fun generateMemos(): Page<Memo> {
        var list: MutableList<Memo> = mutableListOf()
        for (i in 1..5 step (1)) {
            val memo = Memo(
                id = i.toLong(),
                title = "title ${i}",
                text = "text ${i}",
                createdAt = LocalDateTime.now(),
                updatedAt = null
            )
            list.add(memo)
        }
        return PageImpl<Memo>(list)
    }

    private fun createTempMemo():Memo {
        val memoDto = MemoDto(
            title = "test",
            text = "test"
        )
        val memo = Memo(
            id = 1L,
            title = memoDto.title,
            text = memoDto.text,
            createdAt = LocalDateTime.now().minusDays(1),
            updatedAt = null
        )
        return memo
    }
    private fun returnOptionalMemo(memo:Memo): Optional<Memo> {
        return Optional.of(memo)
    }

}