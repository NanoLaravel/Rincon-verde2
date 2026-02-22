package com.example.rincon_verde2.ui.feature.favorites

import com.example.rincon_verde2.domain.model.Place

data class FavoritesUiState(
    val favorites: List<Place> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val sortBy: SortOption = SortOption.RATING,
    val isEmpty: Boolean = true
)

enum class SortOption {
    RATING, NAME, RECENT
}
