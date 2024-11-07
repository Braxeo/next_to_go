package com.brandonkitt.nexttogo.mvi.viewmodel

import com.brandonkitt.core.models.FilterOption

sealed class NextToGoIntent {
    data object FetchRaceData : NextToGoIntent()
    data object RefreshRaceData : NextToGoIntent()
    data class RemoveRaceData(val id: String) : NextToGoIntent()
    data class FiltersUpdated(val filters: List<FilterOption>) : NextToGoIntent()
}