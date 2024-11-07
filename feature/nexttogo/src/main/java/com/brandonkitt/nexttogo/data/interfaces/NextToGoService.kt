package com.brandonkitt.nexttogo.data.interfaces

import com.brandonkitt.nexttogo.data.dtos.RacingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NextToGoService {
    @GET("racing/")
    suspend fun getRacing(
        @Query("method") method: String = "nextraces",
        @Query("count") count: Int = 100
    ): RacingResponse
}