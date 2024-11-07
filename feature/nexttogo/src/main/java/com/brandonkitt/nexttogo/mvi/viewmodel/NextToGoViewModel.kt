package com.brandonkitt.nexttogo.mvi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandonkitt.core.data.network.NetworkChangeReceiver
import com.brandonkitt.core.data.utilities.Helper.calculateCountdown
import com.brandonkitt.core.models.FilterOption
import com.brandonkitt.nexttogo.data.interfaces.NextToGoRepository
import com.brandonkitt.nexttogo.mvi.models.Category
import com.brandonkitt.nexttogo.mvi.models.Race
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class NextToGoViewModel @Inject constructor(
    private val repository: NextToGoRepository,
    private val networkChangeReceiver: NetworkChangeReceiver
) : ViewModel() {
    private val _state = MutableStateFlow(NextToGoState())
    val state: StateFlow<NextToGoState> get() = _state
    private var cachedData = emptyList<Race>()
    private var isInitialLaunch = true;

    init {
        viewModelScope.launch {
            networkChangeReceiver.networkStatusFlow.collect { isConnected ->
                // This should always run on app startup, but ensure we run
                // at least once
                if (isConnected || isInitialLaunch) {
                    isInitialLaunch = false
                    handleIntent(NextToGoIntent.FetchRaceData)
                }
            }
        }
    }

    fun handleIntent(intent: NextToGoIntent) {
        when (intent) {
            NextToGoIntent.FetchRaceData -> fetchRaceData()
            NextToGoIntent.RefreshRaceData -> fetchRaceData()
            is NextToGoIntent.RemoveRaceData -> removeRaceData(intent.id)
            is NextToGoIntent.FiltersUpdated -> updateFilters(intent.filters)
        }
    }

    private fun updateFilters(filters: List<FilterOption>) {
        _state.value = _state.value.copy(filterOptions = filters)
        updateStateWithCachedData()
    }

    private fun removeRaceData(id: String) {
        cachedData = cachedData.filter { it.id != id }
        updateStateWithCachedData()
        fetchRaceData()
    }

    private fun fetchRaceData() {
        viewModelScope.launch {
            // Set loading data
            _state.value = _state.value.copy(loading = true, error = null)

            try {
                val data = repository.getRaceData()
                cachedData = data;

                updateStateWithCachedData()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "Something went wrong")
            } finally {
                _state.value = _state.value.copy(loading = false)
            }
        }
    }

    private fun updateStateWithCachedData() {
        // Create the list of Categories to limit by
        val filtering = createCategoryFilter();
        // Sort the cached data
        val sortedData = cachedData
            .sortedBy { it.startTime } // Sort data by start time
            .dropWhile { calculateCountdown(it.startTime) < com.brandonkitt.nexttogo.data.Constants.TIMEOUT } // Drop any past timeout
            .filter { filtering.contains(it.category) } // Filter by category
            .take(5) // Only show top 5
        //_state.value = _state.value.copy(racesState = RacesState.Data(emptyList()))
        _state.value = _state.value.copy(racesState = RacesState.Data(sortedData))
    }

    private fun createCategoryFilter(): List<Category> {
        return _state.value.filterOptions
            .let { filterOptions ->
                val filters = if (_state.value.filterOptions.none { it.checked }) {
                    filterOptions
                } else {
                    filterOptions.filter { it.checked }
                }

                filters.map { Category.fromIdWithDefault(it.id) }
            }
    }
}