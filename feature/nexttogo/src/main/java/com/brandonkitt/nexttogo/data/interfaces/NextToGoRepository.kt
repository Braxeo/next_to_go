package com.brandonkitt.nexttogo.data.interfaces

import com.brandonkitt.nexttogo.mvi.models.Race

interface NextToGoRepository {
    suspend fun getRaceData(): List<Race>
}