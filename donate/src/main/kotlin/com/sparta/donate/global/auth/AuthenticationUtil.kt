package com.sparta.donate.global.auth

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

object AuthenticationUtil {
    private fun getAuthenticatedUser(): UserDetails =
        SecurityContextHolder.getContext().authentication.principal as UserDetails

    fun getAuthenticationUserId() = getAuthenticatedUser().username.toLong()

}