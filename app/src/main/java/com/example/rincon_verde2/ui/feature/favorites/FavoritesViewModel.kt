package com.example.rincon_verde2.ui.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rincon_verde2.data.repository.PlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val placeRepository: PlaceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    fun loadFavorites() {
        if (_uiState.value.favorites.isNotEmpty()) {
            return
        }

        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)

                val favorites = placeRepository.getFavoritePlaces()

                _uiState.value = _uiState.value.copy(
                    favorites = favorites,
                    isLoading = false,
                    isEmpty = favorites.isEmpty()
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al cargar favoritos"
                )
            }
        }
    }

    fun removeFavorite(placeId: String) {
        viewModelScope.launch {
            try {
                placeRepository.removeFavorite(placeId)

                val updated = _uiState.value.favorites.filter { it.id != placeId }
                _uiState.value = _uiState.value.copy(
                    favorites = updated,
                    isEmpty = updated.isEmpty()
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Error al remover favorito"
                )
            }
        }
    }

    fun sortBy(option: SortOption) {
        val sorted = when (option) {
            SortOption.RATING -> _uiState.value.favorites.sortedByDescending { it.rating }
            SortOption.NAME -> _uiState.value.favorites.sortedBy { it.name }
            SortOption.RECENT -> _uiState.value.favorites.reversed()
        }

        _uiState.value = _uiState.value.copy(
            favorites = sorted,
            sortBy = option
        )
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun retry() {
        loadFavorites()
    }
}
