package com.example.tpsynthese.ui.network

import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tpsynthese.core.ApiResult
import com.example.tpsynthese.data.repositories.NetworkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

class NetworkViewModel : ViewModel() {
    private val networkNodeRepository = NetworkRepository()
    private val _networkUiState = MutableStateFlow<NetworkUiState>(NetworkUiState.Empty)

    val networkUiState = _networkUiState.asStateFlow()

    init{
        viewModelScope.launch {
            networkNodeRepository.retrieveAll().collect{apiResult ->
                _networkUiState.update{
                    when(apiResult){
                        is ApiResult.Error -> NetworkUiState.Error(apiResult.exception as Exception)
                        ApiResult.Loading -> NetworkUiState.Loading
                        is ApiResult.Success -> NetworkUiState.Success(apiResult.data)
                    }
                }
            }
        }
    }


}