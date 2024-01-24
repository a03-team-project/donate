package com.sparta.donate.application.post

import com.sparta.donate.domain.common.SortOrder
import com.sparta.donate.domain.post.Post
import com.sparta.donate.dto.post.request.CreatePostRequest
import com.sparta.donate.dto.post.request.UpdatePostRequest
import com.sparta.donate.dto.post.response.PostResponse
import com.sparta.donate.global.exception.ModelNotFoundException
import com.sparta.donate.repository.member.MemberRepository
import com.sparta.donate.repository.post.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun createPost(request: CreatePostRequest): PostResponse {
        val member = memberRepository.findByNickname(request.nickname) ?: TODO("throw NoSuchEntityException()")
        val post = postRepository.save(Post.toEntity(request, member))

        return post.from()
    }

    fun getAllPostList(sortOrder: SortOrder): List<PostResponse> {
        val postList =
            if (sortOrder == SortOrder.DESC) {
                postRepository.findAllByOrderByCreatedAtDesc()
            } else {
                postRepository.findAllByOrderByCreatedAtAsc()
            }

        return postList
    }

    @Transactional(readOnly = true)
    fun getPostById(postId: Long): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        return post.from()
    }

    @Transactional
    fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse {
        val savedPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        savedPost.updatePost(request)
        return savedPost.from()
    }


    @Transactional
    fun deletePost(postId: Long) {
        postRepository.deleteById(postId)
    }
}