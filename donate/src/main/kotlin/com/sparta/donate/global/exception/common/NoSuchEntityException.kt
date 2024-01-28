package com.sparta.donate.global.exception.common

import com.sparta.donate.global.exception.ErrorCode

class NoSuchEntityException(
    val entity: String,
    val errorCode: ErrorCode = CommonErrorCode.ENTITY_NOT_FOUND_ERROR,
    ) : RuntimeException()