package com.sparta.donate.dto.comment.response

import java.time.LocalDate

data class CommentResponse(
    var id: Long,
    var nickname: String,
    var date: LocalDate,
    var content: String,
    var donationAmount: Long
)
