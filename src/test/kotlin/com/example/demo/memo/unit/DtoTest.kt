package com.example.demo.memo.unit

import com.example.demo.memo.controller.dtos.MemoDto
import com.example.demo.memo.controller.dtos.MemoResource
import com.example.demo.memo.controller.dtos.MemoResponseDto
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
        var id:Long = 1
        var title: String = "test"
        var text: String = "test"
        var createdAt:LocalDateTime = LocalDateTime.now()
        var updatedAt:LocalDateTime = LocalDateTime.now()
        //when
        val result = MemoResponseDto(id, title, text, createdAt, updatedAt)
        //then
        assertThat(result.id).isEqualTo(id)
        assertThat(result.title).isEqualTo(title)
        assertThat(result.text).isEqualTo(text)
        assertThat(result.createdAt).isEqualTo(createdAt)
        assertThat(result.updatedAt).isEqualTo(updatedAt)
    }

    @Test
    @DisplayName("MemoResource")
    fun checkMemoResource() {
        //given
        var id:Long = 1
        var title: String = "test"
        var text: String = "test"
        var createdAt:LocalDateTime = LocalDateTime.now()
        var updatedAt:LocalDateTime = LocalDateTime.now()
        val result = MemoResponseDto(id, title, text, createdAt, updatedAt)

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