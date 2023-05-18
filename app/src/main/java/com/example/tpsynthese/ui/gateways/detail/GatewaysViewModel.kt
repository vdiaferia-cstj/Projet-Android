package com.example.tpsynthese.ui.gateways.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tpsynthese.core.ApiResult
import com.example.tpsynthese.data.repositories.CustomerRepository
import com.example.tpsynthese.data.repositories.GatewayRepository
import com.example.tpsynthese.data.repositories.TicketRepository
import com.example.tpsynthese.ui.tickets.detail.TicketsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GatewaysViewModel(private val href : String) : ViewModel() {
    private val gatewayRepository = GatewayRepository()
    private val _gatewayUiState = MutableStateFlow<GatewaysUiState>(GatewaysUiState.Empty)
    val gatewayUiState = _gatewayUiState.asStateFlow()
    private lateinit var hrefGateway: String


    init {
        getInfoGateway()
    }

    class Factory(private val href:String):  ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(String::class.java).newInstance(href)
        }
    }
    //val text: LiveData<String> = _text

    //TODO JER UTILISE CETTE FONCTION LA POUR GET TON GATEWAY
    private fun getInfoGateway(){
        viewModelScope.launch {
            gatewayRepository.retrieveOne(href).collect() { apiResult ->
                _gatewayUiState.update {
                    when (apiResult) {
                        is ApiResult.Success -> {
                            GatewaysUiState.Success(apiResult.data)
                        }

                        ApiResult.Loading -> GatewaysUiState.Empty

                        is ApiResult.Error -> GatewaysUiState.Error(apiResult.exception)
                    }
                }
            }
        }
    }

    fun reboot(href:String){
        viewModelScope.launch {
            gatewayRepository.reboot(href).collect{}
            getInfoGateway()
        }
    }
    fun update(href: String){
        viewModelScope.launch {
            gatewayRepository.update(href).collect{}
            getInfoGateway()
        }
    }
}
