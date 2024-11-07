package com.brandonkitt.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.brandonkitt.core.models.FilterOption

@Composable
fun FilterBox(
    filterOptions: List<FilterOption>,
    onFilterUpdated: (List<FilterOption>) -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            filterOptions.forEach { filter ->
                FilterItem(filterOption = filter, onFilterOptionUpdated = { filterOption ->
                    val updatedFilter = filterOptions.map {
                        if (it.id == filter.id) {
                            filterOption
                        } else {
                            it
                        }
                    }
                    onFilterUpdated(updatedFilter)
                })
            }
        }
    }
}