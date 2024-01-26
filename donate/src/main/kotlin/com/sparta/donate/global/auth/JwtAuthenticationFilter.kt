package com.sparta.donate.global.auth

import com.sparta.donate.global.exception.auth.AuthErrorCode
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val userDetailsService: UserDetailsService,
    private val jwtResolver: JwtResolver
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = jwtResolver.resolveToken(request)

        jwt?.let {
            jwtResolver.verifyAccessToken(it)
                .onSuccess { claims ->
                    userDetailsService.loadUserByUsername(claims["nickname"] as String)
                        .let { userDetails -> UsernamePasswordAuthenticationToken.authenticated(userDetails, "", userDetails.authorities) }
                        .let { authentication -> SecurityContextHolder.getContext().authentication = authentication } }
                .onFailure {exception ->
                    when (exception) {
                        is ExpiredJwtException -> request.setAttribute("exception", AuthErrorCode.EXPIRED_ACCESS_TOKEN)
                        else -> request.setAttribute("exception", AuthErrorCode.COMMON_UNAUTHORIZED)
                    }
                }
        }
        filterChain.doFilter(request, response)
    }
}