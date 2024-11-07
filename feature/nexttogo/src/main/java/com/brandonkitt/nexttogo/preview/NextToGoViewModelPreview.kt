package com.brandonkitt.nexttogo.preview

import com.brandonkitt.core.data.network.NetworkChangeReceiver
import com.brandonkitt.nexttogo.mvi.viewmodel.NextToGoViewModel

/**
 * Preview class for NextToGoViewModel, mocks the repository for Preview
 */
class NextToGoViewModelPreview : NextToGoViewModel(
    repository = NextToGoRepositoryPreview(),
    networkChangeReceiver = NetworkChangeReceiver()
)