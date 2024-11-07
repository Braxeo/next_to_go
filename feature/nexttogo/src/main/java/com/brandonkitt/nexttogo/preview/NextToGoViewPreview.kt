package com.brandonkitt.nexttogo.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.brandonkitt.core.theme.NextToGoTheme
import com.brandonkitt.nexttogo.mvi.views.NextToGoView

@Preview
@Composable
fun NextToGoViewPreview() {
    // Used to preview ViewModel with mock data
    NextToGoTheme {
        NextToGoView(viewModel = NextToGoViewModelPreview())
    }
}