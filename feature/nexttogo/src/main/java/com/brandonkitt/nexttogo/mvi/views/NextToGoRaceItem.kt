package com.brandonkitt.nexttogo.mvi.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.brandonkitt.core.data.utilities.Helper.calculateCountdown
import com.brandonkitt.nexttogo.data.Constants
import com.brandonkitt.nexttogo.data.Constants.LARGE_FONT_THRESHOLD
import com.brandonkitt.nexttogo.data.Constants.THRESHOLD_FINISHED
import com.brandonkitt.nexttogo.data.Constants.THRESHOLD_FINISHED_COLOR_DARK
import com.brandonkitt.nexttogo.data.Constants.THRESHOLD_FINISHED_COLOR_LIGHT
import com.brandonkitt.nexttogo.data.Constants.THRESHOLD_WARN
import com.brandonkitt.nexttogo.data.Constants.THRESHOLD_WARN_COLOR_DARK
import com.brandonkitt.nexttogo.data.Constants.THRESHOLD_WARN_COLOR_LIGHT
import com.brandonkitt.nexttogo.mvi.models.Race
import kotlinx.coroutines.delay

@Composable
fun NextToGoRaceItem(race: Race, onTimeLimitReached: (id: String) -> Unit) {
    var countdown by remember { mutableLongStateOf(calculateCountdown(race.startTime)) }
    // Use rememberUpdatedState to ensure we have a current callback function
    val currentOnTimeLimitReached = rememberUpdatedState(newValue = onTimeLimitReached)
    val darkMode = isSystemInDarkTheme();
    // Get the current text scale
    val fontScale = LocalContext.current.resources.configuration.fontScale
    var isLargeFont by remember { mutableStateOf(false) }
    // Observe the text scale and determine if it's larger than the threshold
    LaunchedEffect(Unit) {
        isLargeFont = fontScale > LARGE_FONT_THRESHOLD
    }
    // Start a coroutine to update the countdown every second
    LaunchedEffect(race.id) {
        // Reset countdown for new races
        countdown = calculateCountdown(race.startTime)
        // Notify early if already outside timeout
        if (countdown < Constants.TIMEOUT) currentOnTimeLimitReached.value(
            race.id
        )

        while (countdown >= Constants.TIMEOUT) {
            delay(1000)
            countdown = calculateCountdown(race.startTime)
            if (countdown < Constants.TIMEOUT) currentOnTimeLimitReached.value(
                race.id
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(getBackgroundColorForThreshold(countdown, darkMode))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = race.category.iconResId),
                contentDescription = race.category.description,
                colorFilter = ColorFilter.tint(if (darkMode) Color.White else Color.Black),
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = race.meetingName)
                Text(text = "Race Number: ${race.raceNumber}")
                // Move countdown to bottom of cell for large text
                if (isLargeFont) Text(text = formatCountdown(countdown))
            }
        }
        // Countdown Timer, default to side of cell
        if (!isLargeFont) {
            Text(
                text = formatCountdown(countdown),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

private fun formatCountdown(seconds: Long): String {
    return if (seconds >= 0) {
        String.format("%02d:%02d", seconds / 60, seconds % 60)
    } else {
        String.format("-%02d:%02d", -seconds / 60, -seconds % 60)
    }
}

private fun getBackgroundColorForThreshold(countdown: Long, darkMode: Boolean): Color {
    return when (true) {
        (countdown < THRESHOLD_FINISHED && darkMode) -> THRESHOLD_FINISHED_COLOR_DARK
        (countdown < THRESHOLD_FINISHED) -> THRESHOLD_FINISHED_COLOR_LIGHT
        (countdown < THRESHOLD_WARN && darkMode) -> THRESHOLD_WARN_COLOR_DARK
        (countdown < THRESHOLD_WARN) -> THRESHOLD_WARN_COLOR_LIGHT
        else -> Color.Transparent
    }
}