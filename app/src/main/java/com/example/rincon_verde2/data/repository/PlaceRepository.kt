package com.example.rincon_verde2.data.repository

import com.example.rincon_verde2.domain.model.Place
import com.example.rincon_verde2.domain.model.Event

interface PlaceRepository {
    suspend fun getPlaces(): List<Place>
    suspend fun getPlaceById(id: String): Place?
    suspend fun getPlacesByCategory(category: String): List<Place>
    suspend fun searchPlaces(query: String): List<Place>
    suspend fun getFavoritePlaces(): List<Place>
    suspend fun addFavorite(placeId: String): Boolean
    suspend fun removeFavorite(placeId: String): Boolean
    suspend fun isFavorite(placeId: String): Boolean
}

interface EventRepository {
    suspend fun getEvents(): List<Event>
    suspend fun getEventById(id: String): Event?
    suspend fun getUpcomingEvents(): List<Event>
}
