package com.jorgeromo.androidClassMp1.secondpartial.home.ui

import com.jorgeromo.androidClassMp1.secondpartial.home.model.Event

data class HomeUiState(
    val eventos: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
