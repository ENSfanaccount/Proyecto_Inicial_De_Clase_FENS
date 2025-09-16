package com.jorgeromo.androidClassMp1.firstpartial.animation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class UiState(val isLoading: Boolean = true)

class AnimationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun stopLoading() {
        _uiState.value = UiState(isLoading = false)
    }
}