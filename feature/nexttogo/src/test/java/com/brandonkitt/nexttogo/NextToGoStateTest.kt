package com.brandonkitt.nexttogo

import com.brandonkitt.nexttogo.mvi.models.Category
import com.brandonkitt.nexttogo.mvi.viewmodel.NextToGoState
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NextToGoStateTest {
    @Test
    fun `default contains all except 'Others' option`() {
        // Given
        val expectedDefaults = Category.entries
            .filter { it.id != "" }
            .map { it.toFilterOption() }
        // When
        val sut = NextToGoState()
        // Then
        assertThat(sut.filterOptions).isEqualTo(expectedDefaults)
        assertThat(NextToGoState.defaultFilterOptions).isEqualTo(expectedDefaults)
    }
}