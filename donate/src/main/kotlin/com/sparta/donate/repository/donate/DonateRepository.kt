package com.sparta.donate.repository.donate

import com.sparta.donate.domain.donate.Donate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DonateRepository : JpaRepository<Donate, Long> {
    fun findByMemberId(memberId: Long): List<Donate>?
    fun findByPostId(postId: Long): List<Donate>?
    fun findByMemberIdAndPostId(memberId: Long, postId: Long):List<Donate>?
    fun findByPostIdOrderByCreatedAtDesc(postId: Long): List<Donate>
    fun findByPostIdOrderByCreatedAtAsc(postId: Long): List<Donate>
}
