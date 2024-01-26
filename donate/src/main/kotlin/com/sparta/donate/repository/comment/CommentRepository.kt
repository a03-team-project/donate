package com.sparta.donate.repository.comment

import com.sparta.donate.domain.comment.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long>

