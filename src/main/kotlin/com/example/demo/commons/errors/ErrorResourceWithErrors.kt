package com.example.demo.commons.errors

import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.validation.Errors

class ErrorResourceWithErrors {
    companion object {
        fun modelOf(errors:Errors): EntityModel<Errors> {
            val model = EntityModel.of(errors)
            model.add(Link.of("http://localhost:8080/swagger-ui/index.html").withRel("profile"))
            return model
        }
    }
}