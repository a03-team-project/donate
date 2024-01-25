package com.sparta.donate.dto.member

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank


data class ProfileRequest (
    @field:Email(message = "올바른 이메일 형식이 아닙니다.")
    @field:NotBlank(message = "이메일을 입력해주세요")
    val email: String,

    @field:NotBlank(message = "비밀번호를 입력해주세요")
    val password: String,

    @field:NotBlank(message = "자기소개를 입력해주세요")
    val introduce: String,
)
