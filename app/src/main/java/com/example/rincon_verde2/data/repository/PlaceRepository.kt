package com.example.rincon_verde2.data.repository

import com.example.rincon_verde2.domain.model.Place

interface PlaceRepository {
    suspend fun getPlaces(): List<Place>
    suspend fun getPlaceById(id: String): Place?
    suspend fun getPlacesByCategory(category: String): List<Place>
    suspend fun searchPlaces(
        name: String? = null,
        category: String? = null,
        minRating: Float? = null,
        maxRating: Float? = null,
        type: String? = null
    ): List<Place>
    suspend fun getFavoritePlaces(): List<Place>
    suspend fun addFavorite(placeId: String): Boolean
    suspend fun removeFavorite(placeId: String): Boolean
    suspend fun isFavorite(placeId: String): Boolean
}
