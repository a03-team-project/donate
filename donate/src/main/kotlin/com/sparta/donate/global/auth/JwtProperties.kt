package com.sparta.donate.global.auth

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
class JwtProperties(
    val issuer: String,
    val accessTokenExpirationHour: Long,
    val refreshTokenExpirationHour: Long,
    val secret: String
)
