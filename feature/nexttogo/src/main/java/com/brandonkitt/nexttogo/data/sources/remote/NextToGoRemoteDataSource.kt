package com.brandonkitt.nexttogo.data.sources.remote

import com.brandonkitt.nexttogo.data.dtos.RacingResponse
import com.brandonkitt.nexttogo.data.interfaces.NextToGoService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NextToGoRemoteDataSource @Inject constructor(
    private val webservice: NextToGoService
) {
    suspend fun fetchRaceData(): RacingResponse {
        return webservice.getRacing()
    }
}