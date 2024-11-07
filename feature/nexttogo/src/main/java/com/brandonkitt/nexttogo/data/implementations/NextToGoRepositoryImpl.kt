package com.brandonkitt.nexttogo.data.implementations

import com.brandonkitt.nexttogo.data.dtos.RacingResponse
import com.brandonkitt.nexttogo.data.interfaces.NextToGoRepository
import com.brandonkitt.nexttogo.data.sources.remote.NextToGoRemoteDataSource
import com.brandonkitt.nexttogo.mvi.models.Category
import com.brandonkitt.nexttogo.mvi.models.Race
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NextToGoRepositoryImpl @Inject constructor(
    private val remoteDataSource: NextToGoRemoteDataSource
) : NextToGoRepository {
    override suspend fun getRaceData(): List<Race> {
        return remoteDataSource.fetchRaceData().toDomainModel()
    }

    private fun RacingResponse.toDomainModel(): List<Race> {
        return this.data.race_summaries.entries.map {
            Race(
                id = it.value.race_id,
                startTime = Date(it.value.advertised_start.seconds * 1000),
                category = Category.fromIdWithDefault(it.value.category_id),
                meetingName = it.value.meeting_name,
                raceNumber = it.value.race_number
            )
        }
    }
}