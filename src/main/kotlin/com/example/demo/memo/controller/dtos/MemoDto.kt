package com.example.demo.memo.controller.dtos

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

data class MemoDto(
    @field:ApiModelProperty(
        value = "String",
        example = "제목"
    )
    @NotBlank
    val title: String,

    @field:ApiModelProperty(
        value = "String",
        example = "내용"
    )
    @NotBlank
    val text: String


    ) {

}