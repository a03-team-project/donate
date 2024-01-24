package com.sparta.donate.domain.donate

import com.sparta.donate.domain.comment.Comment
import com.sparta.donate.domain.member.Member
import com.sparta.donate.domain.post.Post
import com.sparta.donate.dto.comment.request.CommentRequest
import com.sparta.donate.dto.donate.request.DonateRequest
import com.sparta.donate.dto.donate.response.DonateResponse
import com.sparta.donate.dto.post.response.PostResponse
import com.sparta.donate.global.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime

@Entity
@Table(name = "donates")
class Donate private constructor(
    _amount: Long,
    _message: String,
    _member: Member,
    _post: Post
): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donate_id")
    var id: Long? = null
        private set

    @Column(name = "amount")
    var amount: Long = _amount

    @Column(name = "message")
    var message: String = _message

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "member_id")
    val member: Member = _member

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id")
    val post: Post = _post

    fun from() = DonateResponse(
        amount = amount,
        message = message,
        member = member.nickname,
        post = post.title
    )

    companion object {
        fun toEntity(request: DonateRequest, member: Member, post: Post): Donate {
            return Donate(
                _amount = request.amount,
                _message = request.message,
                _member = member,
                _post = post
            )
        }
    }


}


