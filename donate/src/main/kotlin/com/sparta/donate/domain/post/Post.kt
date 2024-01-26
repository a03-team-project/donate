package com.sparta.donate.domain.post

import com.sparta.donate.domain.member.Member
import com.sparta.donate.dto.post.request.CreatePostRequest
import com.sparta.donate.dto.post.request.UpdatePostRequest
import com.sparta.donate.dto.post.response.PostResponse
import com.sparta.donate.global.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.security.access.AccessDeniedException
import java.time.LocalDateTime

@Entity
@Table(name = "posts")
class Post private constructor(
    _title: String,
    _content: String,
    _member: Member
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    var id: Long? = null
        private set

    @Column(name = "title")
    var title: String = _title
        private set

    @Column(name = "content")
    var content: String = _content
        private set

    @Column(name = "ended_at")
    var endedAt: LocalDateTime = createdAt.plusDays(14)
        private set

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "member_id")
    val member: Member = _member


    companion object {

        fun toEntity(request: CreatePostRequest, member: Member) =
            Post(
                _title = request.title,
                _content = request.content,
                _member = member
            )

    }

    fun from() = PostResponse(
            id = id,
            title = title,
            content = content,
            member = member.nickname,
            createdAt = createdAt,
            endedAt = endedAt
        )

    fun updatePost(request: UpdatePostRequest, authenticationId: Long) {
        if (verify(authenticationId)) {
            title = request.title
            content = request.content
            return
        }
    }

    fun verify(authenticationId: Long): Boolean {
        if (this.member.id == authenticationId) {
            return true
        }

        throw AccessDeniedException("Verify Failed")
    }

}