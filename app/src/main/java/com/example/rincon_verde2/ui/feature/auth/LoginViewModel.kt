package com.example.rincon_verde2.ui.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        val isValid = isValidEmail(email)
        _uiState.value = _uiState.value.copy(
            email = email,
            isValidEmail = isValid
        )
    }

    fun updatePassword(password: String) {
        val isValid = password.length >= 3
        _uiState.value = _uiState.value.copy(
            password = password,
            isValidPassword = isValid
        )
    }

    fun togglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(
            isPasswordVisible = !_uiState.value.isPasswordVisible
        )
    }

    fun validateLogin(): Boolean {
        val email = _uiState.value.email
        val password = _uiState.value.password

        if (!isValidEmail(email) || password.length < 3) {
            _uiState.value = _uiState.value.copy(
                error = "Email o contraseña inválidos"
            )
            return false
        }
        return true
    }

    fun validateRegister(password: String, confirmPassword: String): Boolean {
        if (password != confirmPassword) {
            _uiState.value = _uiState.value.copy(
                error = "Las contraseñas no coinciden"
            )
            return false
        }

        val email = _uiState.value.email
        if (!isValidEmail(email) || password.length < 3) {
            _uiState.value = _uiState.value.copy(
                error = "Email o contraseña inválidos"
            )
            return false
        }
        return true
    }

    fun setLoading(loading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = loading)
    }

    fun setLoginSuccess(success: Boolean) {
        _uiState.value = _uiState.value.copy(loginSuccess = success)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun clearLoginSuccess() {
        _uiState.value = _uiState.value.copy(loginSuccess = false)
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".") && email.length > 5
    }
}

