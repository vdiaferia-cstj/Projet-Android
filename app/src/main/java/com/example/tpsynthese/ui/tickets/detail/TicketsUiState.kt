package com.example.tpsynthese.ui.tickets.detail

import com.example.tpsynthese.domain.models.Ticket
import java.lang.Exception

sealed class TicketsUiState {
    class Error(val exception: Exception) : TicketsUiState()
    class Success(val ticket:Ticket):TicketsUiState()
    class Solved(val ticket: Ticket):TicketsUiState()
}