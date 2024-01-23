package com.sparta.donate.dto.post.response

import com.sparta.donate.domain.post.Post

data class PostResponse (
    var id: Long?,
    var title: String,
    var content: String,
    var member: String,
    var createdAt: String
) {
    companion object {
        fun toPostResponse(post: Post): PostResponse {
            return PostResponse(
                id = post.id,
                title = post.title,
                content = post.content,
                member = post.member.toString(),
                createdAt = post.createdAt.toString()
            )
        }

        fun toPostResponseList(posts: List<Post>): List<PostResponse> {
            return posts.map { toPostResponse(it) }
        }
    }
}