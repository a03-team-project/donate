package com.sparta.donate.repository.comment

import com.sparta.donate.domain.comment.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long>{
    fun findByPostId(postId: Long): List<Comment>

    fun findByMemberIdAndPostId(memberId: Long, postId: Long): List<Comment>
}