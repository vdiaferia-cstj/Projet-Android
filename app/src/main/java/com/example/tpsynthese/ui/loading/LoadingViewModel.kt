package com.example.tpsynthese.ui.loading

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoadingViewModel : ViewModel() {
    private val _loadingUiState = MutableStateFlow<LoadingUiState>(LoadingUiState.Empty)
    val loadingUiState = _loadingUiState.asStateFlow()

    private var _timerCounter = 0;
    private var _progress = 0;

    private val timer = object  :  CountDownTimer(10000, 1000){
        override fun onTick(millisUntilFinished: Long) {
            _timerCounter++
            _progress += 10
            _loadingUiState.update {
                LoadingUiState.Loading(_timerCounter, _progress)
            }
        }

        override fun onFinish() {
            cancel()

            _loadingUiState.update {
                LoadingUiState.Success
            }
        }
    }
    init {
        timer.start()
    }

    fun startTimer() {


    }
}
