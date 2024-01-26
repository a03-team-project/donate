package com.sparta.donate.application.member

import com.sparta.donate.domain.member.Member
import com.sparta.donate.domain.member.PasswordHistory
import com.sparta.donate.dto.member.request.ProfileRequest
import com.sparta.donate.dto.member.response.ProfileResponse
import com.sparta.donate.dto.member.request.SignInRequest
import com.sparta.donate.dto.member.request.SignUpRequest
import com.sparta.donate.dto.member.response.JwtResponse
import com.sparta.donate.global.auth.AuthenticationUtil
import com.sparta.donate.global.auth.JwtProvider
import com.sparta.donate.global.exception.common.NoSuchEntityException
import com.sparta.donate.global.exception.member.DuplicateEmailException
import com.sparta.donate.global.exception.member.DuplicateNicknameException
import com.sparta.donate.global.exception.member.InvalidPasswordException
import com.sparta.donate.repository.member.MemberRepository
import com.sparta.donate.repository.password.PasswordRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import javax.naming.AuthenticationException

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
            throw DuplicateEmailException(request.email)
        }

        if (memberRepository.existsByNickname(request.nickname)) {
            throw DuplicateNicknameException(request.nickname)
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
        val authenticatedId = AuthenticationUtil.getAuthenticationUserId()
        val member = memberRepository.findByIdOrNull(authenticatedId) ?: throw NoSuchEntityException("MEMBER")
        member.logout()
    }

    @Transactional
    fun updateProfile(request: ProfileRequest): ProfileResponse {
        val passwordHistories = passwordRepository.findByEmailOrderByUpdatedAtDesc(request.email)
        val isDuplicate = passwordHistories.any { passwordEncoder.matches(request.password, it.password) }

        if (isDuplicate) {
            throw InvalidPasswordException()
        }

        val authenticatedId = AuthenticationUtil.getAuthenticationUserId()
        val member = getByEmailOrNull(request.email)
        member.verify(authenticatedId)

        val updatedPassword = passwordEncoder.encode(request.password)
        member.update(request.introduce, updatedPassword)

        if (passwordHistories.size <= 3) {
            passwordRepository.save(PasswordHistory.of(request.email, updatedPassword))
        } else {
            passwordHistories[0].update(updatedPassword)
        }

        return member.from()
    }

    private fun getByEmailAndPassword(email: String, password: String): Member {
        return memberRepository.findByEmail(email)
            ?.takeIf { passwordEncoder.matches(password, it.password) }
            ?: throw NoSuchEntityException("MEMBER")
    }

    fun getByEmailOrNull(email: String) = memberRepository.findByEmail(email) ?: throw NoSuchEntityException("MEMBER")

}
