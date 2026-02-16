package com.example.rincon_verde2.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rincon_verde2.data.repository.EventRepository
import com.example.rincon_verde2.data.repository.PlaceRepository
import com.example.rincon_verde2.domain.model.PlaceCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _favorites = MutableStateFlow<Set<String>>(setOf())
    val favorites: StateFlow<Set<String>> = _favorites.asStateFlow()

    fun loadPlaces() {
        if (_uiState.value.places.isNotEmpty()) {
            // Ya están cargados
            return
        }

        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)

                // Cargar lugares y eventos en paralelo
                val placesDeferred = async { placeRepository.getPlaces() }
                val eventsDeferred = async { eventRepository.getEvents() }
                val favoritesDeferred = async { placeRepository.getFavoritePlaces() }

                val places = placesDeferred.await()
                val events = eventsDeferred.await()
                val favorites = favoritesDeferred.await()

                _uiState.value = _uiState.value.copy(
                    places = places,
                    events = events,
                    favorites = favorites,
                    isLoading = false
                )

                _favorites.value = favorites.map { place -> place.id }.toSet()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error desconocido"
                )
            }
        }
    }

    fun loadEvents() {
        viewModelScope.launch {
            try {
                val events = eventRepository.getEvents()
                _uiState.value = _uiState.value.copy(events = events)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Error cargando eventos"
                )
            }
        }
    }

    fun toggleFavorite(placeId: String) {
        viewModelScope.launch {
            try {
                val currentFavorites = _favorites.value.toMutableSet()
                if (currentFavorites.contains(placeId)) {
                    placeRepository.removeFavorite(placeId)
                    currentFavorites.remove(placeId)
                } else {
                    placeRepository.addFavorite(placeId)
                    currentFavorites.add(placeId)
                }
                _favorites.value = currentFavorites

                // Actualizar favoritos en el estado
                val updatedFavorites = _uiState.value.places.filter { it.id in currentFavorites }
                _uiState.value = _uiState.value.copy(favorites = updatedFavorites)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Error al actualizar favoritos"
                )
            }
        }
    }

    fun filterByCategory(category: PlaceCategory) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    selectedCategory = category,
                    isLoading = true
                )

                val filtered = placeRepository.getPlacesByCategory(category.name)
                _uiState.value = _uiState.value.copy(
                    places = if (category == PlaceCategory.ACTIVITY) {
                        _uiState.value.places.filter { it.category == category }
                    } else {
                        filtered
                    },
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error filtrando"
                )
            }
        }
    }

    fun searchPlaces(query: String) {
        if (query.isBlank()) {
            _uiState.value = _uiState.value.copy(selectedCategory = null)
            return
        }

        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val results = placeRepository.searchPlaces(query)
                _uiState.value = _uiState.value.copy(
                    places = results,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error en búsqueda"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun retry() {
        loadPlaces()
    }
}
