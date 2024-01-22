package com.sparta.donate.domain.member

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

    @Column(name = "email", unique = true)
    val email: String = _email

}

