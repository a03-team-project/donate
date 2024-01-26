package com.sparta.donate.global.exception.member

import com.sparta.donate.global.exception.ErrorCode

class InvalidPasswordException(
    val errorCode: ErrorCode = MemberErrorCode.INVALID_PASSWORD_ERROR
) : RuntimeException()