package com.brandonkitt.nexttogo.data.dtos

data class RacingResponse(
    val status: Int,
    val data: RacingData,
    val message: String
)