package com.example.rincon_verde2.ui.feature.auth

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isPasswordVisible: Boolean = false,
    val isValidEmail: Boolean = false,
    val isValidPassword: Boolean = false,
    val loginSuccess: Boolean = false
)
