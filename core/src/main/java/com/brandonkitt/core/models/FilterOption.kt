package com.brandonkitt.core.models

data class FilterOption(
    val id: String,
    val displayName: String,
    val description: String,
    val iconResId: Int,
    val checked: Boolean,
)