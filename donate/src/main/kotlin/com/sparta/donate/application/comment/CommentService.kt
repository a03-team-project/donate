package com.sparta.donate.application.comment

import com.sparta.donate.domain.comment.Comment
import com.sparta.donate.dto.comment.request.CommentRequest
import com.sparta.donate.dto.comment.response.CommentResponse
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
        // TODO: 로그인 한 사용자만 댓글 작성 가능해야 함
        val post = postRepository.findByIdOrNull(postId) ?: throw IllegalStateException()
        val member = memberRepository.findByIdOrNull(request.memberId) ?: throw IllegalStateException()
        val donationAmount: Long = donateRepository.findByMemberIdAndPostId(request.memberId, postId)?.amount ?: 0

        commentRepository.save(Comment.toEntity(request, member, post))
            .let { return it.toResponse(donationAmount) }
    }

    @Transactional
    fun updateComment(postId: Long, commentId: Long, request: CommentRequest): CommentResponse{
        // TODO: 댓글을 작성한 사용자만 댓글 수정 가능해야 함
        val comment = getByIdOrNull(commentId)

        comment.update(request.content)

        return comment.toResponse()
    }

    @Transactional
    fun deleteComment(postId: Long, commentId: Long){
        // TODO: 댓글을 작성한 사용자만 댓글 삭제가 가능해야 함
        val commentToDelete = getByIdOrNull(commentId)
        commentRepository.delete(commentToDelete)
    }

    fun getByIdOrNull(commentId: Long) = commentRepository.findByIdOrNull(commentId) ?: TODO("NoSuchEntityException()")

}

