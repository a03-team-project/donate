package com.sparta.donate.dto.comment.response

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val nickname: String,
    val date: LocalDateTime,
    val content: String,
    val donationAmount: Long?
)
