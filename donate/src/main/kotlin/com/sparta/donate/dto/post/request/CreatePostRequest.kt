package com.sparta.donate.dto.post.request

import jakarta.validation.constraints.NotBlank

data class CreatePostRequest(
    @field:NotBlank(message = "제목을 입력해주세요")
    val title: String,
    @field:NotBlank(message = "내용을 입력해주세요")
    val content: String,
)