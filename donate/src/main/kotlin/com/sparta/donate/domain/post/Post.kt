package com.sparta.donate.domain.post

import com.sparta.donate.domain.member.Member
import com.sparta.donate.dto.post.request.UpdatePostRequest
import com.sparta.donate.global.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Table(name = "posts")
class Post(
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

    fun updatePost(request: UpdatePostRequest) {
        request.title?.let { this.title = it }
        request.content?.let { this.content = it }

    }


}