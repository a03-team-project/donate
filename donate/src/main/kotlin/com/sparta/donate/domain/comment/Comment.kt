package com.sparta.donate.domain.comment

import com.sparta.donate.domain.member.Member
import com.sparta.donate.domain.post.Post
import com.sparta.donate.dto.comment.request.CommentRequest
import com.sparta.donate.global.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction


@Entity
@Table(name = "comments")
class Comment private constructor(
    _member: Member,
    _content: String,
    _post: Post
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    var id: Long? = null
        private set


    @Column(name = "content")
    var content: String = _content
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

    companion object {
        fun toEntity(request: CommentRequest, member: Member, post: Post): Comment {
            return Comment(
                _member = member,
                _content = request.content,
                _post = post
            )
        }
    }
}
