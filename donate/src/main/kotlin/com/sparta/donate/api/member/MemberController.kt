package com.sparta.donate.api.member

import com.sparta.donate.application.member.MemberService
import com.sparta.donate.dto.member.ProfileRequest
import com.sparta.donate.dto.member.ProfileResponse
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
    fun update(@RequestBody request: ProfileRequest): ResponseEntity<ProfileResponse> {
        // 1. 이메일이 id 가 돼서 비밀번호가 리스트로 들어간다.
        // 2. 회원가입을 했을 때 사용한 비밀번호도 최근 3번 안에 사용한 비밀번호에 해당한다.
        // 3. 프로필 수정 Request, Response Dto 생성
        val profileResponse = memberService.updateProfile(request)
        return ResponseEntity.ok(profileResponse)
    }

}
