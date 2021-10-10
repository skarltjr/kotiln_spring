package com.example.demo.memo.controller.dtos

import io.swagger.annotations.ApiModelProperty

data class MemoDto(
    @field:ApiModelProperty(
        value = "String",
        example = "제목"
    )
    var title: String,

    @field:ApiModelProperty(
        value = "String",
        example = "내용"
    )
    var text: String){

}