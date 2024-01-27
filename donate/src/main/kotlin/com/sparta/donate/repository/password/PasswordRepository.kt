package com.sparta.donate.repository.password

import com.sparta.donate.domain.member.PasswordHistory
import org.springframework.data.jpa.repository.JpaRepository

interface PasswordRepository: JpaRepository<PasswordHistory, Long> {
    fun findByEmailOrderByUpdatedAtAsc(email: String): List<PasswordHistory>
}

