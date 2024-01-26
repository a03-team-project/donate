package com.sparta.donate.dto.donate.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class DonateRequest(
    @field:Positive
    @field:Min(1000, message = "최소 1000원 이상의 금액을 후원해주세요")
    val amount: Long,

    @field:NotBlank(message = "메시지를 입력해주세요")
    val message: String
)