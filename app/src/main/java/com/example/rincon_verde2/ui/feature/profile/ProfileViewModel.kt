package com.example.rincon_verde2.ui.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rincon_verde2.data.repository.UserRepository
import com.example.rincon_verde2.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun loadProfile(userId: String) {
        if (_uiState.value.user != null) {
            return
        }

        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)

                val result = userRepository.getCurrentUser()

                if (result.isSuccess) {
                    val user = result.getOrNull()
                    if (user != null) {
                        _uiState.value = _uiState.value.copy(
                            user = user,
                            isLoading = false,
                            favoriteCount = user.favoriteCount,
                            reviewCount = user.reviewCount
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(isLoading = false, error = "Usuario no encontrado")
                    }
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = result.exceptionOrNull()?.message ?: "Error al cargar perfil")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al cargar perfil"
                )
            }
        }
    }

    fun updateProfile(displayName: String, email: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isSaving = true, error = null)

                val currentUser = _uiState.value.user ?: return@launch
                val updatedUser = currentUser.copy(
                    displayName = displayName,
                    email = email
                )

                val result = userRepository.updateProfile(updatedUser)

                if (result.isSuccess) {
                    _uiState.value = _uiState.value.copy(
                        user = result.getOrThrow(),
                        isSaving = false,
                        updateSuccess = true
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isSaving = false,
                        error = result.exceptionOrNull()?.message ?: "Error al actualizar perfil"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    error = e.message ?: "Error al actualizar perfil"
                )
            }
        }
    }

    /**
     * Cierra la sesión del usuario.
     * Limpia el token y los datos locales.
     */
    fun logout() {
        viewModelScope.launch {
            try {
                userRepository.logout()
            } catch (_: Exception) {
                // Ignorar errores de logout
            }
            _uiState.value = ProfileUiState()
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
