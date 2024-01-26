package com.sparta.donate.global.exception.common

import com.sparta.donate.global.exception.ErrorCode
import org.springframework.http.HttpStatus


enum class CommonErrorCode(
    val httpStatus: HttpStatus,
    var message: String,
) : ErrorCode {
    ENTITY_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "존재하지 않는 %s입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 서버 오류입니다."),
    REQUEST_VALID_ERROR(HttpStatus.BAD_REQUEST, "조건에 충족되지 않는 입력값입니다.")
    ;

    override fun errorName() = name
    override fun httpStatus() = httpStatus
    override fun message() = message

}

