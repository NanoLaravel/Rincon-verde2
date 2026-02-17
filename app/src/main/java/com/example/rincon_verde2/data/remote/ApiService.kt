package com.example.rincon_verde2.data.remote

import com.example.rincon_verde2.data.remote.dto.PlaceDto
import com.example.rincon_verde2.data.remote.dto.PlacesResponse
import com.example.rincon_verde2.data.remote.dto.EventDto
import com.example.rincon_verde2.data.remote.dto.EventsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface ApiService {
    
    /**
     * Obtener lista de places
     * Fácil de cambiar: solo actualiza la URL base y endpoint
     */
    @GET("/api/places")
    suspend fun getPlaces(): PlacesResponse
    
    /**
     * Obtener place por ID
     */
    @GET("/api/places/{id}")
    suspend fun getPlaceById(@Path("id") placeId: String): PlaceDto
    
    /**
     * Buscar places por query
     */
    @GET("/api/places/search")
    suspend fun searchPlaces(@Query("q") query: String): PlacesResponse
    
    /**
     * Obtener places por categoría
     */
    @GET("/api/places/category/{category}")
    suspend fun getPlacesByCategory(@Path("category") category: String): PlacesResponse
    
    /**
     * Obtener eventos
     */
    @GET("/api/events")
    suspend fun getEvents(): EventsResponse
    
    /**
     * Obtener evento por ID
     */
    @GET("/api/events/{id}")
    suspend fun getEventById(@Path("id") eventId: String): EventDto
}
