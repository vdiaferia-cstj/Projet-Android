package com.example.tpsynthese.ui.tickets.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tpsynthese.core.ApiResult
import com.example.tpsynthese.data.repositories.CustomerRepository
import com.example.tpsynthese.data.repositories.TicketRepository
import com.example.tpsynthese.domain.models.Gateway
import com.example.tpsynthese.domain.models.Ticket
import com.github.kittinunf.fuel.json.jsonDeserializer
import io.github.g00fy2.quickie.QRResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject


class TicketsViewModel (private val href : String) : ViewModel() {
    private val customerRepository = CustomerRepository()
    private val ticketRepository = TicketRepository()
    private val _ticketUiState = MutableStateFlow<TicketsUiState>(TicketsUiState.Empty)
    val ticketUiState = _ticketUiState.asStateFlow()
    private lateinit var hrefCustomer: String




    init {
        viewModelScope.launch {
            ticketRepository.retrieveOne(href).collect() { apiResult ->
                _ticketUiState.update {
                    when (apiResult) {
                       is ApiResult.Success -> {
                            getCustomer(apiResult.data.customer.href)
                           hrefCustomer = apiResult.data.customer.href
                           TicketsUiState.Success(apiResult.data)
                        }

                        ApiResult.Loading -> TicketsUiState.Empty

                        is ApiResult.Error -> TicketsUiState.Error(apiResult.exception)
                    }
                }
            }
        }
    }

    fun getCustomer(href: String){
        viewModelScope.launch {
            customerRepository.retrieveOne(href).collect() { apiResult ->
                _ticketUiState.update {
                    when (apiResult) {
                        //  is ApiResult.Success -> TicketsUiState.Success(apiResult.data)
                        is ApiResult.Error -> TicketsUiState.CustomerError(apiResult.exception as Exception)
                        is ApiResult.Success -> TicketsUiState.CustomerSuccess(apiResult.data)
                        ApiResult.Loading -> TicketsUiState.Empty
                    }
                }
            }

        }
    }

    fun installGateway(jsonGateway: Gateway) {
        viewModelScope.launch {
            customerRepository.install(hrefCustomer, jsonGateway).collect() { apiResult ->
                _ticketUiState.update {
                    when (apiResult) {
                        is ApiResult.Error -> TicketsUiState.GatewayError(apiResult.exception)
                        ApiResult.Loading -> TicketsUiState.Empty
                        is ApiResult.Success -> TicketsUiState.GatewaySuccess(apiResult.data)
                    }
                }
            }
            }
        }

    fun changeState(href:String,action:String) {
        ticketRepository.changeState(href,action)
     }
   


    class Factory(private val href:String):  ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(String::class.java).newInstance(href)
        }

    }
}