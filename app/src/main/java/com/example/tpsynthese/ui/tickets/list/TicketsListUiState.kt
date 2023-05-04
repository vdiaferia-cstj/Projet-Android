package com.example.tpsynthese.ui.tickets.list

import com.example.tpsynthese.domain.models.Ticket

sealed class TicketsListUiState {
    object Loading: TicketsListUiState()
    class Success(val ticket: List<Ticket>): TicketsListUiState()
    class Error(val exception: Exception? = null) : TicketsListUiState()
}