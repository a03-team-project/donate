package com.sparta.donate.domain.member

import com.sparta.donate.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "passwords")
class PasswordHistory private constructor(
    _email: String,
    _password: String
): BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        private set

    @Column(name = "email")
    var email: String = _email
        private set

    @Column(name = "password")
    var password: String = _password
        private set

    fun update(password: String) {
        this.password = password
    }

    companion object {
        fun of(email: String, password: String) = PasswordHistory(
            _email = email,
            _password = password
        )

    }
}


