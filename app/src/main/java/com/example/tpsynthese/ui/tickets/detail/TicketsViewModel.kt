package com.example.tpsynthese.ui.tickets.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TicketsViewModel : ViewModel() {

    private val _ticketUiState = MutableStateFlow<TicketsUiState>(TicketsUiState.Empty)
    val ticketUiState = _ticketUiState.asStateFlow()

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}