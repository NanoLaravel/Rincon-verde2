package com.example.rincon_verde2.ui.feature.placedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rincon_verde2.data.repository.PlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceDetailViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val placeId: String = checkNotNull(savedStateHandle["placeId"])

    private val _uiState = MutableStateFlow(PlaceDetailUiState())
    val uiState: StateFlow<PlaceDetailUiState> = _uiState.asStateFlow()

    init {
        loadPlace()
    }

    private fun loadPlace() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)

                val place = placeRepository.getPlaceById(placeId)
                val isFav = placeRepository.isFavorite(placeId)

                _uiState.value = _uiState.value.copy(
                    place = place,
                    isFavorite = isFav,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al cargar detalles"
                )
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            try {
                val placeId = _uiState.value.place?.id ?: return@launch

                if (_uiState.value.isFavorite) {
                    placeRepository.removeFavorite(placeId)
                    _uiState.value = _uiState.value.copy(isFavorite = false)
                } else {
                    placeRepository.addFavorite(placeId)
                    _uiState.value = _uiState.value.copy(isFavorite = true)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Error al actualizar favoritos"
                )
            }
        }
    }

    fun addReview(rating: Float, comment: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                // Simular guardado de reseña
                delay(800)

                val reviews = _uiState.value.reviews.toMutableList()
                reviews.add(0, "★ $rating - $comment")

                _uiState.value = _uiState.value.copy(
                    userRating = rating,
                    reviews = reviews,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al guardar reseña"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun retry() {
        loadPlace()
    }
}
