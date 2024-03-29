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

    override fun loadUserByUsername(username: String): UserDetails? {
        val member = memberRepository.findByNickname(username)

        return if(member?.refreshToken != null) {
            generateUserDetails(member)
        } else {
            null
        }
    }

    private fun generateUserDetails(member: Member): UserDetails =
        User(
            member.id.toString(),
            "",
            setOf(SimpleGrantedAuthority("ROLE_${member.role.name}"))
        )

}