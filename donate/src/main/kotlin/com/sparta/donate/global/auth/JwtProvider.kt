package com.sparta.donate.global.auth

import com.sparta.donate.domain.member.Member
import com.sparta.donate.dto.member.response.JwtResponse
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.util.*

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties
) {

    private val key = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    fun generateJwt(member: Member): JwtResponse {
        val accessToken = generateToken(member)
        val refreshToken = generateToken()

        return JwtResponse("Bearer $accessToken", refreshToken)
    }

    private fun generateToken(member: Member): String {
        val claims = mapOf(
            "nickname" to member.nickname,
            "email" to member.email,
            "role" to member.role
        )

        return Jwts.builder()
            .issuer(jwtProperties.issuer)
            .issuedAt(Date.from(Instant.now()))
            .claims(claims)
            .signWith(key)
            .expiration(Date.from(Instant.now().plus(Duration.ofHours(jwtProperties.accessTokenExpirationHour))))
            .compact()
    }

    private fun generateToken(): String {
        val emptyClaims: Map<String, Any> = emptyMap()

        return Jwts.builder()
            .issuer(jwtProperties.issuer)
            .issuedAt(Date.from(Instant.now()))
            .claims(emptyClaims)
            .signWith(key)
            .expiration(Date.from(Instant.now().plus(Duration.ofHours(jwtProperties.refreshTokenExpirationHour))))
            .compact()
    }

}
