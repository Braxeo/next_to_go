package com.brandonkitt.nexttogo.data.dtos

data class RaceForm(
    val distance: Int,
    val distance_type: DistanceType,
    val distance_type_id: String,
    val track_condition: TrackCondition,
    val track_condition_id: String,
    val weather: Weather,
    val weather_id: String,
    val race_comment: String
)