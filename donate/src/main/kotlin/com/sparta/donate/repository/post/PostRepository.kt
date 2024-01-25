package com.sparta.donate.repository.post

import com.sparta.donate.domain.post.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<Post, Long>