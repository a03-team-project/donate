package com.sparta.donate.global.auth

import com.sparta.donate.domain.member.Member
import com.sparta.donate.repository.member.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val memberRepository: MemberRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        memberRepository.findByNickname(username)
            ?.let { generateUserDetails(it) }
            ?: throw RuntimeException()


    private fun generateUserDetails(member: Member): UserDetails =
        User(
            member.nickname,
            "",
            setOf(SimpleGrantedAuthority("ROLE_${member.role}"))
        )

}