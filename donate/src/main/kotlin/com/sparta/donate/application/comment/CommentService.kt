package com.sparta.donate.application.comment

import com.sparta.donate.domain.comment.Comment
import com.sparta.donate.dto.comment.request.CommentRequest
import com.sparta.donate.dto.comment.response.CommentResponse
import com.sparta.donate.repository.member.MemberRepository
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val memberRepository: MemberRepository
) {

    fun createComment(request: CommentRequest): CommentResponse {
        // TODO: member, post 엔티티 받는 로직 구현하기
        Comment.toEntity(request, member, post)

    }
}
