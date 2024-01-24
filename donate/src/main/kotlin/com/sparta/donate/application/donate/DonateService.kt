package com.sparta.donate.application.donate

import com.sparta.donate.domain.common.SortOrder
import com.sparta.donate.domain.donate.Donate
import com.sparta.donate.dto.donate.request.DonateRequest
import com.sparta.donate.dto.donate.response.DonateResponse
import com.sparta.donate.global.exception.ModelNotFoundException
import com.sparta.donate.repository.donate.DonateRepository
import com.sparta.donate.repository.member.MemberRepository
import com.sparta.donate.repository.post.PostRepository
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
        // TODO: 로그인 한 사용자만 후원 가능
        val post = postRepository.findByIdOrNull(postId) ?: throw IllegalStateException()
        val member = memberRepository.findByIdOrNull(request.memberId) ?: throw IllegalStateException()

        donateRepository.save(Donate.toEntity(request, member, post))
            .let { return it.from() }
    }


    fun getAllDonateList(sortOrder: SortOrder): List<DonateResponse> =
        when (sortOrder) {
            SortOrder.DESC -> donateRepository.findAllByOrderByCreatedAtDesc()
            SortOrder.ASC -> donateRepository.findAllByOrderByCreatedAtAsc()
        }

    fun getAllDonateListByPostId(postId: Long, sortOrder: SortOrder): List<DonateResponse> {
        postRepository.findById(postId).let {
            if (it.isEmpty) {
                // 만약 postId에 해당하는 포스트가 없다면 예외 처리
                throw ModelNotFoundException("Post", postId)
            }
        }
        return when (sortOrder) {
            SortOrder.DESC -> donateRepository.findByPostIdOrderByCreatedAtDesc(postId)
            SortOrder.ASC -> donateRepository.findByPostIdOrderByCreatedAtAsc(postId)
        }
    }

    @Transactional
    fun deleteDonate(postId: Long, donateId: Long) {
        // TODO: 관리자만 후원 내역 삭제 가능
        donateRepository.deleteById(donateId)
    }

}