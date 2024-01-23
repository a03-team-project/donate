package com.sparta.donate.dto.member.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SignInRequest(
    @field:Email(message = "올바른 이메일 형식이 아닙니다.")
    @field:NotBlank(message = "이메일을 입력해주세요")
    val email: String,

    @field:NotBlank(message = "비밀번호를 입력해주세요")
    val password: String
)
