package com.brandonkitt.nexttogo.mvi.models

import java.util.Date

data class Race(
    val id: String,
    val startTime: Date,
    val category: Category,
    val meetingName: String,
    val raceNumber: Int
)