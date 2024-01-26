package com.sparta.donate.application.post

import com.sparta.donate.domain.post.Post
import com.sparta.donate.dto.post.request.CreatePostRequest
import com.sparta.donate.dto.post.request.UpdatePostRequest
import com.sparta.donate.dto.post.response.PostResponse
import com.sparta.donate.global.auth.AuthenticationUtil.getAuthenticationUserId
import com.sparta.donate.global.common.SortOrder
import com.sparta.donate.global.exception.common.NoSuchEntityException
import com.sparta.donate.repository.comment.CommentRepository
import com.sparta.donate.repository.member.MemberRepository
import com.sparta.donate.repository.post.PostRepository
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository,
    private val commentRepository: CommentRepository
) {

    @Transactional
    fun createPost(request: CreatePostRequest): PostResponse {
        val authenticatedId = getAuthenticationUserId()
        val member = memberRepository.findByIdOrNull(authenticatedId) ?: throw NoSuchEntityException("MEMBER")
        val post = postRepository.save(Post.toEntity(request, member))

        return post.from()
    }

    fun getAllPostList(sortOrder: SortOrder): List<PostResponse> {
        return when (sortOrder) {
            SortOrder.DESC -> postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).map { it.from() }
            SortOrder.ASC -> postRepository.findAll(Sort.by(Sort.Direction.ASC, "createdAt")).map { it.from() }
        }
    }

    @Transactional(readOnly = true)
    fun getPostById(postId: Long): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw NoSuchEntityException("POST")
        val commentList = commentRepository.findByPostId(postId).map{ it.from()}

        return post.from(commentList)
    }

    @Transactional
    fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse {
        val authenticatedId = getAuthenticationUserId()
        val savedPost = getByIdOrNull(postId)
        val commentList = commentRepository.findByPostId(postId).map{ it.from()}

        savedPost.updatePost(request, authenticatedId)

        return savedPost.from(commentList)
    }

    @Transactional
    fun deletePost(postId: Long) {
        val authenticatedId = getAuthenticationUserId()
        val post = getByIdOrNull(postId)

        if (post.verify(authenticatedId)){
            postRepository.delete(post)
        }
    }

    fun getByIdOrNull(postId: Long) = postRepository.findByIdOrNull(postId) ?: throw NoSuchEntityException("POST")


}
