package com.sparta.donate.repository.post

import com.sparta.donate.domain.post.Post
import com.sparta.donate.dto.post.response.PostResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<Post, Long> {
    fun findAllByOrderByCreatedAtDesc(): List<PostResponse>
    fun findAllByOrderByCreatedAtAsc(): List<PostResponse>

}