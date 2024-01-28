package com.sparta.donate.global.exception.handler

import com.sparta.donate.global.exception.common.CommonErrorCode
import com.sparta.donate.global.exception.common.NoSuchEntityException
import com.sparta.donate.global.exception.member.DuplicateEmailException
import com.sparta.donate.global.exception.member.DuplicateNicknameException
import com.sparta.donate.global.exception.member.InvalidPasswordException
import com.sparta.donate.global.exception.member.MemberErrorCode
import com.sparta.donate.global.exception.response.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestApiExceptionHandler {

    private final val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler
    fun handleRuntimeException(e: RuntimeException): ResponseEntity<ErrorResponse> {
        val errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR
        logger.warn("INTERNAL SERVER ERROR", e)

        return ResponseEntity.status(errorCode.httpStatus).body(ErrorResponse.of(errorCode))
    }

    @ExceptionHandler
    fun handleNoSuchEntityException(e: NoSuchEntityException): ResponseEntity<ErrorResponse> {
        val errorCode = e.errorCode as CommonErrorCode
        logger.warn("NoSuchEntityException!", e)

        val customMessage = String.format(errorCode.message, e.entity)

        return ResponseEntity.status(errorCode.httpStatus()).body(ErrorResponse.of(errorCode, customMessage))
    }

    @ExceptionHandler
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorCode = CommonErrorCode.REQUEST_VALID_ERROR
        logger.warn("Request Validation Exception", e)

        return ResponseEntity.status(errorCode.httpStatus()).body(ErrorResponse.of(errorCode, e.bindingResult))
    }

    @ExceptionHandler
    fun handleDuplicateEmailException(e: DuplicateEmailException): ResponseEntity<ErrorResponse> {
        val errorCode = e.errorCode as MemberErrorCode
        logger.warn("Duplicate Email Exception", e)

        errorCode.message = String.format(errorCode.message(), e.email)

        return ResponseEntity.status(errorCode.httpStatus()).body(ErrorResponse.of(errorCode))
    }

    @ExceptionHandler
    fun handleDuplicateNicknameException(e: DuplicateNicknameException): ResponseEntity<ErrorResponse> {
        val errorCode = e.errorCode as MemberErrorCode
        logger.warn("Duplicate Nickname Exception", e)

        errorCode.message = String.format(errorCode.message(), e.nickname)

        return ResponseEntity.status(errorCode.httpStatus()).body(ErrorResponse.of(errorCode))
    }

    @ExceptionHandler
    fun handleInvalidPasswordException(e: InvalidPasswordException): ResponseEntity<ErrorResponse> {
        val errorCode = e.errorCode
        logger.warn("Invalid Password Exception", e)

        return ResponseEntity.status(errorCode.httpStatus()).body(ErrorResponse.of(errorCode))
    }
}
