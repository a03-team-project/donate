package com.sparta.donate.dto.donate.response


data class DonateResponse(
    val amount: Long,
    val message: String,
    val member: String,
    val post: String
)
