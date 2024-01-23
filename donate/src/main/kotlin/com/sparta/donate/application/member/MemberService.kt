package com.sparta.donate.application.member

import com.sparta.donate.domain.member.Member
import com.sparta.donate.dto.member.request.SignUpRequest
import com.sparta.donate.repository.member.MemberRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {

    fun signup(request: SignUpRequest): Long {

        if (memberRepository.existsByEmail(request.email)) {
            TODO("DuplicateEmailException")
        }

        if (memberRepository.existsByNickname(request.nickname)) {
            TODO("DuplicateNicknameException")
        }

        val member = Member.of(request.copy(password = passwordEncoder.encode(request.password)))
        memberRepository.save(member)
        return member.id!!
    }

}

