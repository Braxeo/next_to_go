package com.brandonkitt.nexttogo

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.brandonkitt.nexttogo.mvi.models.Race
import com.brandonkitt.nexttogo.mvi.views.NextToGoRaceItem
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NextToGoRaceItemTest {
    // Create a ComposeTestRule to manage the Compose environment
    @get:Rule
    val composeTestRule = createComposeRule()
    @Before
    fun setup() {
    }
    // Helper to set the content of the composable view
    private fun setContent(race: Race, onTimeLimitReached: (String) -> Unit) {
        composeTestRule.setContent {
            NextToGoRaceItem(race, onTimeLimitReached)
        }
    }
    @Test
    fun initialState_displaysConnectingText() {
        // Set up initial state to be "Initial"
        // Check for the initial message
        composeTestRule.onNodeWithText("Connecting you to the races!")
            .assertExists()
    }
    @Test
    fun loadingState_showsProgressIndicator() {
    }
}