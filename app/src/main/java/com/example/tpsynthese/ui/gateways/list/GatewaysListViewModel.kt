package com.example.tpsynthese.ui.gateways.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tpsynthese.core.ApiResult
import com.example.tpsynthese.data.repositories.GatewayRepository
import com.example.tpsynthese.ui.gateways.detail.GatewaysUiState
import com.example.tpsynthese.ui.tickets.list.TicketsListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GatewaysListViewModel: ViewModel() {
    private val gatewayRepository = GatewayRepository()

    private val _mainUiState = MutableStateFlow<GatewaysListUiState>(GatewaysListUiState.Loading)
    val mainUiState = _mainUiState.asStateFlow()


    init {
        gatewaysRefresh()
    }

    fun gatewaysRefresh() {
        viewModelScope.launch {
            gatewayRepository.retrieveAll().collect {
                _mainUiState.update { _ ->
                    when (it) {
                        is ApiResult.Error -> GatewaysListUiState.Error(it.exception)
                        ApiResult.Loading -> GatewaysListUiState.Loading
                        is ApiResult.Success -> GatewaysListUiState.Success(it.data)
                    }
                }
            }
        }
    }

}