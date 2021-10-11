package com.example.demo.memo.unit

import com.example.demo.memo.controller.dtos.MemoDto
import com.example.demo.memo.model.Memo
import com.example.demo.memo.repository.MemoRepository
import com.example.demo.memo.service.MemoServiceImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.*


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
        assertThat(createMemo.body?.content?.title).isEqualTo(findById.get().title)
        assertThat(createMemo.body?.content?.text).isEqualTo(findById.get().text)
        assertThat(createMemo.body?.content?.createdAt).isEqualTo(findById.get().createdAt)
        assertThat(createMemo.body?.content?.updatedAt).isEqualTo(findById.get().updatedAt)
    }
    private fun returnOptionalMemo(memo:Memo): Optional<Memo> {
        return Optional.of(memo)
    }

}