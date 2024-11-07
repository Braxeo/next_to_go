package com.brandonkitt.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brandonkitt.core.models.FilterOption

@Composable
fun FilterItem(filterOption: FilterOption, onFilterOptionUpdated: (FilterOption) -> Unit) {
    val darkMode = isSystemInDarkTheme();

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Icon and Title
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = filterOption.iconResId),
                colorFilter = ColorFilter.tint(if (darkMode) Color.White else Color.Black),
                contentDescription = filterOption.description,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = filterOption.displayName,
                    style = TextStyle(fontSize = 12.sp)
                )
            }
        }
        Checkbox(
            checked = filterOption.checked,
            onCheckedChange = { check ->
                val updatedFilterOption = filterOption.copy(checked = check)
                onFilterOptionUpdated(updatedFilterOption)
            },
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .testTag("Checkbox_${filterOption.id}")  // Assign a unique tag
        )
    }
}