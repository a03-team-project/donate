package com.sparta.donate.global.exception.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.sparta.donate.global.exception.ErrorCode
import org.springframework.validation.BindingResult


@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class ErrorResponse(
    val code: String,
    val message: String,
    val errors: List<FieldError>?,
) {

    data class FieldError(
        val field: String,
        val value: String,
        val message: String
    ) {

        companion object {
            fun of(bindingResult: BindingResult): List<FieldError> {
                val errors = bindingResult.fieldErrors

                return errors.map {
                    FieldError(
                        field = it.field,
                        value = it.rejectedValue.toString(),
                        message = it.defaultMessage.toString()
                    )
                }
            }

        }
    }

    companion object {
        fun of(errorCode: ErrorCode) =
            ErrorResponse(errorCode.errorName(), errorCode.message(), null)

        fun of(errorCode: ErrorCode, bindingResult: BindingResult) =
            ErrorResponse(errorCode.errorName(), errorCode.message(), FieldError.of(bindingResult))
    }

}
