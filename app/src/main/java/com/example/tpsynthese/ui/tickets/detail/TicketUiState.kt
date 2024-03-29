package com.example.tpsynthese.ui.tickets.detail

import com.example.tpsynthese.data.repositories.TicketRepository
import com.example.tpsynthese.domain.models.Customer
import com.example.tpsynthese.domain.models.Gateway
import com.example.tpsynthese.domain.models.Ticket
import java.lang.Exception

sealed class TicketsUiState {
    class Error(val exception: Exception) : TicketsUiState()
    class Success(val ticket: Ticket):TicketsUiState()
    class Solved(val ticket: Ticket):TicketsUiState()
    class CustomerSuccess(val customer: Customer): TicketsUiState()
    class CustomerError(val exception: Exception) : TicketsUiState()
    class GatewaySuccess(val gateways:  List<Gateway>) : TicketsUiState()
    class GatewayError(val exception: Exception) : TicketsUiState()
    object Loading:TicketsUiState()
    object Empty: TicketsUiState()
    class GatewayInstallSuccess(val gateway: Gateway) : TicketsUiState()
    class GatewayInstallError(val exception: Exception) : TicketsUiState()

}