package com.brandonkitt.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.brandonkitt.core.models.FilterOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuBar(
    title: String,
    filterOptions: List<FilterOption>,
    onFilterUpdated: (List<FilterOption>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        // Menu Bar with Filter Icon
        TopAppBar(
            title = { Text(title) },
            actions = {
                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.testTag("Filter")
                ) {
                    Icon(Icons.Default.FilterList, contentDescription = "Filter")
                }
            }
        )
        if (expanded) {
            FilterBox(filterOptions = filterOptions, onFilterUpdated = onFilterUpdated)
        }
    }
}