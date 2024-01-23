package com.sparta.donate.domain.post.service

import com.sparta.donate.application.post.PostService
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
class PostServiceImpl(private val postRepository: PostRepository) : PostService {

    @Transactional
    override fun createPost(request: CreatePostRequest): PostResponse {
        val post = postRepository.save(request.toPost())
        return PostResponse.toPostResponse(post)
    }

    override fun getAllPostList(sortOrder: SortOrder): List<PostResponse> {
        var postList: List<PostResponse> = listOf()
        if (sortOrder == SortOrder.DESC) {
            postList = postRepository.findAllByOrderByCreatedAtDesc()
        } else {
            postList = postRepository.findAllByOrderByCreatedAtAsc()
        }

        return postList
    }

    @Transactional(readOnly = true)
    override fun getPostById(postId: Long): PostResponse {
        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("Post", postId)
        return PostResponse.toPostResponse(post)
    }

    @Transactional
    override fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse {
        val savedPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        savedPost.updatePost(request)
        return PostResponse.toPostResponse(savedPost)
    }


    @Transactional
    override fun deletePost(postId: Long) {
        postRepository.deleteById(postId)
    }
}