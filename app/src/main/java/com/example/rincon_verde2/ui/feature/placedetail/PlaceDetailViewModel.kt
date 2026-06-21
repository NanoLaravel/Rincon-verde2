package com.example.rincon_verde2.ui.feature.placedetail

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Wifi
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

// PlaceDetail data classes are defined in PlaceDetailUiModel.kt

@HiltViewModel
class PlaceDetailViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<PlaceDetailUiState>(PlaceDetailUiState.Loading)
    val uiState: StateFlow<PlaceDetailUiState> = _uiState.asStateFlow()

    private var currentPlaceId: String? = null
    private var isFavorite = false

    fun loadPlaceDetail(placeId: String) {
        if (currentPlaceId == placeId && _uiState.value !is PlaceDetailUiState.Loading) {
            return // Ya está cargado
        }
        
        currentPlaceId = placeId
        
        viewModelScope.launch {
            try {
                _uiState.value = PlaceDetailUiState.Loading

                val place = placeRepository.getPlaceById(placeId)
                isFavorite = placeRepository.isFavorite(placeId)

                if (place != null) {
                    Log.d("PlaceDetailViewModel", "Loaded place: ${place.name}, imageUrl=${place.imageUrl}")
                    
                    val placeDetail = PlaceDetail(
                        id = place.id,
                        name = place.name,
                        imageUrl = place.imageUrl,
                        imageUrls = place.imageUrls,
                        rating = place.rating,
                        location = place.location,
                        description = place.description,
                        amenities = listOf(
                            Amenity("1", "WiFi", Icons.Filled.Wifi),
                            Amenity("2", "Piscina", Icons.Filled.Pool),
                            Amenity("3", "Seguridad", Icons.Filled.CheckCircle)
                        ),
                        contact = ContactInfo(place.phone, hours = place.hours),
                        reviews = listOf(
                            Review("1", "Usuario", place.rating, place.name, "Reciente", "")
                        ),
                        isFavorite = isFavorite,
                        priceCategory = 2
                    )
                    
                    _uiState.value = PlaceDetailUiState.Success(placeDetail)
                } else {
                    _uiState.value = PlaceDetailUiState.Error("Lugar no encontrado")
                }
            } catch (e: Exception) {
                Log.e("PlaceDetailViewModel", "Error loading place", e)
                _uiState.value = PlaceDetailUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            try {
                currentPlaceId?.let { placeId ->
                    if (isFavorite) {
                        placeRepository.removeFavorite(placeId)
                        isFavorite = false
                    } else {
                        placeRepository.addFavorite(placeId)
                        isFavorite = true
                    }
                    
                    // Recargar para actualizar el estado
                    loadPlaceDetail(placeId)
                }
            } catch (e: Exception) {
                Log.e("PlaceDetailViewModel", "Error toggling favorite", e)
            }
        }
    }

    fun addReview(rating: Float, comment: String) {
        viewModelScope.launch {
            try {
                delay(800)
                // TODO: Implement review submission to API
            } catch (e: Exception) {
                Log.e("PlaceDetailViewModel", "Error adding review", e)
            }
        }
    }

    fun clearError() {
        if (_uiState.value is PlaceDetailUiState.Error) {
            _uiState.value = PlaceDetailUiState.Loading
        }
    }

    fun retry() {
        currentPlaceId?.let { loadPlaceDetail(it) }
    }
}
