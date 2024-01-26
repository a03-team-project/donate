package com.sparta.donate.global.exception.auth

import com.sparta.donate.global.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class AuthErrorCode(
    val httpStatus: HttpStatus,
    val message: String
) : ErrorCode {
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "엑세스 토큰이 만료됨"),
    COMMON_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증 실패"),
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "API에 대한 접근 권한이 없습니다.")
    ;

    override fun errorName() = name
    override fun httpStatus() = httpStatus
    override fun message() = message
}