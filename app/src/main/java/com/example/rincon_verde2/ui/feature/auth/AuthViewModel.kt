package com.example.rincon_verde2.ui.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rincon_verde2.domain.model.AuthState
import com.example.rincon_verde2.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: com.example.rincon_verde2.data.repository.UserRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun login(email: String, password: String) {
        if (!isValidEmail(email) || password.length < 3) {
            _authState.value = AuthState.Error(
                "Email válido requerido. Contraseña mínimo 3 caracteres."
            )
            return
        }

        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading

                val result = userRepository.login(email, password)

                if (result.isSuccess) {
                    _authState.value = AuthState.Authenticated(result.getOrThrow())
                } else {
                    _authState.value = AuthState.Error(result.exceptionOrNull()?.message ?: "Login failed")
                }

            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Error al iniciar sesión")
            }
        }
    }

    fun socialLogin(token: String, provider: String) {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading
                val result = userRepository.socialLogin(token, provider)

                if (result.isSuccess) {
                    _authState.value = AuthState.Authenticated(result.getOrThrow())
                } else {
                    _authState.value = AuthState.Error(result.exceptionOrNull()?.message ?: "Social login failed")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Error en autenticación social")
            }
        }
    }
    
    fun signUp(email: String, password: String, displayName: String, confirmPassword: String) {
        when {
            !isValidEmail(email) -> {
                _authState.value = AuthState.Error("Email inválido")
                return
            }
            password.length < 6 -> {
                _authState.value = AuthState.Error("La contraseña debe tener al menos 6 caracteres")
                return
            }
            password != confirmPassword -> {
                _authState.value = AuthState.Error("Las contraseñas no coinciden")
                return
            }
            displayName.isBlank() -> {
                _authState.value = AuthState.Error("Ingresa un nombre")
                return
            }
        }

        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading

                val result = userRepository.register(email, password, displayName)

                if (result.isSuccess) {
                    _authState.value = AuthState.Authenticated(result.getOrThrow())
                } else {
                    _authState.value = AuthState.Error(result.exceptionOrNull()?.message ?: "Registration failed")
                }

            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Error al registrarse")
            }
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            try {
                userRepository.logout()
            } catch (_: Exception) {
                // ignore
            }
            _authState.value = AuthState.Unauthenticated
        }
    }
    
    fun clearError() {
        if (_authState.value is AuthState.Error) {
            _authState.value = AuthState.Unauthenticated
        }
    }
    
    private fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() && 
               email.contains("@") && 
               email.contains(".") &&
               email.length > 5
    }
}
