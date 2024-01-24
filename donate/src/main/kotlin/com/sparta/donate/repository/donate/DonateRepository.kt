package com.sparta.donate.repository.donate

import com.sparta.donate.domain.donate.Donate
import com.sparta.donate.dto.donate.response.DonateResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DonateRepository : JpaRepository<Donate, Long> {
    fun findByMemberIdAndPostId(memberId: Long, postId: Long): Donate?

    fun findAllByOrderByCreatedAtDesc(): List<DonateResponse>
    fun findAllByOrderByCreatedAtAsc(): List<DonateResponse>

    fun findByPostIdOrderByCreatedAtDesc(postId: Long): List<DonateResponse>

    fun findByPostIdOrderByCreatedAtAsc(postId: Long): List<DonateResponse>
}