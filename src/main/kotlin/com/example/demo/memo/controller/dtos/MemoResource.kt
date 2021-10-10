package com.example.demo.memo.controller.dtos

import com.example.demo.config.AppProperties
import com.example.demo.memo.controller.MemoController
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo



class MemoResource(
    var memoResponseDto: MemoResponseDto,
    private val appProperties: AppProperties
    ) : EntityModel<MemoResponseDto>()
{
    companion object {
        fun modelOf(memoResponseDto: MemoResponseDto): EntityModel<MemoResponseDto> {
            val model = EntityModel.of(memoResponseDto)
            model.add(linkTo(MemoController::class.java).withRel("create"))
            model.add(linkTo(MemoController::class.java).slash(memoResponseDto.id).withRel("getMemo"))
            model.add(linkTo(MemoController::class.java).slash(memoResponseDto.id).withRel("updateMemo"))
            model.add(linkTo(MemoController::class.java).slash(memoResponseDto.id).withRel("deleteMemo"))
            model.add(Link.of("http://localhost:8080/swagger-ui/index.html").withRel("profile"))
            return model
        }
    }
}