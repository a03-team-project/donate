package com.sparta.donate.api.donate

import com.sparta.donate.application.donate.DonateService
import com.sparta.donate.dto.donate.request.DonateRequest
import com.sparta.donate.dto.donate.response.DonateResponse
import com.sparta.donate.global.common.SortOrder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/donate")
class DonateController (
    private val donateService: DonateService
) {

    @PostMapping("/{postId}")
    fun createDonate(
        @PathVariable postId: Long,
        @RequestBody donateRequest: DonateRequest
    ): ResponseEntity<DonateResponse>{
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(donateService.createDonate(postId, donateRequest))
    }

    @GetMapping
    fun getAllDonateList(
        @RequestParam sortOrder: SortOrder
    ): ResponseEntity<List<DonateResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(donateService.getAllDonateList(sortOrder))
    }

    @GetMapping("/{postId}")
    fun getAllDonateListByPostId(
        @PathVariable postId: Long,
        @RequestParam sortOrder: SortOrder
    ): ResponseEntity<List<DonateResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(donateService.getAllDonateListByPostId(postId, sortOrder))
    }

}
