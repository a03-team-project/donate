package com.sparta.donate.dto.post.request

data class CreatePostRequest(
    val title: String,
    val content: String,
    val nickname: String
)