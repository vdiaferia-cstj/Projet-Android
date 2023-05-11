package com.example.tpsynthese.ui.network

import com.example.tpsynthese.domain.models.NetworkNode

sealed class NetworkUiState {
    object Empty: NetworkUiState()
    object Loading: NetworkUiState()
    class Error(val exception: Exception): NetworkUiState()
    class Success(val networkNode: List<NetworkNode>) : NetworkUiState()
}