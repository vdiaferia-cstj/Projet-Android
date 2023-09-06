package com.example.tpsynthese.ui.loading

sealed class LoadingUiState {
    object Success: LoadingUiState()
    class Loading(val secondes: Int, val progression: Int) : LoadingUiState()
    object Empty : LoadingUiState()
}