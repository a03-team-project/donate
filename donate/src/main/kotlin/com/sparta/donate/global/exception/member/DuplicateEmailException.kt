package com.sparta.donate.global.exception.member

import com.sparta.donate.global.exception.ErrorCode

class DuplicateEmailException(
    val email: String,
    val errorCode: ErrorCode = MemberErrorCode.DUPLICATE_EMAIL_ERROR
) : RuntimeException()
