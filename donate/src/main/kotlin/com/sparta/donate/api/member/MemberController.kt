package com.sparta.donate.api.member

import com.sparta.donate.application.member.MemberService
import com.sparta.donate.dto.member.request.SignUpRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1/members")
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/signup")
    fun signup(@Valid @RequestBody request: SignUpRequest): ResponseEntity<Unit> {
        val id = memberService.signup(request)
        return ResponseEntity.created(URI.create(String.format("/api/v1/members/%d", id))).build()
    }

}