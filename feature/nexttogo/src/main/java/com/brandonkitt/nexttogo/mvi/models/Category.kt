package com.brandonkitt.nexttogo.mvi.models

import com.brandonkitt.core.models.FilterOption
import com.brandonkitt.nexttogo.R

enum class Category(
    val id: String,
    val iconResId: Int,
    val description: String,
    val displayName: String
) {
    HorseRacing(
        id = "4a2788f8-e825-4d36-9894-efd4baf1cfae",
        iconResId = R.drawable.horse_solid,
        description = "Horse Racing Icon",
        displayName = "Horse Racing"
    ),
    GreyHoundRacing(
        id = "9daef0d7-bf3c-4f50-921d-8e818c60fe61",
        iconResId = R.drawable.dog_solid,
        description = "Greyhound Racing Icon",
        displayName = "Greyhound Racing"
    ),
    HarnessRacing(
        id = "161d9be2-e909-4326-8c2c-35ed71fb460b",
        iconResId = R.drawable.hat_cowboy_solid,
        description = "Harness Racing Icon",
        displayName = "Harness Racing"
    ),
    Other(
        id = "",
        iconResId = R.drawable.question_solid,
        description = "Question Mark Icon",
        displayName = "Other"
    );

    companion object {
        fun fromIdWithDefault(fromId: String, default: Category = Other): Category {
            return entries.firstOrNull { it.id == fromId } ?: default
        }
    }
    /**
     * Used to convert into known filterOption class.
     * Opted for this as a simple conversion method instead of updating core library class
     * to interface as the Category enum class gets too messy when inheriting the interface.
     * @see FilterOption
     */
    fun toFilterOption(checked: Boolean = false): FilterOption {
        return FilterOption(
            id = this.id,
            displayName = this.displayName,
            description = this.description,
            iconResId = this.iconResId,
            checked = checked
        )
    }
}