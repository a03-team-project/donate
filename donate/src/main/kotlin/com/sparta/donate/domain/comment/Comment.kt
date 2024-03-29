package com.sparta.donate.domain.comment

import com.sparta.donate.domain.member.Member
import com.sparta.donate.domain.post.Post
import com.sparta.donate.dto.comment.request.CommentRequest
import com.sparta.donate.dto.comment.response.CommentResponse
import com.sparta.donate.global.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.security.access.AccessDeniedException

@Entity
@Table(name = "comments")
class Comment private constructor(
    _member: Member,
    _content: String,
    _post: Post,
    _amount: Long
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    var id: Long? = null
        private set


    @Column(name = "content")
    var content: String = _content
        private set

    @Column(name = "amount")
    var amount: Long = _amount
        private set

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "member_id")
    var member: Member = _member
        private set

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id")
    var post: Post = _post
        private set


    fun from(): CommentResponse {
        return CommentResponse(
            nickname = member.nickname,
            createdAt = createdAt,
            content = content,
            donationAmount = amount
        )
    }


    fun update(newContent: String, authenticationId: Long) {
        if (verify(authenticationId)) {
            content = newContent
            return
        }

    }

    fun updateAmount(newAmount: Long){
        amount = newAmount
    }

    fun verify(authenticationId: Long): Boolean {
        if (this.member.id == authenticationId) {
            return true
        }

        throw AccessDeniedException("Verify Failed")
    }

    companion object {
        fun of(request: CommentRequest, member: Member, post: Post, amount: Long): Comment {
            return Comment(
                _member = member,
                _content = request.content,
                _post = post,
                _amount = amount
            )
        }
    }
}
