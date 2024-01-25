package com.sparta.donate.application.comment

import com.sparta.donate.domain.comment.Comment
import com.sparta.donate.dto.comment.request.CommentRequest
import com.sparta.donate.dto.comment.response.CommentResponse
import com.sparta.donate.global.auth.AuthenticationUtil.getAuthenticationUserId
import com.sparta.donate.repository.comment.CommentRepository
import com.sparta.donate.repository.donate.DonateRepository
import com.sparta.donate.repository.member.MemberRepository
import com.sparta.donate.repository.post.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val memberRepository: MemberRepository,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val donateRepository: DonateRepository
) {

    @Transactional
    fun createComment(postId: Long, request: CommentRequest): CommentResponse {
        val authenticatedId = getAuthenticationUserId()
        val post = postRepository.findByIdOrNull(postId) ?: throw IllegalStateException()
        val member = memberRepository.findByIdOrNull(authenticatedId)!!
        val donate = donateRepository.findByMemberIdAndPostId(authenticatedId, postId)
        val donateAmount = donate?.sumOf { it.amount } ?: 0
        val comment = Comment.of(request, member, post)

        commentRepository.save(comment)

        return comment.from(donateAmount)
    }


    @Transactional
    fun updateComment(commentId: Long, request: CommentRequest): CommentResponse {
        val authenticatedId = getAuthenticationUserId()
        val comment = getByIdOrNull(commentId)

        comment.update(request.content, authenticatedId)

        return comment.from()
    }

    @Transactional
    fun deleteComment(postId: Long, commentId: Long) {
        val authenticatedId = getAuthenticationUserId()
        val comment = getByIdOrNull(commentId)

        if (comment.verify(authenticatedId)) {
            commentRepository.delete(comment)
        }
    }

    fun getByIdOrNull(commentId: Long) = commentRepository.findByIdOrNull(commentId) ?: TODO("NoSuchEntityException()")

}
