package com.example.rincon_verde2.ui.feature.search

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
class SearchViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _recentSearches = MutableStateFlow<List<String>>(listOf())
    val recentSearches: StateFlow<List<String>> = _recentSearches.asStateFlow()

    fun search(query: String) {
        if (query.isEmpty()) {
            _uiState.value = SearchUiState()
            return
        }

        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    isLoading = true,
                    error = null,
                    searchQuery = query
                )

                // Buscar lugares y eventos en paralelo
                val placesDeferred = async { placeRepository.searchPlaces(query) }
                val eventsDeferred = async { eventRepository.getEvents() }

                val places = placesDeferred.await()
                val events = eventsDeferred.await()

                // Filtrar eventos que contengan la query en título o descripción
                val filteredEvents = events.filter {
                    it.title.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
                }

                _uiState.value = _uiState.value.copy(
                    places = places,
                    events = filteredEvents,
                    isLoading = false
                )

                // Agregar a búsquedas recientes si no está ya
                val recent = _recentSearches.value.toMutableList()
                if (!recent.contains(query)) {
                    recent.add(0, query)
                    if (recent.size > 10) recent.removeAt(recent.lastIndex)
                    _recentSearches.value = recent
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error en búsqueda"
                )
            }
        }
    }

    fun filterByCategory(category: PlaceCategory?) {
        val filtered = if (category == null) {
            _uiState.value.places
        } else {
            _uiState.value.places.filter { it.category == category }
        }

        _uiState.value = _uiState.value.copy(
            places = filtered,
            selectedCategory = category
        )
    }

    fun clearSearch() {
        _uiState.value = SearchUiState()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun removeRecentSearch(query: String) {
        val recent = _recentSearches.value.toMutableList()
        recent.remove(query)
        _recentSearches.value = recent
    }

    fun clearRecentSearches() {
        _recentSearches.value = emptyList()
    }
}
