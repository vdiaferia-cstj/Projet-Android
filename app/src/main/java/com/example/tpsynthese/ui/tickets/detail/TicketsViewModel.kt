package com.example.tpsynthese.ui.tickets.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class TicketsViewModel : ViewModel() {

    private val _ticketUiState = MutableStateFlow<TicketsUiState>(TicketsUiState.Empty)
    val ticketUiState = _ticketUiState.asStateFlow()

    fun installGateway(rawValue: String) {
        viewModelScope.launch {
            val checkIn = CheckIn(rawValue, Constants.DOOR)
            checkInRepository.create(checkIn).collect{apiResult ->
                _barcodeUiState.update {
                    when(apiResult){
                        is ApiResult.Error -> BarcodeUiState.Error(apiResult.throwable as Exception)
                        ApiResult.Loading -> BarcodeUiState.Empty
                        is ApiResult.Success -> BarcodeUiState.Success(apiResult.data)
                    }
                }
            }
        }
    }
}