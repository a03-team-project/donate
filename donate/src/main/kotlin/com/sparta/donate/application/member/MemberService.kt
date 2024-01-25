package com.sparta.donate.application.member

import com.sparta.donate.domain.member.Member
import com.sparta.donate.domain.member.PasswordHistory
import com.sparta.donate.dto.member.ProfileRequest
import com.sparta.donate.dto.member.ProfileResponse
import com.sparta.donate.dto.member.request.SignInRequest
import com.sparta.donate.dto.member.request.SignUpRequest
import com.sparta.donate.dto.member.response.JwtResponse
import com.sparta.donate.global.auth.JwtProvider
import com.sparta.donate.repository.member.MemberRepository
import com.sparta.donate.repository.password.PasswordRepository
import jakarta.transaction.Transactional
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtProvider: JwtProvider,
    private val passwordRepository: PasswordRepository
) {

    @Transactional
    fun signup(request: SignUpRequest): Long {

        if (memberRepository.existsByEmail(request.email)) {
            TODO("DuplicateEmailException")
        }

        if (memberRepository.existsByNickname(request.nickname)) {
            TODO("DuplicateNicknameException")
        }

        val member = Member.of(request.copy(password = passwordEncoder.encode(request.password)))
        memberRepository.save(member)

        val passwordHistory = PasswordHistory.of(member.email, member.password)
        passwordRepository.save(passwordHistory)
        return member.id!!
    }

    @Transactional
    fun signin(request: SignInRequest): JwtResponse {
        val (email, password) = request
        val member = getByEmailAndPassword(email, password)
        val jwt = jwtProvider.generateJwt(member)
        member.saveRefreshToken(jwt.refreshToken)
        return jwt
    }

    @Transactional
    fun logout() {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val member = memberRepository.findByNickname(userDetails.username) ?: TODO("throw NoSuchEntityException()")
        member.logout()
    }

    @Transactional
    fun updateProfile(request: ProfileRequest): ProfileResponse {
        val passwordHistories: List<PasswordHistory> = passwordRepository.findAllByEmailOrderByUpdatedAtDesc(request.email)
        val isDuplicate = passwordHistories.any { passwordEncoder.matches(request.password, it.password) }

        if(isDuplicate) {
            throw RuntimeException()
        }
        val updatedPassword = passwordEncoder.encode(request.password)
        if (passwordHistories.size <= 3) {
            passwordRepository.save(PasswordHistory.of(request.email, updatedPassword))
        } else {
            passwordHistories[0].update(updatedPassword)
        }

        val member = memberRepository.findByEmail(request.email) ?: TODO("NoSuchEntityException")
        member.update(request.introduce, updatedPassword)

        return member.from()
    }

    private fun getByEmailAndPassword(email: String, password: String): Member {
        return memberRepository.findByEmail(email)
            ?.takeIf { passwordEncoder.matches(password, it.password) }
            ?: TODO("NoSuchEntityException")
    }

}
