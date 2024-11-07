package com.brandonkitt.nexttogo.data

import com.brandonkitt.core.theme.DarkOrange
import com.brandonkitt.core.theme.DarkRed
import com.brandonkitt.core.theme.LightOrange
import com.brandonkitt.core.theme.LightRed

object Constants {
    const val TIMEOUT = -60; // Timeout race 60 seconds after start time
    // Thresholds for color changing when race is about to end
    const val THRESHOLD_WARN = 10;
    const val THRESHOLD_FINISHED = 0;
    // Colors for Threshold (might be better way to do this?)
    val THRESHOLD_WARN_COLOR_DARK = DarkOrange
    val THRESHOLD_WARN_COLOR_LIGHT = LightOrange
    val THRESHOLD_FINISHED_COLOR_DARK = DarkRed
    val THRESHOLD_FINISHED_COLOR_LIGHT = LightRed
    // Threshold for layout transition
    const val LARGE_FONT_THRESHOLD = 1.6f
}