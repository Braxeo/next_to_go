package com.brandonkitt.nexttogo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.brandonkitt.nexttogo.data.dtos.AdvertisedStart
import com.brandonkitt.nexttogo.data.dtos.DistanceType
import com.brandonkitt.nexttogo.data.dtos.RaceForm
import com.brandonkitt.nexttogo.data.dtos.RaceSummary
import com.brandonkitt.nexttogo.data.dtos.RacingData
import com.brandonkitt.nexttogo.data.dtos.RacingResponse
import com.brandonkitt.nexttogo.data.dtos.TrackCondition
import com.brandonkitt.nexttogo.data.dtos.Weather
import com.brandonkitt.nexttogo.data.implementations.NextToGoRepositoryImpl
import com.brandonkitt.nexttogo.data.sources.remote.NextToGoRemoteDataSource
import com.brandonkitt.nexttogo.mvi.models.Category
import com.brandonkitt.nexttogo.mvi.models.Race
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
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import java.time.LocalDateTime
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class NextToGoRepositoryTest {
    // Rule used to ensure all actions are made on the main thread
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()
    private val mockRemoteDataSource: NextToGoRemoteDataSource = mockk()
    private val repository = NextToGoRepositoryImpl(mockRemoteDataSource)
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
    fun `when FetchRaceData intent is handled, state should update to loading and then show race data`() =
        runTest {
            val now = LocalDateTime.now().second
            // Given
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
            val expectedRaces = listOf(
                Race(
                    id = "1",
                    startTime = Date.from(Instant.ofEpochSecond(now.toLong())),
                    category = Category.HorseRacing,
                    meetingName = "meeting1",
                    raceNumber = 1
                ),
                Race(
                    id = "2",
                    startTime = Date.from(Instant.ofEpochSecond(now.toLong())),
                    category = Category.GreyHoundRacing,
                    meetingName = "meeting2",
                    raceNumber = 2
                ),
                Race(
                    id = "3",
                    startTime = Date.from(Instant.ofEpochSecond(now.toLong())),
                    category = Category.HarnessRacing,
                    meetingName = "meeting3",
                    raceNumber = 3
                ),
                Race(
                    id = "4",
                    startTime = Date.from(Instant.ofEpochSecond(now.toLong())),
                    category = Category.Other,
                    meetingName = "meeting4",
                    raceNumber = 4
                ),
            )
            coEvery { mockRemoteDataSource.fetchRaceData() } returns expectedResponse
            // When
            val races = repository.getRaceData()
            // Then
            assertThat(races).isEqualTo(expectedRaces)
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