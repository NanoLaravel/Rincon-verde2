package com.example.rincon_verde2.ui.feature.search

import com.example.rincon_verde2.domain.model.Place
import com.example.rincon_verde2.domain.model.Event
import com.example.rincon_verde2.domain.model.PlaceCategory
import com.example.rincon_verde2.domain.model.Filter
import com.example.rincon_verde2.domain.model.SortOption

data class SearchUiState(
    val places: List<Place> = emptyList(),
    val events: List<Event> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: PlaceCategory? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val recentSearches: List<String> = emptyList(),
    val filters: Filter = Filter(),
    val hasActiveFilters: Boolean = false
)
