package com.example.rincon_verde2.data.remote

import com.example.rincon_verde2.data.remote.dto.EventDto
import com.example.rincon_verde2.data.remote.dto.EventsResponse
import com.example.rincon_verde2.data.remote.dto.LoginRequest
import com.example.rincon_verde2.data.remote.dto.LoginResponse
import com.example.rincon_verde2.data.remote.dto.PlaceDto
import com.example.rincon_verde2.data.remote.dto.PlacesResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // ============= PLACES =============

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

    // ============= EVENTS =============

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

    // ============= AUTHENTICATION =============

    /**
     * Login user with email and password
     */
    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    /**
     * Register new user
     */
    @POST("/api/auth/register")
    suspend fun register(@Body request: LoginRequest): LoginResponse

    /**
     * Logout user
     */
    @POST("/api/auth/logout")
    suspend fun logout(): LoginResponse
}

