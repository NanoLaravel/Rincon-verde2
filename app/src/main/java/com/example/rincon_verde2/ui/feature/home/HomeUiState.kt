package com.example.rincon_verde2.ui.feature.home

import com.example.rincon_verde2.domain.model.Place
import com.example.rincon_verde2.domain.model.Event
import com.example.rincon_verde2.domain.model.PlaceCategory

data class HomeUiState(
    val places: List<Place> = emptyList(),
    val events: List<Event> = emptyList(),
    val favorites: List<Place> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedCategory: PlaceCategory? = null
)
