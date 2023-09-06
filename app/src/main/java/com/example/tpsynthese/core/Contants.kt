package com.example.tpsynthese.core

object Constants {

    object BaseURL {
        private const val BASE_API = "https://api.andromia.science"
        const val TICKETS = "$BASE_API/tickets"
        const val CUSTOMERS = "$BASE_API/customers"
        const val GATEWAYS = "$BASE_API/gateways"
        const val NETWORK = "$BASE_API/network"
    }

    const val FLAG_API_URL = "https://flagcdn.com/h40/%s.png"

    enum class TicketPriority {
        Low, Normal, High, Critical
    }

    enum class TicketStatus {
        Open, Solved
    }

    enum class ConnectionStatus {
        Online, Offline
    }

    object RefreshDelay {
        const val TICKET_DELAY:Long = 30 * 1000L
        const val GATEWAYS_DELAY:Long = 60 * 1000L
        const val LOADING_DELAY: Long = 10000L
        const val NETWORK_DELAY: Long = 120000L
    }



}