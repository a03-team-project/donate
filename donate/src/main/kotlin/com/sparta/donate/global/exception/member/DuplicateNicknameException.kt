package com.sparta.donate.global.exception.member

import com.sparta.donate.global.exception.ErrorCode

class DuplicateNicknameException(
    val nickname: String,
    val errorCode: ErrorCode = MemberErrorCode.DUPLICATE_NICKNAME_ERROR
) : RuntimeException()
