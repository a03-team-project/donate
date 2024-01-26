package com.sparta.donate.global.exception

import org.springframework.http.HttpStatus

interface ErrorCode {
    fun errorName(): String
    fun httpStatus(): HttpStatus
    fun message(): String
}