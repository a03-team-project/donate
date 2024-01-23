package com.sparta.donate.domain.post.service

import com.sparta.donate.domain.common.SortOrder
import com.sparta.donate.dto.post.request.CreatePostRequest
import com.sparta.donate.dto.post.request.UpdatePostRequest
import com.sparta.donate.dto.post.response.PostResponse
import com.sparta.donate.global.exception.ModelNotFoundException
import com.sparta.donate.repository.post.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(private val postRepository: PostRepository) {

    @Transactional
    fun createPost(request: CreatePostRequest): PostResponse {
        val post = postRepository.save(request.toPost())
        return PostResponse.toPostResponse(post)
    }

    fun getAllPostList(sortOrder: SortOrder): List<PostResponse> {
        var postList: List<PostResponse> = listOf()
        if (sortOrder == SortOrder.DESC) {
            postList = postRepository.findAllByOrderByCreatedAtDesc()
        } else {
            postList = postRepository.findAllByOrderByCreatedAtAsc()
        }

        return postList
    }

    @Transactional(readOnly = true)
    fun getPostById(postId: Long): PostResponse {
        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("Post", postId)
        return PostResponse.toPostResponse(post)
    }

    @Transactional
    fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse {
        val savedPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        savedPost.updatePost(request)
        return PostResponse.toPostResponse(savedPost)
    }


    @Transactional
    fun deletePost(postId: Long) {
        postRepository.deleteById(postId)
    }
}