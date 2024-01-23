package com.sparta.donate.application.member

import com.sparta.donate.domain.member.Member
import com.sparta.donate.dto.member.request.SignInRequest
import com.sparta.donate.dto.member.request.SignUpRequest
import com.sparta.donate.dto.member.response.JwtResponse
import com.sparta.donate.global.auth.JwtProvider
import com.sparta.donate.repository.member.MemberRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtProvider: JwtProvider
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

    fun signin(request: SignInRequest): JwtResponse {
        val (email, password) = request
        val member = getByEmailAndPassword(email, passwordEncoder.encode(password))

        return jwtProvider.generateJwt(member)
    }

    private fun getByEmailAndPassword(email: String, password: String): Member {
        return memberRepository.findByEmailAndPassword(email, passwordEncoder.encode(password))
            ?: TODO("NoSuchEntityException")
    }

}
