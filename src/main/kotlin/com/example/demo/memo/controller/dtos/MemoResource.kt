package com.example.demo.memo.controller.dtos

import com.example.demo.memo.controller.MemoController
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo


class MemoResource(var memoResponseDto: MemoResponseDto) : EntityModel<MemoResponseDto>() {
    companion object {
        fun modelOf(memoResponseDto: MemoResponseDto): EntityModel<MemoResponseDto> {
            val model = EntityModel.of(memoResponseDto)
            model.add(linkTo(MemoController::class.java).withRel("create"))
            model.add(linkTo(MemoController::class.java).slash(memoResponseDto.id).withRel("getMemo"))
            model.add(linkTo(MemoController::class.java).slash(memoResponseDto.id).withRel("updateMemo"))
            model.add(linkTo(MemoController::class.java).slash(memoResponseDto.id).withRel("deleteMemo"))
            return model
        }
    }
}