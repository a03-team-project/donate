package com.sparta.donate.api.member

import com.sparta.donate.application.member.MemberService
import com.sparta.donate.dto.member.request.ProfileRequest
import com.sparta.donate.dto.member.response.ProfileResponse
import com.sparta.donate.dto.member.request.SignInRequest
import com.sparta.donate.dto.member.request.SignUpRequest
import com.sparta.donate.dto.member.response.JwtResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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

    @PostMapping("/signin")
    fun signin(@Valid @RequestBody request: SignInRequest): ResponseEntity<JwtResponse> {
        val jwt = memberService.signin(request)
        return ResponseEntity.ok(jwt)
    }

    @PostMapping("/logout")
    fun logout(): ResponseEntity<Unit> {
        memberService.logout()
        return ResponseEntity.ok().build()
    }

    @PutMapping("/profile")
    fun update(@Valid @RequestBody request: ProfileRequest): ResponseEntity<ProfileResponse> {
        val profileResponse = memberService.updateProfile(request)
        return ResponseEntity.ok(profileResponse)
    }

}
