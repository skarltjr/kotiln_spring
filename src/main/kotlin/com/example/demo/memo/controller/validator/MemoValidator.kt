package com.example.demo.memo.controller.validator

import com.example.demo.memo.controller.dtos.MemoDto
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Component
class MemoValidator : Validator {
    override fun supports(clazz: Class<*>): Boolean {
        return clazz.isAssignableFrom(MemoDto::class.java)
    }

    override fun validate(target: Any, errors: Errors) {
        if (target is MemoDto) {
            if (target.text == "") {
                errors.rejectValue("text","invalid text", arrayOf(target.text),"텍스트가 비어있는지 확인해주세요")
            }
            if (target.title == "") {
                errors.rejectValue("title","invalid title", arrayOf(target.title),"타이틀이 비어있는지 확인해주세요")

            }
        }
    }
}