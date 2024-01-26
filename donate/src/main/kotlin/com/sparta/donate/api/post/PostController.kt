package com.sparta.donate.api.post

import com.sparta.donate.application.post.PostService
import com.sparta.donate.dto.post.response.PostResponse
import com.sparta.donate.global.common.SortOrder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostController(private val postService: PostService) {

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

}