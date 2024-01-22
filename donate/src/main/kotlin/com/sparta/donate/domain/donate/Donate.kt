package com.sparta.donate.domain.donate

import com.sparta.donate.domain.member.Member
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "donates")
class Donate private constructor(
    _amount: Long,
    _member: Member
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donate_id")
    var id: Long? = null
        private set

    @Column(name = "amount")
    var amount: Long = _amount

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "member_id")
    val member: Member = _member

}