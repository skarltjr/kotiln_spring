package com.example.demo.commons.errors


import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.hateoas.EntityModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestController
@RestControllerAdvice
class GlobalControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleRequest(exception: MethodArgumentNotValidException):ResponseEntity<EntityModel<ErrorResponse>> {
        val builder = StringBuilder()
        exception.bindingResult.fieldErrors.forEach { it->
            builder.append("${it.field}의 입력된 ${it.rejectedValue}로 인해 에러발생, 참고 : ${it.defaultMessage}")
        }
        val errorResponse = ErrorResponse(HttpStatus.BAD_REQUEST, "invalid Error", builder.toString())
        val model = ErrorResource.modelOf(errorResponse)
        return ResponseEntity.badRequest().body(model)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleNotReadable(exception: HttpMessageNotReadableException): ResponseEntity<EntityModel<ErrorResponse>> {
        var message = "wrong request body"
        if (exception.cause is MissingKotlinParameterException) {
            message = "빈 값이 있는지 확인해주세요"
        }
        val response = ErrorResponse(HttpStatus.BAD_REQUEST, "NotReadable", message)
        val model = ErrorResource.modelOf(response)
        return ResponseEntity.badRequest().body(model)
    }
}
