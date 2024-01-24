package com.sparta.donate.dto.member.response

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String
)
