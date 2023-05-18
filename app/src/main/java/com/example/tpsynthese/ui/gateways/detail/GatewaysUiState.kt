package com.example.tpsynthese.ui.gateways.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tpsynthese.domain.models.Gateway
import com.example.tpsynthese.ui.tickets.detail.TicketsUiState
import java.lang.Exception

sealed class GatewaysUiState {
    class Error(val exception: Exception) : GatewaysUiState()
    class Success(val gateway: Gateway): GatewaysUiState()
    class Loading: GatewaysUiState()
    object Empty: GatewaysUiState()
}

