package com.brandonkitt.nexttogo.mvi.viewmodel

import com.brandonkitt.core.models.FilterOption
import com.brandonkitt.nexttogo.mvi.models.Category
import com.brandonkitt.nexttogo.mvi.models.Race

/**
 * State object for the NextToGo feature module.
 * FilterOptions: State for filter
 * RacesState: State for Race data
 * Loading: State for loading race data
 */
data class NextToGoState(
    val filterOptions: List<FilterOption> = defaultFilterOptions,
    val racesState: RacesState = RacesState.Initial,
    val error: String? = null,
    val loading: Boolean = false
) {
    companion object {
        // Default filter options, excludes the "Other" option
        val defaultFilterOptions =
            Category.entries.filter { it.id != "" }.map { it.toFilterOption() }
    }
}
/**
 * State object for Races data.
 * Initial: Default state when no data has been loaded, clearer than having it nullable
 * Error: Error state for when Race data throws an exception, either network or parsing.
 * Data: Races data to be displayed
 */
sealed class RacesState {
    data object Initial : RacesState()
    data class Data(val races: List<Race>) : RacesState()
}