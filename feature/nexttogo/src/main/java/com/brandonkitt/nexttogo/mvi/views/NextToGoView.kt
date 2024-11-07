package com.brandonkitt.nexttogo.mvi.views

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.brandonkitt.core.components.MenuBar
import com.brandonkitt.nexttogo.mvi.viewmodel.NextToGoIntent
import com.brandonkitt.nexttogo.mvi.viewmodel.NextToGoIntent.RemoveRaceData
import com.brandonkitt.nexttogo.mvi.viewmodel.NextToGoViewModel
import com.brandonkitt.nexttogo.mvi.viewmodel.RacesState

/**
 * Main composable view for the NextToGo feature.
 * @param viewModel ViewModel used to give State to the view, DI by Hilt but included inside
 * constructor to allow for Preview + Mocking
 */
@Composable
fun NextToGoView(viewModel: NextToGoViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            MenuBar(
                title = "Next To Go",
                filterOptions = state.filterOptions,
                onFilterUpdated = {
                    viewModel.handleIntent(NextToGoIntent.FiltersUpdated(it))
                })

            Spacer(modifier = Modifier.height(16.dp))

            when (val racesState = state.racesState) {
                is RacesState.Data -> {
                    LazyColumn {
                        items(racesState.races) { race ->
                            NextToGoRaceItem(race) { id ->
                                viewModel.handleIntent(RemoveRaceData(id))
                            }
                        }
                    }

                    if (racesState.races.isEmpty()) {
                        Text(
                            text = "Damn! No races at the moment.",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 8.dp)
                        )
                        Button(
                            onClick = { viewModel.handleIntent(NextToGoIntent.RefreshRaceData) },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text(text = "Refresh")
                        }
                    }
                }

                RacesState.Initial -> {
                    Text(
                        text = "Connecting you to the races!",
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }

        if (state.error != null) {
            // Show Toast error message
            Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_LONG).show()
            // Allow for manual refresh
            Button(
                onClick = { viewModel.handleIntent(NextToGoIntent.RefreshRaceData) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Text(text = "Try again")
            }
        }

        if (state.loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .testTag("progress_indicator")
            )
        }
    }
}