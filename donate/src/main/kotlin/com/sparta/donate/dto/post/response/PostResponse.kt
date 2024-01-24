package com.sparta.donate.dto.post.response

import java.time.LocalDateTime

data class PostResponse(
    val id: Long?,
    val title: String,
    val content: String,
    val member: String,
    val createdAt: LocalDateTime,
    val endedAt: LocalDateTime
)
