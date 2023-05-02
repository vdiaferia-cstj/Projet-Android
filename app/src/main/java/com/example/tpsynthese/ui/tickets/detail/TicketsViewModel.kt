package com.example.tpsynthese.ui.tickets.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tpsynthese.core.ApiResult
import com.example.tpsynthese.data.repositories.CustomerRepository
import com.example.tpsynthese.domain.models.Gateway
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

class TicketsViewModel(private val href : String) : ViewModel() {
    private val customerRepository = CustomerRepository()
    private val _ticketUiState = MutableStateFlow<TicketsUiState>(TicketsUiState.Empty)
    val ticketUiState = _ticketUiState.asStateFlow()

    fun installGateway(rawValue: String) {
        viewModelScope.launch {
            //TODO: Créer un gateway a partir de rawValue
            //  val checkIn = CheckIn(rawValue, Constants.DOOR)

            val gateway = Gateway() //TODO
            customerRepository.install(href, gateway).collect{apiResult ->
                _ticketUiState.update {
                    when(apiResult){
                        is ApiResult.Error -> TicketsUiState.Error(apiResult.exception)
                        ApiResult.Loading -> TicketsUiState.Empty
                        is ApiResult.Success -> TicketsUiState.Success(apiResult.data)
                    }
                }
            }
        }
    }


    class Factory(private val href:String):  ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(String::class.java).newInstance(href)
        }

    }
}