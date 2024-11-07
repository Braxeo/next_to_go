package com.brandonkitt.core.data.utilities

import java.util.Date

object Helper {
    /**
     * Calculates a countdown for the given startTime down to now.
     * Can give negative seconds if startTime is past now.
     * @return Seconds remaining or past.
     */
    fun calculateCountdown(startTime: Date): Long {
        val now = Date()
        return (startTime.time - now.time) / 1000
    }
}