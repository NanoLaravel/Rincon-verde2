package com.example.rincon_verde2.ui.feature.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rincon_verde2.data.repository.EventRepository
import com.example.rincon_verde2.data.repository.PlaceRepository
import com.example.rincon_verde2.domain.model.Filter
import com.example.rincon_verde2.domain.model.PlaceCategory
import com.example.rincon_verde2.domain.model.SortOption
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
        Log.d("SearchViewModel", "search() called with query: '$query'")
        performSearch(query)
    }

    private fun performSearch(query: String = _uiState.value.searchQuery) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    isLoading = true,
                    error = null,
                    searchQuery = query
                )

                val filters = _uiState.value.filters

                Log.d("SearchViewModel", "====== performSearch() STARTED ======")
                Log.d("SearchViewModel", "Query: '$query'")
                Log.d("SearchViewModel", "Full filters object: $filters")
                Log.d("SearchViewModel", "filters.minRating: ${filters.minRating} (type: ${filters.minRating.javaClass.simpleName})")
                Log.d("SearchViewModel", "filters.maxRating: ${filters.maxRating} (type: ${filters.maxRating.javaClass.simpleName})")
                Log.d("SearchViewModel", "filters.minRating != 0f? ${filters.minRating != 0f}")
                Log.d("SearchViewModel", "filters.maxRating != 5f? ${filters.maxRating != 5f}")

                // Buscar lugares - usar el endpoint apropiado según los filtros
                val placesDeferred = async {
                    // IMPORTANTE: debe enviar minRating/maxRating SIEMPRE que sean diferentes del default
                    // Default: minRating=0f, maxRating=5f
                    val minRating = if (filters.minRating != 0f) filters.minRating else null
                    val maxRating = if (filters.maxRating != 5f) filters.maxRating else null
                    
                    Log.d("SearchViewModel", "FINAL VALUES TO SEND:")
                    Log.d("SearchViewModel", "  minRating=$minRating")
                    Log.d("SearchViewModel", "  maxRating=$maxRating")
                    Log.d("SearchViewModel", "  name=${query.takeIf { it.isNotEmpty() }}")
                    Log.d("SearchViewModel", "  category=${_uiState.value.selectedCategory?.toString()?.lowercase()}")
                    Log.d("SearchViewModel", "====== performSearch() END LOG ======")
                    
                    // Mapear categoría de la UI a término de la API
                    val apiCategory = when(_uiState.value.selectedCategory) {
                        PlaceCategory.EAT -> "restaurant"
                        PlaceCategory.STAY -> "hotel"
                        PlaceCategory.ACTIVITY -> "recreation"
                        else -> _uiState.value.selectedCategory?.name?.lowercase()
                    }
                    
                    placeRepository.searchPlaces(
                        name = query.takeIf { it.isNotEmpty() },
                        category = apiCategory,
                        minRating = minRating,
                        maxRating = maxRating,
                        type = null
                    )
                }
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
                if (query.isNotEmpty()) {
                    val recent = _recentSearches.value.toMutableList()
                    if (!recent.contains(query)) {
                        recent.add(0, query)
                        if (recent.size > 10) recent.removeAt(recent.lastIndex)
                        _recentSearches.value = recent
                    }
                }

                Log.d("SearchViewModel", "Search completed: ${places.size} places found")

            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error during search", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error en búsqueda"
                )
            }
        }
    }


    fun filterByCategory(category: PlaceCategory?) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
        performSearch()
    }

    fun applyFilters(newFilters: Filter) {
        Log.d("SearchViewModel", "🔥🔥🔥 applyFilters() CALLED 🔥🔥🔥 with: $newFilters")
        val hasActiveFilters = newFilters != Filter()
        _uiState.value = _uiState.value.copy(
            filters = newFilters,
            hasActiveFilters = hasActiveFilters
        )
        Log.d("SearchViewModel", "Filter state updated. Current state filters: ${_uiState.value.filters}")
        Log.d("SearchViewModel", "Calling performSearch() from applyFilters")
        performSearch()
    }

    fun updateRatingFilter(min: Float, max: Float) {
        val newFilters = _uiState.value.filters.copy(minRating = min, maxRating = max)
        applyFilters(newFilters)
    }

    fun updatePriceFilter(min: Int, max: Int) {
        val newFilters = _uiState.value.filters.copy(minPrice = min, maxPrice = max)
        applyFilters(newFilters)
    }

    fun updateDistanceFilter(min: Float, max: Float) {
        val newFilters = _uiState.value.filters.copy(minDistance = min, maxDistance = max)
        applyFilters(newFilters)
    }

    fun updateSortOption(sortOption: SortOption) {
        val newFilters = _uiState.value.filters.copy(sortBy = sortOption)
        applyFilters(newFilters)
    }

    fun clearFilters() {
        _uiState.value = _uiState.value.copy(
            filters = Filter(),
            selectedCategory = null,
            hasActiveFilters = false,
            places = emptyList(),
            events = emptyList(),
            searchQuery = ""
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
