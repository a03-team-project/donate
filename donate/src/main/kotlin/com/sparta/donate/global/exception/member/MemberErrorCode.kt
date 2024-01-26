package com.sparta.donate.global.exception.member

import com.sparta.donate.global.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class MemberErrorCode(
    val httpStatus: HttpStatus,
    var message: String
) : ErrorCode {
    DUPLICATE_EMAIL_ERROR(HttpStatus.CONFLICT, "이미 존재하는 이메일 입니다. email => %s"),
    DUPLICATE_NICKNAME_ERROR(HttpStatus.CONFLICT, "이미 존재하는 닉네임 입니다. nickname => %s"),
    INVALID_PASSWORD_ERROR(HttpStatus.CONFLICT, "이전에 사용한 적이 있는 비밀번호 입니다.")
    ;

    override fun errorName() = name
    override fun httpStatus() = httpStatus
    override fun message() = message
}