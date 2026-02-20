package com.example.rincon_verde2.data.remote

import com.example.rincon_verde2.data.remote.dto.EventDto
import com.example.rincon_verde2.data.remote.dto.EventsResponse
import com.example.rincon_verde2.data.remote.dto.LoginRequest
import com.example.rincon_verde2.data.remote.dto.LoginResponse
import com.example.rincon_verde2.data.remote.dto.PlaceDto
import com.example.rincon_verde2.data.remote.dto.PlacesResponse
import com.example.rincon_verde2.data.remote.dto.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    // ============= PLACES =============

    /**
     * Obtener lista de places
     */
    @GET("/api/places")
    suspend fun getPlaces(): PlacesResponse

    /**
     * Obtener place por ID
     */
    @GET("/api/places/{id}")
    suspend fun getPlaceById(@Path("id") placeId: String): PlaceDto

    /**
     * Buscar places por nombre
     */
    @GET("/api/places/search/{name}")
    suspend fun searchPlaces(@Path("name") name: String): List<PlaceDto>

    /**
     * Obtener places con rating entre min y max
     */
    @GET("/api/places/rating/{min}/{max}")
    suspend fun getPlacesByRating(@Path("min") min: Float, @Path("max") max: Float): List<PlaceDto>

    /**
     * Obtener places por categoría
     */
    @GET("/api/places/category/{category}")
    suspend fun getPlacesByCategory(@Path("category") category: String): List<PlaceDto>

    /**
     * Obtener places por tipo
     */
    @GET("/api/places/type/{type}")
    suspend fun getPlacesByType(@Path("type") type: String): List<PlaceDto>

    /**
     * Obtener los mejores lugares (por reviews/rating)
     */
    @GET("/api/places/best-reviews")
    suspend fun getBestReviewedPlaces(): List<PlaceDto>

    /**
     * Obtener reviews de un lugar específico
     */
    @GET("/api/places/{placeId}/reviews")
    suspend fun getPlaceReviews(@Path("placeId") placeId: String): Any

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
    @POST("/api/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    /**
     * Register new user
     */
    @POST("/api/register")
    suspend fun register(@Body request: RegisterRequest): LoginResponse

    /**
     * Logout user
     */
    @POST("/api/logout")
    suspend fun logout(): LoginResponse

    // ============= FAVORITES =============

    /**
     * Obtener lugares favoritos del usuario
     */
    @GET("/api/favorites")
    suspend fun getFavoritePlaces(): List<PlaceDto>

    /**
     * Agregar lugar a favoritos
     */
    @POST("/api/places/{placeId}/favorite")
    suspend fun addFavorite(@Path("placeId") placeId: String): Any

    /**
     * Eliminar lugar de favoritos
     */
    @DELETE("/api/places/{placeId}/favorite")
    suspend fun removeFavorite(@Path("placeId") placeId: String): Any
}

