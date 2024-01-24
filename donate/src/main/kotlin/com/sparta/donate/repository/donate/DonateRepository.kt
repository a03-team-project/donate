package com.sparta.donate.repository.donate

import com.sparta.donate.domain.donate.Donate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DonateRepository : JpaRepository<Donate, Long> {
    fun findByMemberIdAndPostId(memberId: Long, postId: Long): Donate?
}