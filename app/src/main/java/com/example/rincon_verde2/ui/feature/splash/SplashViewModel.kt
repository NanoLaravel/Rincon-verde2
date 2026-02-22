package com.example.rincon_verde2.ui.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rincon_verde2.data.local.TokenManager
import com.example.rincon_verde2.data.repository.UserRepository
import com.example.rincon_verde2.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Estado de la verificación de sesión al iniciar la app.
 */
sealed class SessionState {
    data object Loading : SessionState()
    data class Authenticated(val user: User) : SessionState()
    data object Unauthenticated : SessionState()
}

/**
 * ViewModel para la pantalla de Splash.
 * Verifica si existe una sesión guardada (token + usuario local).
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _sessionState = MutableStateFlow<SessionState>(SessionState.Loading)
    val sessionState: StateFlow<SessionState> = _sessionState.asStateFlow()

    /**
     * Verifica si hay una sesión guardada.
     * Si hay token y usuario local, la sesión es válida.
     */
    fun checkSession() {
        viewModelScope.launch {
            try {
                // Verificar si hay token guardado
                val token = tokenManager.getToken()
                
                if (token.isNullOrBlank()) {
                    // No hay token, usuario no autenticado
                    _sessionState.value = SessionState.Unauthenticated
                    return@launch
                }

                // Hay token, verificar si hay usuario local
                val userResult = userRepository.getCurrentUser()
                
                if (userResult.isSuccess) {
                    val user = userResult.getOrNull()
                    if (user != null) {
                        // Sesión válida: token + usuario
                        _sessionState.value = SessionState.Authenticated(user)
                    } else {
                        // Token pero sin usuario local, limpiar token
                        tokenManager.clearToken()
                        _sessionState.value = SessionState.Unauthenticated
                    }
                } else {
                    // Error al obtener usuario, limpiar sesión
                    tokenManager.clearToken()
                    _sessionState.value = SessionState.Unauthenticated
                }
            } catch (e: Exception) {
                // Error inesperado, asumir no autenticado
                _sessionState.value = SessionState.Unauthenticated
            }
        }
    }
}
