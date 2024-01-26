package com.sparta.donate.api.comment

import com.sparta.donate.application.comment.CommentService
import com.sparta.donate.dto.comment.request.CommentRequest
import com.sparta.donate.dto.comment.response.CommentResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/comments/{postId}")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping
    fun creatComment(
        @PathVariable postId: Long,
        @Valid @RequestBody commentRequest: CommentRequest,
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(postId, commentRequest))
    }

    @PatchMapping("/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @Valid @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<CommentResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.updateComment(commentId, commentRequest))
    }


    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long
    ): ResponseEntity<Unit> {
        commentService.deleteComment(postId, commentId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }


}