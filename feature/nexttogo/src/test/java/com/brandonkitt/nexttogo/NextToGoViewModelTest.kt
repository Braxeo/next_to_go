package com.brandonkitt.nexttogo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.brandonkitt.core.data.network.NetworkChangeReceiver
import com.brandonkitt.nexttogo.data.interfaces.NextToGoRepository
import com.brandonkitt.nexttogo.mvi.models.Category
import com.brandonkitt.nexttogo.mvi.models.Race
import com.brandonkitt.nexttogo.mvi.viewmodel.NextToGoIntent
import com.brandonkitt.nexttogo.mvi.viewmodel.NextToGoViewModel
import com.brandonkitt.nexttogo.mvi.viewmodel.RacesState
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class NextToGoViewModelTest {
    // Rule used to ensure all actions are made on the main thread
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()
    private val mockRepository: NextToGoRepository = mockk()
    private val mockNetworkChangeReceiver: NetworkChangeReceiver = mockk()
    private lateinit var viewModel: NextToGoViewModel
    private val networkConnectionFlow = MutableSharedFlow<Boolean>(replay = 1)
    @Before
    fun setUp() {
        // Use a TestCoroutineDispatcher for coroutine control
        Dispatchers.setMain(testDispatcher)
        coEvery { mockNetworkChangeReceiver.networkStatusFlow } returns networkConnectionFlow
        // Initialize the ViewModel with mocked dependencies
        viewModel = NextToGoViewModel(mockRepository, mockNetworkChangeReceiver)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun `when Initial State is loaded by any network connection state, should fetch data`() =
        runTest {
            // Given
            val expectedRaces = listOf(
                Race(
                    "1",
                    startTime = Date.from(Instant.now().plusSeconds(60)),
                    category = Category.HorseRacing,
                    meetingName = "Meeting1",
                    raceNumber = 1
                ),
                Race(
                    "2",
                    startTime = Date.from(Instant.now().plusSeconds(120)),
                    category = Category.HarnessRacing,
                    meetingName = "Meeting2",
                    raceNumber = 2
                ),
            )
            // When
            coEvery { mockRepository.getRaceData() } returns expectedRaces
            // Initial query should run even if false is emitted
            networkConnectionFlow.emit(false)

            testDispatcher.scheduler.advanceUntilIdle()
            // Then
            val actualState = viewModel.state.value
            val actualLoading = actualState.loading
            val actualError = actualState.error
            val actualRaces = (actualState.racesState as RacesState.Data).races

            assertThat(actualLoading).isFalse()
            assertThat(actualError).isNull()
            assertThat(actualRaces).isEqualTo(expectedRaces)
        }
    @Test
    fun `when network connection changed, state should refresh`() =
        runTest {
            // Given
            val initialRaces = listOf(
                Race(
                    "1",
                    startTime = Date.from(Instant.now().plusSeconds(60)),
                    category = Category.HorseRacing,
                    meetingName = "Meeting1",
                    raceNumber = 1
                ),
                Race(
                    "2",
                    startTime = Date.from(Instant.now().plusSeconds(120)),
                    category = Category.HarnessRacing,
                    meetingName = "Meeting2",
                    raceNumber = 2
                ),
            )
            // When
            coEvery { mockRepository.getRaceData() } returns initialRaces
            viewModel.handleIntent(NextToGoIntent.FetchRaceData)
            testDispatcher.scheduler.advanceUntilIdle()
            val updatedRaces = listOf(
                Race(
                    "3",
                    startTime = Date.from(Instant.now().plusSeconds(60)),
                    category = Category.HorseRacing,
                    meetingName = "Meeting3",
                    raceNumber = 3
                ),
                Race(
                    "4",
                    startTime = Date.from(Instant.now().plusSeconds(120)),
                    category = Category.HarnessRacing,
                    meetingName = "Meeting4",
                    raceNumber = 4
                ),
            )
            coEvery { mockRepository.getRaceData() } returns updatedRaces
            networkConnectionFlow.emit(true)
            testDispatcher.scheduler.advanceUntilIdle()
            // Then
            val actualState = viewModel.state.value
            val actualLoading = actualState.loading
            val actualError = actualState.error
            val actualRaces = (actualState.racesState as RacesState.Data).races

            assertThat(actualLoading).isFalse()
            assertThat(actualError).isNull()
            assertThat(actualRaces).isEqualTo(updatedRaces)
        }
    @Test
    fun `when FetchRaceData intent is handled, state should update to loading and then show race data`() =
        runTest {
            // Given
            val expectedRaces = listOf(
                Race(
                    "1",
                    startTime = Date.from(Instant.now().plusSeconds(60)),
                    category = Category.HorseRacing,
                    meetingName = "Meeting1",
                    raceNumber = 1
                ),
                Race(
                    "2",
                    startTime = Date.from(Instant.now().plusSeconds(120)),
                    category = Category.HarnessRacing,
                    meetingName = "Meeting2",
                    raceNumber = 2
                ),
            )
            // When
            coEvery { mockRepository.getRaceData() } returns expectedRaces
            viewModel.handleIntent(NextToGoIntent.FetchRaceData)
            testDispatcher.scheduler.advanceUntilIdle()
            // Then
            val actualState = viewModel.state.value
            val actualLoading = actualState.loading
            val actualError = actualState.error
            val actualRaces = (actualState.racesState as RacesState.Data).races

            assertThat(actualLoading).isFalse()
            assertThat(actualError).isNull()
            assertThat(actualRaces).isEqualTo(expectedRaces)
        }
    @Test
    fun `when RefreshRaceData intent is handled, state should update to loading and refresh race data`() =
        runTest {
            // Given
            val initialRaces = listOf(
                Race(
                    "1",
                    startTime = Date.from(Instant.now().plusSeconds(60)),
                    category = Category.HorseRacing,
                    meetingName = "Meeting1",
                    raceNumber = 1
                ),
                Race(
                    "2",
                    startTime = Date.from(Instant.now().plusSeconds(120)),
                    category = Category.HarnessRacing,
                    meetingName = "Meeting2",
                    raceNumber = 2
                ),
            )
            val updatedRaces = listOf(
                Race(
                    "3",
                    startTime = Date.from(Instant.now().plusSeconds(180)),
                    category = Category.HorseRacing,
                    meetingName = "Meeting3",
                    raceNumber = 3
                ),
                Race(
                    "4",
                    startTime = Date.from(Instant.now().plusSeconds(240)),
                    category = Category.HarnessRacing,
                    meetingName = "Meeting4",
                    raceNumber = 4
                ),
            )
            // When
            // Send Initial Data
            coEvery { mockRepository.getRaceData() } returns initialRaces
            viewModel.handleIntent(NextToGoIntent.FetchRaceData)
            testDispatcher.scheduler.advanceUntilIdle()
            // Send updated Data
            coEvery { mockRepository.getRaceData() } returns updatedRaces
            viewModel.handleIntent(NextToGoIntent.RefreshRaceData)
            testDispatcher.scheduler.advanceUntilIdle()
            // Then
            val actualState = viewModel.state.value
            val actualLoading = actualState.loading
            val actualError = actualState.error
            val actualRaces = (actualState.racesState as RacesState.Data).races

            assertThat(actualLoading).isFalse()
            assertThat(actualError).isNull()
            assertThat(actualRaces).isEqualTo(updatedRaces)
        }
    @Test
    fun `when RemoveRaceData intent is handled, state should remove specific race data`() =
        runTest {
            // Given
            val initialRaces = listOf(
                Race(
                    "1",
                    startTime = Date.from(Instant.now().plusSeconds(60)),
                    category = Category.HorseRacing,
                    meetingName = "Meeting1",
                    raceNumber = 1
                ),
                Race(
                    "2",
                    startTime = Date.from(Instant.now().plusSeconds(120)),
                    category = Category.HarnessRacing,
                    meetingName = "Meeting2",
                    raceNumber = 2
                ),
            )
            // When
            coEvery { mockRepository.getRaceData() } returns initialRaces
            viewModel.handleIntent(NextToGoIntent.FetchRaceData)
            testDispatcher.scheduler.advanceUntilIdle()
            coEvery { mockRepository.getRaceData() } throws Exception("Expected error")
            viewModel.handleIntent(NextToGoIntent.RemoveRaceData("1"))
            testDispatcher.scheduler.advanceUntilIdle()
            // Then
            val expectedRaces = initialRaces.drop(1)
            val actualState = viewModel.state.value
            val actualLoading = actualState.loading
            val actualError = actualState.error
            val actualRaces = (actualState.racesState as RacesState.Data).races
            /**
             * Due to the StateFlow testing method (test the end result, not individual state
             * progress, we throw an error to ensure @initialRaces isn't refreshed into the races
             */
            assertThat(actualLoading).isFalse()
            assertThat(actualError).isEqualTo("Expected error")
            assertThat(actualRaces).isEqualTo(expectedRaces)
        }
    @Test
    fun `when FiltersUpdated intent is handled, state should filter by specific race data`() =
        runTest {
            // Given
            val filtering = listOf(
                Category.GreyHoundRacing.toFilterOption(),
                Category.HorseRacing.toFilterOption(),
                Category.HarnessRacing.toFilterOption(),
            )
            val initialRaces = listOf(
                Race(
                    "1",
                    startTime = Date.from(Instant.now().plusSeconds(60)),
                    category = Category.HorseRacing,
                    meetingName = "Meeting1",
                    raceNumber = 1
                ),
                Race(
                    "2",
                    startTime = Date.from(Instant.now().plusSeconds(120)),
                    category = Category.HarnessRacing,
                    meetingName = "Meeting2",
                    raceNumber = 2
                ),
                Race(
                    "3",
                    startTime = Date.from(Instant.now().plusSeconds(180)),
                    category = Category.GreyHoundRacing,
                    meetingName = "Meeting3",
                    raceNumber = 3
                ),
            )
            // When
            coEvery { mockRepository.getRaceData() } returns initialRaces
            viewModel.handleIntent(NextToGoIntent.FetchRaceData)
            testDispatcher.scheduler.advanceUntilIdle()
            // Horse Filtering
            val horseFiltering = filtering
                .filter { it.id != Category.HorseRacing.id }
                .toMutableList()
                .apply { add(Category.HorseRacing.toFilterOption(true)) }
            viewModel.handleIntent(NextToGoIntent.FiltersUpdated(horseFiltering))
            testDispatcher.scheduler.advanceUntilIdle()
            // Then
            var expectedRaces = listOf(initialRaces[0])
            var actualState = viewModel.state.value
            var actualLoading = actualState.loading
            var actualError = actualState.error
            var actualRaces = (actualState.racesState as RacesState.Data).races
            var actualFilterOptions = actualState.filterOptions

            assertThat(actualLoading).isFalse()
            assertThat(actualError).isNull()
            assertThat(actualRaces).isEqualTo(expectedRaces)
            assertThat(actualFilterOptions).isEqualTo(horseFiltering)
            // Greyhound Filtering
            val greyhoundFiltering = filtering
                .filter { it.id != Category.GreyHoundRacing.id }
                .toMutableList()
                .apply { add(Category.GreyHoundRacing.toFilterOption(true)) }
            viewModel.handleIntent(NextToGoIntent.FiltersUpdated(greyhoundFiltering))
            testDispatcher.scheduler.advanceUntilIdle()
            // Then
            expectedRaces = listOf(initialRaces[2])
            actualState = viewModel.state.value
            actualLoading = actualState.loading
            actualError = actualState.error
            actualRaces = (actualState.racesState as RacesState.Data).races
            actualFilterOptions = actualState.filterOptions

            assertThat(actualLoading).isFalse()
            assertThat(actualError).isNull()
            assertThat(actualRaces).isEqualTo(expectedRaces)
            assertThat(actualFilterOptions).isEqualTo(greyhoundFiltering)
            // Harness Filtering
            val harnessFiltering = filtering
                .filter { it.id != Category.HarnessRacing.id }
                .toMutableList()
                .apply { add(Category.HarnessRacing.toFilterOption(true)) }
            viewModel.handleIntent(NextToGoIntent.FiltersUpdated(harnessFiltering))
            testDispatcher.scheduler.advanceUntilIdle()
            // Then
            expectedRaces = listOf(initialRaces[1])
            actualState = viewModel.state.value
            actualLoading = actualState.loading
            actualError = actualState.error
            actualRaces = (actualState.racesState as RacesState.Data).races
            actualFilterOptions = actualState.filterOptions

            assertThat(actualLoading).isFalse()
            assertThat(actualError).isNull()
            assertThat(actualRaces).isEqualTo(expectedRaces)
            assertThat(actualFilterOptions).isEqualTo(harnessFiltering)
        }
}

