package com.example.demo.memo.unit

import com.example.demo.memo.controller.dtos.MemoDto
import com.example.demo.memo.controller.dtos.MemoResource
import com.example.demo.memo.controller.dtos.MemoResponse
import com.example.demo.memo.model.Memo
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class DtoTest {
    @Test
    @DisplayName("MemoDto")
    fun checkMemoDto() {
        //given
        var title: String = "test"
        var text: String = "test"
        //when
        val result = MemoDto(
            title = title,
            text = text
        )

        //then
        assertThat(result.text).isEqualTo(text)
        assertThat(result.title).isEqualTo(title)
    }

    @Test
    @DisplayName("MemoResponseDto")
    fun checkMemoResponseDto() {
        //given
        val memo = Memo(
            id = 1,
            title = "test",
            text = "test",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        //when
        val result = MemoResponse(memo)
        //then
        assertThat(result.memo.id).isEqualTo(memo.id)
        assertThat(result.memo.title).isEqualTo(memo.title)
        assertThat(result.memo.text).isEqualTo(memo.text)
        assertThat(result.memo.createdAt).isEqualTo(memo.createdAt)
        assertThat(result.memo.updatedAt).isEqualTo(memo.updatedAt)
    }

    @Test
    @DisplayName("MemoResource")
    fun checkMemoResource() {
        //given
        val memo = Memo(
            id = 1,
            title = "test",
            text = "test",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        val result = MemoResponse(memo)

        //when
        val resource = MemoResource.modelOf(result)
        //then
        assertThat(resource.links.hasLink("create")).isTrue()
        assertThat(resource.links.hasLink("getMemo")).isTrue()
        assertThat(resource.links.hasLink("updateMemo")).isTrue()
        assertThat(resource.links.hasLink("deleteMemo")).isTrue()
        assertThat(resource.links.hasLink("profile")).isTrue()
    }

}