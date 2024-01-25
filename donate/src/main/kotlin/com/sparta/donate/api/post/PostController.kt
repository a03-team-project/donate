package com.sparta.donate.api.post

import com.sparta.donate.application.post.PostService
import com.sparta.donate.global.common.SortOrder
import com.sparta.donate.dto.post.request.CreatePostRequest
import com.sparta.donate.dto.post.request.UpdatePostRequest
import com.sparta.donate.dto.post.response.PostResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostController(private val postService: PostService) {

    @PostMapping
    fun createPost(
        @RequestBody createPostRequest: CreatePostRequest
    ): ResponseEntity<PostResponse> {
        val createdPost = postService.createPost(createPostRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost)
    }

    @GetMapping
    fun getAllPostList(
        @RequestParam sortOrder: SortOrder
    ): ResponseEntity<List<PostResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPostList(sortOrder))
    }

    @GetMapping("/{postsId}")
    fun getPostById(
        @PathVariable postsId: Long
    ): ResponseEntity<PostResponse> {
        val post = postService.getPostById(postsId)
        return ResponseEntity.status(HttpStatus.OK).body(post)
    }

    @PutMapping("/{postsId}")
    fun updatePost(
        @PathVariable postsId: Long,
        @RequestBody updatePostRequest: UpdatePostRequest
    ): ResponseEntity<PostResponse> {
        val updatedPost = postService.updatePost(postsId, updatePostRequest)
        return ResponseEntity.status(HttpStatus.OK).body(updatedPost)
    }

    @DeleteMapping("/{postsId}")
    fun deletePost(
        @PathVariable postsId: Long
    ): ResponseEntity<Unit> {
        postService.deletePost(postsId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}