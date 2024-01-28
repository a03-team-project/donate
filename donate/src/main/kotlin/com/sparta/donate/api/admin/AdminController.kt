package com.sparta.donate.api.admin

import com.sparta.donate.application.donate.DonateService
import com.sparta.donate.application.post.PostService
import com.sparta.donate.dto.donate.response.DonateResponse
import com.sparta.donate.dto.post.request.CreatePostRequest
import com.sparta.donate.dto.post.request.UpdatePostRequest
import com.sparta.donate.dto.post.response.PostResponse
import com.sparta.donate.global.common.SortOrder
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/admin")
class AdminController(
    private val postService: PostService,
    private val donateService: DonateService
) {

    @PostMapping("/posts")
    fun createPost(
        @Valid @RequestBody createPostRequest: CreatePostRequest,
    ): ResponseEntity<PostResponse> {
        val createdPost = postService.createPost(createPostRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost)
    }

    @PutMapping("/posts/{postsId}")
    fun updatePost(
        @PathVariable postsId: Long,
        @Valid @RequestBody updatePostRequest: UpdatePostRequest
    ): ResponseEntity<PostResponse> {
        val updatedPost = postService.updatePost(postsId, updatePostRequest)
        return ResponseEntity.status(HttpStatus.OK).body(updatedPost)
    }

    @DeleteMapping("/posts/{postsId}")
    fun deletePost(
        @PathVariable postsId: Long
    ): ResponseEntity<Unit> {
        postService.deletePost(postsId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @GetMapping("/donates")
    fun getAllDonateList(
        @RequestParam sortOrder: SortOrder
    ): ResponseEntity<List<DonateResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(donateService.getAllDonateList(sortOrder))
    }

    @GetMapping("/donates/{postId}")
    fun getAllDonateListByPostId(
        @PathVariable postId: Long,
        @RequestParam sortOrder: SortOrder
    ): ResponseEntity<List<DonateResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(donateService.getAllDonateListByPostId(postId, sortOrder))
    }

    @DeleteMapping("/donates/{donateId}")
    fun deleteDonateById(
        @PathVariable donateId: Long
    ): ResponseEntity<Unit> {
        donateService.deleteDonate(donateId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

}

