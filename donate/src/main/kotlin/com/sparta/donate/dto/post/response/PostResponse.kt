package com.sparta.donate.dto.post.response

data class PostResponse(
    val id: Long?,
    val title: String,
    val content: String,
    val member: String,
    val createdAt: String,
    val endedAt: String
)
