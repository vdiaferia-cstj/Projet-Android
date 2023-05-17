package com.example.tpsynthese.ui.gateways.list

import com.example.tpsynthese.domain.models.Gateway


sealed class GatewaysListUiState {
    object Loading: GatewaysListUiState()
    class Success(val gateways: List<Gateway>): GatewaysListUiState()
    class Error(val exception: Exception? = null) : GatewaysListUiState()
}