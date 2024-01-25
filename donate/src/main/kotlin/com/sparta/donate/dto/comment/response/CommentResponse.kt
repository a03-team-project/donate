package com.sparta.donate.dto.comment.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class CommentResponse(
    val nickname: String,
    val content: String,
    val donationAmount: Long?,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime
)
