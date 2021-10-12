package com.example.demo.memo

import com.example.demo.memo.controller.dtos.MemoDto
import com.example.demo.memo.model.Memo
import com.example.demo.memo.repository.MemoRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.hateoas.MediaTypes
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTest() {

    
    @Autowired
    lateinit var memoRepository: MemoRepository
    @Autowired
    lateinit var mockmvc:MockMvc
    @Autowired
    lateinit var mapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        memoRepository.deleteAll()
    }

    @Test
    @DisplayName("메모 생성")
    fun createMemo() {
        mockmvc.perform(post("/memos")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaTypes.HAL_JSON)
            .content(mapper.writeValueAsString(createMemoDto())))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("id").exists())
    }

    @Test
    @DisplayName("메모 수정")
    fun updateMemo() {
        val temp = createTempMemo()
        mockmvc.perform(patch("/memos/{id}",temp.id)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaTypes.HAL_JSON)
            .content(mapper.writeValueAsString(memoDtoForUpdate())))
            .andExpect(status().isOk)
            .andExpect(jsonPath("id").value(temp.id))
            .andExpect(jsonPath("title").value("updated"))
            .andExpect(jsonPath("text").value("updated"))
    }

    @Test
    @DisplayName("메모 수정 실패 - not found ")
    fun failUpdateWith404() {
        val memo = createTempMemo()
        var anotherId = 99
        mockmvc.perform(
            patch("/memos/{id}", anotherId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(mapper.writeValueAsString(memoDtoForUpdate()))
        )
            .andExpect(status().isNotFound)
    }

    @Test
    @DisplayName("메모 삭제")
    fun removeMemo() {
        val memo = createTempMemo()
        mockmvc.perform(delete("/memos/{id}", memo.id))
            .andExpect(status().isOk)
    }

    @Test
    @DisplayName("메모 삭제 - 실패")
    fun failToRemove() {
        val memo = createTempMemo()
        var anotherId = 99
        mockmvc.perform(delete("/memos/{id}", anotherId))
            .andExpect(status().isNotFound)
    }

    @Test
    @DisplayName("메모 단건 조회")
    fun getMemo() {
        val memo = createTempMemo()
        mockmvc.perform(get("/memos/{id}", memo.id))
            .andExpect(status().isOk)
            .andExpect(jsonPath("id").value(memo.id))
            .andExpect(jsonPath("title").value("title"))
            .andExpect(jsonPath("text").value("text"))
    }

    @Test
    @DisplayName("메모 단건 조회 - 실패")
    fun failToGetMemo() {
        val memo = createTempMemo()
        var anotherId = 99
        mockmvc.perform(get("/memos/{id}", anotherId))
            .andExpect(status().isNotFound)
    }

    @Test
    @DisplayName("메모 리스트 조회")
    fun queryMemos() {
        listSetUp()
        mockmvc.perform(get("/memos")
            .param("date", LocalDate.now().toString()))
            .andExpect(status().isOk)
            .andExpect(jsonPath("page.size").value(5))
            .andExpect(jsonPath("page.totalElements").value(10))
            .andExpect(jsonPath("page.totalPages").value(2))
    }

    @Test
    @DisplayName("메모 조회 시 선택 일자에 해당하는 리스트만 가져온다")
    fun queryMemosWithSpecificDay() {
        listSetUpWithAnotherDay()
        mockmvc.perform(get("/memos")
            .param("date", LocalDate.now().toString()))
            .andExpect(status().isOk)
            .andExpect(jsonPath("page.size").value(5))
            .andExpect(jsonPath("page.totalElements").value(0))
            .andExpect(jsonPath("page.totalPages").value(0))
    }

    private fun listSetUpWithAnotherDay() {
        for (i in 1..10 step (1)) {
            if (i % 2 == 0) {
                memoRepository.save(
                    Memo(
                        title = "test" + i,
                        text = "test" + i,
                        createdAt = LocalDateTime.now().plusDays(1)
                    )
                )
            } else {
                memoRepository.save(
                    Memo(
                        title = "test" + i,
                        text = "test" + i,
                        createdAt = LocalDateTime.now().minusDays(1)
                    )
                )
            }
        }
    }

    private fun listSetUp() {
        for (i in 1..10 step (1)) {
            memoRepository.save(
                Memo(
                    title = "test"+i,
                    text = "test"+i,
                    createdAt = LocalDateTime.now()
                )
            )
        }
    }


    private fun memoDtoForUpdate(): MemoDto {
        return MemoDto(
            title = "updated",
            text="updated"
        )
    }

    private fun createMemoDto(): MemoDto {
        return MemoDto(title = "title", text = "text")
    }

    private fun createTempMemo():Memo {
        return memoRepository.save(Memo(
            title = "title",
            text="text",
            createdAt = LocalDateTime.now()
        ))
    }
}