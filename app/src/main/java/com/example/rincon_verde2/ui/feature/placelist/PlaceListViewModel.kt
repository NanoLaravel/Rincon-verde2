package com.example.rincon_verde2.ui.feature.placelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rincon_verde2.data.repository.PlaceRepository
import com.example.rincon_verde2.domain.model.Place
import com.example.rincon_verde2.domain.model.PlaceCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    // For now load fake / mock data to make UI preview and dev easier
    viewModelScope.launch {
      _uiState.value = PlaceListUiState(isLoading = true)
      delay(300) // simulate loading
      val sample = listOf(
        Place(
          id = "1",
          name = "Cafetería Central",
          description = "Cafetería especializada en cafés de origen y pasteles",
          rating = 4.5f,
          imageUrl = "",
          category = PlaceCategory.EAT,
          location = "Centro",
          reviewCount = 156,
          phone = "+57 (1) 1111-2222",
          address = "Carrera 8 #10-25",
          hours = "7:00 AM - 8:00 PM"
        ),
        Place(
          id = "2",
          name = "Hotel Paraíso",
          description = "Hotel 4 estrellas con piscina y restaurante",
          rating = 4.7f,
          imageUrl = "",
          category = PlaceCategory.STAY,
          location = "Zona Turística",
          reviewCount = 203,
          phone = "+57 (1) 3333-4444",
          address = "Avenida Turística #50",
          hours = "24 Horas"
        )
      )
      _uiState.value = PlaceListUiState(isLoading = false, places = sample)
    }
  }
}
