package com.sparta.donate.dto.post.request

import com.sparta.donate.domain.member.Member
import com.sparta.donate.domain.post.Post

data class CreatePostRequest(
    val title: String,
    val content: String,
    val member: Member
) {

    fun toPost(): Post {

        return Post(
            _title = title,
            _content = content,
            _member = member

        )
    }
}