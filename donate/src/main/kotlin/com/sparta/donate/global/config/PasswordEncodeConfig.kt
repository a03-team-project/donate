package com.sparta.donate.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class PasswordEncodeConfig {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}