package com.sparta.donate.dto.member.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern


data class SignUpRequest(

    @field:Email(message = "올바른 이메일 형식이 아닙니다.")
    val email: String,

    @field:Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}${'$'}",
        message = "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다"
    )
    val password: String,

    @field:NotBlank(message = "역할은 필수값입니다.")
    val role: String,

    val name: String,
    val nickname: String,
)
