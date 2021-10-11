package com.example.demo.memo.unit

import com.example.demo.memo.controller.dtos.MemoDto
import com.example.demo.memo.model.Memo
import com.example.demo.memo.repository.MemoRepository
import com.example.demo.memo.service.MemoServiceImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
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

        // given - stubbing 앞으로 이런게 불리면 이 값을 줘
        given(memoRepository.findById(1)).willReturn(returnOptionalMemo(1,memoDto))

        //when
        memoService.createMemo(memoDto)
        val findById = memoRepository.findById(1)
        //then 예측과 비교
        assertThat(findById).isEqualTo(returnOptionalMemo(1,memoDto))
    }
    private fun returnOptionalMemo(id: Long,memoDto: MemoDto): Optional<Memo> {
        return Optional.of(createMemo(id,memoDto))
    }

    private fun createMemo(id: Long, memoDto: MemoDto): Memo {
        return Memo(id,memoDto.title,memoDto.text, LocalDateTime.now())
    }
}