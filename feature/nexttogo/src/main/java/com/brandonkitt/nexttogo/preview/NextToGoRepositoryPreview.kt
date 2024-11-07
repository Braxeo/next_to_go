package com.brandonkitt.nexttogo.preview

import android.os.Build
import androidx.annotation.RequiresApi
import com.brandonkitt.nexttogo.data.interfaces.NextToGoRepository
import com.brandonkitt.nexttogo.mvi.models.Category
import com.brandonkitt.nexttogo.mvi.models.Race
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

/**
 * Mock Repository to provide data for Preview
 */
class NextToGoRepositoryPreview : NextToGoRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getRaceData(): List<Race> {
        return (1..10).map { createRaceData(it) }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createRaceData(raceNumber: Int): Race {
        return Race(
            // Randomize ID otherwise duplicate IDs causes issues when rendering
            id = LocalDateTime.now().atZone(ZoneId.systemDefault())
                .toInstant().epochSecond.toString(),
            startTime = Date
                .from(
                    LocalDateTime.now().plusSeconds(raceNumber.toLong() * 5)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                ),
            category = Category.entries[raceNumber / Category.entries.size],
            //category = Category.HarnessRacing,
            meetingName = "MeetingName $raceNumber",
            raceNumber = raceNumber
        )
    }
}