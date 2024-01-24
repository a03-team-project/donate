package com.sparta.donate.global.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class JwtResolver(
    private val jwtProperties: JwtProperties
) {

    private val key = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    private val BEARER_PATTERN = Regex("^Bearer (.*?)$")

    fun resolveToken(request: HttpServletRequest): String? {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        return BEARER_PATTERN.find(header)?.groupValues?.get(1)
    }

    fun verifyAccessToken(accessToken: String): Result<Claims> {
        return kotlin.runCatching { getClaims(accessToken) }
    }

    fun getClaims(accessToken: String): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .requireExpiration(Date.from(Instant.now()))
            .build()
            .parseSignedClaims(accessToken)
            .payload
    }

}
