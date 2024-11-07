package com.brandonkitt.nexttogo

import com.brandonkitt.nexttogo.data.dtos.AdvertisedStart
import com.brandonkitt.nexttogo.data.dtos.DistanceType
import com.brandonkitt.nexttogo.data.dtos.RaceForm
import com.brandonkitt.nexttogo.data.dtos.RaceSummary
import com.brandonkitt.nexttogo.data.dtos.RacingData
import com.brandonkitt.nexttogo.data.dtos.RacingResponse
import com.brandonkitt.nexttogo.data.dtos.TrackCondition
import com.brandonkitt.nexttogo.data.dtos.Weather
import com.brandonkitt.nexttogo.data.interfaces.NextToGoService
import com.brandonkitt.nexttogo.data.sources.remote.NextToGoRemoteDataSource
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class NextToGoRemoteDataSourceTest {
    private val testDispatcher = StandardTestDispatcher()
    private val mockWebService: NextToGoService = mockk()
    private val remoteDataSource = NextToGoRemoteDataSource(mockWebService)
    @Before
    fun setUp() {
        // Use a TestCoroutineDispatcher for coroutine control
        Dispatchers.setMain(testDispatcher)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun `When the remoteDataSource fetches racing data it uses the get racing data endpoint`() =
        runTest {
            // Given
            val now = LocalDateTime.now().second
            val expectedResponse = RacingResponse(
                status = 200,
                data = RacingData(
                    next_to_go_ids = listOf(),
                    race_summaries = mapOf(
                        "1" to createRaceSummary(
                            "1",
                            1,
                            "meeting1",
                            now,
                            "4a2788f8-e825-4d36-9894-efd4baf1cfae"
                        ),
                        "2" to createRaceSummary(
                            "2",
                            2,
                            "meeting2",
                            now,
                            "9daef0d7-bf3c-4f50-921d-8e818c60fe61"
                        ),
                        "3" to createRaceSummary(
                            "3",
                            3,
                            "meeting3",
                            now,
                            "161d9be2-e909-4326-8c2c-35ed71fb460b"
                        ),
                        "4" to createRaceSummary(
                            "4",
                            4,
                            "meeting4",
                            now,
                            "161d5be2-e909-4366-8c2c-35ed713b460b"
                        ),
                    )
                ),
                message = ""
            )

            coEvery { mockWebService.getRacing() } returns expectedResponse
            // When
            val result = remoteDataSource.fetchRaceData()
            // Then
            assertThat(result).isEqualTo(expectedResponse)
        }

    private fun createRaceSummary(
        raceId: String,
        raceNumber: Int,
        meetingName: String,
        start: Int,
        categoryId: String
    ): RaceSummary {
        return RaceSummary(
            race_id = raceId,
            race_name = "",
            race_number = raceNumber,
            meeting_id = "",
            meeting_name = meetingName,
            category_id = categoryId,
            advertised_start = AdvertisedStart(start.toLong()),
            race_form = RaceForm(
                distance = 1,
                distance_type = DistanceType(
                    id = "",
                    name = "",
                    short_name = ""
                ),
                distance_type_id = "",
                track_condition = TrackCondition(
                    id = "",
                    name = "",
                    short_name = ""
                ),
                track_condition_id = "",
                weather = Weather(
                    id = "",
                    name = "",
                    short_name = "",
                    icon_uri = ""
                ),
                weather_id = "",
                race_comment = "",
            ),
            additional_data = "",
            generated = 0,
            silk_base_url = "",
            race_comment_alternative = "",
            venue_id = "",
            venue_name = "",
            venue_state = "",
            venue_country = "",
        )
    }
}