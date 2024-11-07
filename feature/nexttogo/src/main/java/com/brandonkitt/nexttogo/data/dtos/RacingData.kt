package com.brandonkitt.nexttogo.data.dtos

data class RacingData(
    val next_to_go_ids: List<String>,
    val race_summaries: Map<String, RaceSummary>
)