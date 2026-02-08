package com.example.rincon_verde2.ui.feature.placelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rincon_verde2.ui.feature.home.Place
import com.example.rincon_verde2.ui.feature.home.PlaceCategory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PlaceListUiState(
  val isLoading: Boolean = false,
  val error: String? = null,
  val places: List<Place> = emptyList()
)

class PlaceListViewModel : ViewModel() {
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
          rating = 4.5f,
          imageUrl = "",
          category = PlaceCategory.EAT,
          location = "Centro"
        ),
        Place(
          id = "2",
          name = "Hotel Paraíso",
          rating = 4.7f,
          imageUrl = "",
          category = PlaceCategory.STAY,
          location = "Zona Turística"
        )
      )
      _uiState.value = PlaceListUiState(isLoading = false, places = sample)
    }
  }
}
