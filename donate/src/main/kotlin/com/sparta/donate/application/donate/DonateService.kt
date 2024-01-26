package com.sparta.donate.application.donate

import com.sparta.donate.domain.donate.Donate
import com.sparta.donate.dto.donate.request.DonateRequest
import com.sparta.donate.dto.donate.response.DonateResponse
import com.sparta.donate.global.auth.AuthenticationUtil.getAuthenticationUserId
import com.sparta.donate.global.common.SortOrder
import com.sparta.donate.global.exception.common.NoSuchEntityException
import com.sparta.donate.repository.donate.DonateRepository
import com.sparta.donate.repository.member.MemberRepository
import com.sparta.donate.repository.post.PostRepository
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DonateService(
    private val donateRepository: DonateRepository,
    private val memberRepository: MemberRepository,
    private val postRepository: PostRepository
) {
    @Transactional
    fun createDonate(postId: Long, request: DonateRequest): DonateResponse {
        val authenticatedId = getAuthenticationUserId()
        val post = postRepository.findByIdOrNull(postId) ?: throw NoSuchEntityException("POST")
        val member = memberRepository.findByIdOrNull(authenticatedId) ?: throw NoSuchEntityException("MEMBER")
        val donate = Donate.of(request, member, post)

        donateRepository.save(donate)

        return donate.from()
    }


    fun getAllDonateList(sortOrder: SortOrder): List<DonateResponse> =
        when (sortOrder) {
            SortOrder.DESC -> donateRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).map { it.from() }
            SortOrder.ASC -> donateRepository.findAll(Sort.by(Sort.Direction.ASC, "createdAt")).map { it.from() }
        }

    fun getAllDonateListByPostId(postId: Long, sortOrder: SortOrder): List<DonateResponse> {
        return when (sortOrder) {
            SortOrder.DESC -> donateRepository.findByPostIdOrderByCreatedAtDesc(postId).map { it.from() }
            SortOrder.ASC -> donateRepository.findByPostIdOrderByCreatedAtAsc(postId).map { it.from() }
        }
    }

    @Transactional
    fun deleteDonate(postId: Long, donateId: Long) {
        val donate = donateRepository.findByIdOrNull(donateId) ?: throw NoSuchEntityException("DONATE")
        donateRepository.delete(donate)
    }

}
