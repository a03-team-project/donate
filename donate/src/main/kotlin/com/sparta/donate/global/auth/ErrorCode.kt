package com.sparta.donate.global.auth

enum class ErrorCode(val reason: String) {
    EXPIRED_ACCESS_TOKEN("엑세스 토큰이 만료됨")
}