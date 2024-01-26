package com.sparta.donate.dto.member.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern


data class ProfileRequest (
    @field:Email(message = "올바른 이메일 형식이 아닙니다.")
    @field:NotBlank(message = "이메일을 입력해주세요")
    val email: String,

    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,15}$",
        message = "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상 15자 이하를 충족해야 합니다"
    )
    val password: String,

    @field:NotBlank(message = "자기소개를 입력해주세요")
    val introduce: String,
)
