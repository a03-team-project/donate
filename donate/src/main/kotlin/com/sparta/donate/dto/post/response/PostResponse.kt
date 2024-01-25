package com.sparta.donate.dto.post.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class PostResponse(
    val id: Long?,
    val title: String,
    val content: String,
    val member: String,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val endedAt: LocalDateTime
)
