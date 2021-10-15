package com.example.demo.memo.controller.dtos

import com.example.demo.config.AppProperties
import com.example.demo.memo.controller.MemoController
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo



class MemoResource: EntityModel<MemoResponse>()
{
    companion object {
        fun modelOf(memoResponse: MemoResponse): EntityModel<MemoResponse> {
            val model = EntityModel.of(memoResponse)
            model.add(linkTo(MemoController::class.java).withRel("create"))
            model.add(linkTo(MemoController::class.java).slash(memoResponse.memo.id).withRel("getMemo"))
            model.add(linkTo(MemoController::class.java).slash(memoResponse.memo.id).withRel("updateMemo"))
            model.add(linkTo(MemoController::class.java).slash(memoResponse.memo.id).withRel("deleteMemo"))
            model.add(Link.of("http://localhost:8080/swagger-ui/index.html").withRel("profile"))
            return model
        }
    }
}