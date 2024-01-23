package com.sparta.donate.dto.comment.request


data class CommentRequest(
    val memberId: Long,
    val content: String
)