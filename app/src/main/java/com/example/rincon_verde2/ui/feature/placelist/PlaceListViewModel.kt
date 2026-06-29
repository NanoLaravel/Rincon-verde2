package com.example.rincon_verde2.ui.feature.placelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rincon_verde2.data.repository.PlaceRepository
import com.example.rincon_verde2.domain.model.Place
import com.example.rincon_verde2.domain.model.PlaceCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PlaceListUiState(
  val isLoading: Boolean = false,
  val error: String? = null,
  val places: List<Place> = emptyList()
)

@HiltViewModel
class PlaceListViewModel @Inject constructor(
  private val placeRepository: PlaceRepository
) : ViewModel() {
  private val _uiState = MutableStateFlow(PlaceListUiState(isLoading = true))
  val uiState: StateFlow<PlaceListUiState> = _uiState.asStateFlow()

  fun loadPlaces() {
    viewModelScope.launch {
      try {
        _uiState.value = PlaceListUiState(isLoading = true)
        
        // Load from repository (API with Room fallback)
        val places = placeRepository.getPlaces()
        
        _uiState.value = PlaceListUiState(
          isLoading = false,
          places = places
        )
      } catch (e: Exception) {
        _uiState.value = PlaceListUiState(
          isLoading = false,
          error = e.message ?: "Error al cargar lugares"
        )
      }
    }
  }

  fun loadPlacesByCategory(category: PlaceCategory) {
    viewModelScope.launch {
      try {
        _uiState.value = PlaceListUiState(isLoading = true)
        
        // Mapear categoría de la UI a término de búsqueda de la API
        val apiCategory = when(category) {
            PlaceCategory.EAT -> "restaurant"
            PlaceCategory.STAY -> "hotel"
            PlaceCategory.ACTIVITY -> "recreation"
            else -> category.name.lowercase()
        }
        
        // Cargar directamente desde el repositorio usando el filtro de la API
        val places = placeRepository.getPlacesByCategory(apiCategory)
        
        _uiState.value = PlaceListUiState(
          isLoading = false,
          places = places
        )
      } catch (e: Exception) {
        _uiState.value = PlaceListUiState(
          isLoading = false,
          error = e.message ?: "Error al cargar lugares"
        )
      }
    }
  }
}
