package com.example.tpsynthese.ui.gateways.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tpsynthese.data.repositories.GatewayRepository
import kotlinx.coroutines.launch

class GatewaysViewModel(private val href: String) : ViewModel() {

    private val gatewayRepository = GatewayRepository()

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    //TODO JER UTILISE CETTE FONCTION LA POUR GET TON GATEWAY
    fun getInfoGateway(){
        viewModelScope.launch {
            //gatewayRepository.retrieveOne(href)?.collect{apiResult->


            //}
        }
    }

    fun reboot(href:String){
        viewModelScope.launch {
            viewModelScope.launch {
                //gatewayRepository.reboot(href).collect{}
                //displayGateway()
            }
        }
    }
}