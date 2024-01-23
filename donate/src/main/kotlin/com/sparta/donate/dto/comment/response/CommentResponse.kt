package com.sparta.donate.dto.comment.response

import java.time.LocalDate

data class CommentResponse(
    val id: Long,
    val nickname: String,
    val date: LocalDate,
    val content: String,
    val donationAmount: Long
)
