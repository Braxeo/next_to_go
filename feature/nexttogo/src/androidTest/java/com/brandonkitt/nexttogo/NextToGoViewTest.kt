package com.brandonkitt.nexttogo

import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.brandonkitt.nexttogo.mvi.models.Category
import com.brandonkitt.nexttogo.mvi.models.Race
import com.brandonkitt.nexttogo.mvi.viewmodel.NextToGoIntent
import com.brandonkitt.nexttogo.mvi.viewmodel.NextToGoState
import com.brandonkitt.nexttogo.mvi.viewmodel.NextToGoViewModel
import com.brandonkitt.nexttogo.mvi.viewmodel.RacesState
import com.brandonkitt.nexttogo.mvi.views.NextToGoView
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class NextToGoViewTest {
    // Create a ComposeTestRule to manage the Compose environment
    @get:Rule
    val composeTestRule = createComposeRule()
    private val mockViewModel: NextToGoViewModel = mockk(relaxed = true)
    private val testStateFlow = MutableStateFlow(NextToGoState())
    @Before
    fun setup() {
        // Set the mock ViewModel's state to use the test state flow
        coEvery { mockViewModel.state } returns testStateFlow
    }
    // Helper to set the content of the composable view
    private fun setContent() {
        composeTestRule.setContent {
            NextToGoView(viewModel = mockViewModel)
        }
    }
    @Test
    fun initialState_displaysConnectingText() {
        // Set up initial state to be "Initial"
        testStateFlow.value = NextToGoState(racesState = RacesState.Initial)
        setContent()
        // Check for the initial message
        composeTestRule.onNodeWithText("Connecting you to the races!")
            .assertExists()
    }
    @Test
    fun loadingState_showsProgressIndicator() {
        // Set state to loading
        testStateFlow.value = NextToGoState(loading = true)
        setContent()
        // Verify the loading indicator is displayed
        composeTestRule.onNodeWithTag("progress_indicator")
            .assertExists()
    }
    @Test
    fun dataState_displaysRaceItems() = runTest {
        val now = Date()
        val races = listOf(
            Race("1", now, Category.HorseRacing, "meeting1", 1),
            Race("2", now, Category.GreyHoundRacing, "meeting2", 2)
        ) // Mock Race data
        testStateFlow.value = NextToGoState(racesState = RacesState.Data(races))
        setContent()
        // Check that each race item is displayed
        races.forEach { race ->
            composeTestRule.onNodeWithText(race.meetingName).assertExists()
        }
    }
    @Test
    fun emptyDataState_displaysNoRacesMessage() = runTest {
        testStateFlow.value = NextToGoState(racesState = RacesState.Data(emptyList()))
        setContent()
        // Check for the empty races message
        composeTestRule.onNodeWithText("Damn! No races at the moment.").assertExists()
    }
    @Test
    fun errorState_displaysErrorMessageAndRetryButton() = runTest {
        testStateFlow.value = NextToGoState(error = "Network Error")
        setContent()
        // Check error message is shown
        // Disabled due to Toast not showing on UI Tree
        //composeTestRule.onNodeWithText("Network Error").assertExists()
        // Check that the retry button exists and perform click
        val retryButton = composeTestRule.onNodeWithText("Try again")
        retryButton.assertExists()
        retryButton.performClick()
        // Verify that the intent to refresh data was called
        coVerify { mockViewModel.handleIntent(NextToGoIntent.RefreshRaceData) }
    }
    @Test
    fun filterOptionsUpdated_triggersCorrectIntent() = runTest {
        val newFilterOption = listOf(
            Category.HorseRacing.toFilterOption(true),
            Category.GreyHoundRacing.toFilterOption(false),
            Category.HarnessRacing.toFilterOption(true),
        ) // Mock FilterOption
        testStateFlow.value = NextToGoState(filterOptions = newFilterOption)
        setContent()

        composeTestRule.onNodeWithTag("Filter").performClick()

        composeTestRule.onNodeWithTag("Checkbox_${Category.HorseRacing.id}").assertIsOn()
        composeTestRule.onNodeWithTag("Checkbox_${Category.GreyHoundRacing.id}").assertIsOff()
        composeTestRule.onNodeWithTag("Checkbox_${Category.HarnessRacing.id}").assertIsOn()
    }
}
