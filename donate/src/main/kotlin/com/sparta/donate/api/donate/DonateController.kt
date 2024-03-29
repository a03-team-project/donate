package com.sparta.donate.api.donate

import com.sparta.donate.application.donate.DonateService
import com.sparta.donate.dto.donate.request.DonateRequest
import com.sparta.donate.dto.donate.response.DonateResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/donate")
class DonateController (
    private val donateService: DonateService
) {

    @PostMapping("/donate/{postId}")
    fun createDonate(
        @PathVariable postId: Long,
        @Valid @RequestBody donateRequest: DonateRequest
    ): ResponseEntity<DonateResponse>{
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(donateService.createDonate(postId, donateRequest))
    }

    @GetMapping
    fun getDonateListByMemberId(): ResponseEntity<List<DonateResponse>>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(donateService.getDonateListByMemberId())
    }



}

