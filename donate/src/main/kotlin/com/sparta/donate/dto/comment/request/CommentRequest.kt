package com.sparta.donate.dto.comment.request

import jakarta.validation.constraints.NotBlank


data class CommentRequest(
    @field:NotBlank(message = "댓글을 입력해주세요")
    val content: String
)