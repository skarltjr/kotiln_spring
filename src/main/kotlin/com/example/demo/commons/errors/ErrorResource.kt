package com.example.demo.commons.errors

import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link

class ErrorResource : EntityModel<ErrorResponse>() {
    companion object {
        fun modelOf(errors:ErrorResponse):EntityModel<ErrorResponse> {
            val model = EntityModel.of(errors)
            model.add(Link.of("http://localhost:8080/swagger-ui/index.html").withRel("profile"))
            return model
        }
    }
}