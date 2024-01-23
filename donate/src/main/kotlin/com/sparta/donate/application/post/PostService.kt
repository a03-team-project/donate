package com.sparta.donate.application.post

import com.sparta.donate.domain.common.SortOrder
import com.sparta.donate.dto.post.request.CreatePostRequest
import com.sparta.donate.dto.post.request.UpdatePostRequest
import com.sparta.donate.dto.post.response.PostResponse
import org.springframework.stereotype.Service

@Service
interface PostService {

    fun createPost(request: CreatePostRequest): PostResponse

    fun getAllPostList(sortOrder: SortOrder): List<PostResponse>

    fun getPostById(postId: Long): PostResponse

    fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse

    fun deletePost(postId: Long)
}