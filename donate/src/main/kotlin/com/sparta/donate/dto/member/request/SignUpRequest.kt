package com.sparta.donate.dto.member.request

import com.sparta.donate.domain.member.MemberRole
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern


data class SignUpRequest(

    @field:Email(message = "올바른 이메일 형식이 아닙니다.")
    val email: String,

    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,15}$",
        message = "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상 15자 이하를 충족해야 합니다"
    )
    val password: String,

    @field:NotNull(message = "역할은 필수값입니다.")
    val role: MemberRole,

    val name: String,
    val nickname: String,
    val introduce: String,
)


