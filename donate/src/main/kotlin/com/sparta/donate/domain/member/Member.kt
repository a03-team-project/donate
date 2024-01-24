package com.sparta.donate.domain.member

import com.sparta.donate.dto.member.request.SignUpRequest
import com.sparta.donate.global.entity.BaseEntity
import jakarta.persistence.*


@Entity
@Table(name = "members")
class Member private constructor(
    _nickname: String,
    _name: String,
    _password: String,
    _role: String,
    _email: String
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    var id: Long? = null
        private set

    @Column(name = "nickname", unique = true)
    var nickname: String = _nickname
        private set

    @Column(name = "name")
    var name: String = _name
        private set

    @Column(name = "password")
    var password: String = _password
        private set

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    var role: String = _role
        private set

    @Column(name = "refresh_token")
    var refreshToken: String? = null
        private set

    @Column(name = "email", unique = true)
    val email: String = _email

    fun saveRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }

    fun logout() {
        this.refreshToken = null
    }

    companion object {
        fun of(request: SignUpRequest) = Member(
            _nickname = request.nickname,
            _name = request.name,
            _password = request.password,
            _role = request.role,
            _email = request.email
        )
    }

}

