package com.example.rincon_verde2.domain.model

data class Filter(
    val minRating: Float = 0f,
    val maxRating: Float = 5f,
    val minPrice: Int = 0,
    val maxPrice: Int = 1000,
    val minDistance: Float = 0f,
    val maxDistance: Float = 100f,
    val amenities: List<String> = emptyList(),
    val categories: List<String> = emptyList(),
    val sortBy: SortOption = SortOption.RELEVANCE
)

enum class SortOption {
    RELEVANCE,
    RATING,
    PRICE_LOW_TO_HIGH,
    PRICE_HIGH_TO_LOW,
    DISTANCE,
    NEWEST
}
