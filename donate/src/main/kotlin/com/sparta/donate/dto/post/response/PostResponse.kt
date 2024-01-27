package com.sparta.donate.dto.post.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.sparta.donate.domain.comment.Comment
import com.sparta.donate.dto.comment.response.CommentResponse
import java.time.LocalDateTime

data class PostResponse(
    val id: Long?,
    val title: String,
    val content: String,
    val member: String,
    val commentList: List<CommentResponse>,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val endedAt: LocalDateTime
)
