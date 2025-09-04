package com.jorgeromo.androidClassMp1.firstpartial.login.model

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false
)