package com.example.rincon_verde2.ui.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rincon_verde2.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun loadProfile(userId: String) {
        if (_uiState.value.user != null) {
            return
        }

        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)

                // Simular carga desde base de datos
                delay(800)

                val mockUser = User(
                    id = userId,
                    email = "usuario@example.com",
                    displayName = "Juan Perez",
                    favoriteCount = 12,
                    reviewCount = 8
                )

                _uiState.value = _uiState.value.copy(
                    user = mockUser,
                    isLoading = false,
                    favoriteCount = mockUser.favoriteCount,
                    reviewCount = mockUser.reviewCount
                )
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

                // Simular guardado
                delay(1200)

                val currentUser = _uiState.value.user ?: return@launch
                val updatedUser = currentUser.copy(
                    displayName = displayName,
                    email = email
                )

                _uiState.value = _uiState.value.copy(
                    user = updatedUser,
                    isSaving = false,
                    updateSuccess = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    error = e.message ?: "Error al actualizar perfil"
                )
            }
        }
    }

    fun logout() {
        _uiState.value = ProfileUiState()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun clearUpdateSuccess() {
        _uiState.value = _uiState.value.copy(updateSuccess = false)
    }
}
