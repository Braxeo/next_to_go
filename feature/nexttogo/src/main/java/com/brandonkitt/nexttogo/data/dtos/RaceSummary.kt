package com.brandonkitt.nexttogo.data.dtos

data class RaceSummary(
    val race_id: String,
    val race_name: String,
    val race_number: Int,
    val meeting_id: String,
    val meeting_name: String,
    val category_id: String,
    val advertised_start: AdvertisedStart,
    val race_form: RaceForm,
    val additional_data: String,
    val generated: Int,
    val silk_base_url: String,
    val race_comment_alternative: String,
    val venue_id: String,
    val venue_name: String,
    val venue_state: String,
    val venue_country: String
)