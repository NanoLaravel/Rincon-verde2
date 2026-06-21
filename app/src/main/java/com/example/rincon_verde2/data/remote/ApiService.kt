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
    @GET("places")
    suspend fun getPlaces(): PlacesResponse

    /**
     * Obtener place por ID
     */
    @GET("places/{id}")
    suspend fun getPlaceById(@Path("id") placeId: String): PlaceDto

    /**
     * Buscar places por nombre
     */
    @GET("places/search/{name}")
    suspend fun searchPlaces(@Path("name") name: String): PlacesResponse

    /**
     * Obtener places con rating entre min y max
     */
    @GET("places/rating/{min}/{max}")
    suspend fun getPlacesByRating(@Path("min") min: Float, @Path("max") max: Float): PlacesResponse

    /**
     * Obtener places por categoría
     */
    @GET("places/category/{category}")
    suspend fun getPlacesByCategory(@Path("category") category: String): PlacesResponse

    /**
     * Obtener places por tipo
     */
    @GET("places/type/{type}")
    suspend fun getPlacesByType(@Path("type") type: String): PlacesResponse

    /**
     * Obtener los mejores lugares (por reviews/rating)
     */
    @GET("places/best-reviews")
    suspend fun getBestReviewedPlaces(): PlacesResponse

    /**
     * Obtener reviews de un lugar específico
     */
    @GET("places/{placeId}/reviews")
    suspend fun getPlaceReviews(@Path("placeId") placeId: String): Any

    // ============= EVENTS =============

    /**
     * Obtener todos los eventos
     */
    @GET("events")
    suspend fun getEvents(): EventsResponse

    /**
     * Obtener eventos destacados
     */
    @GET("events/featured")
    suspend fun getFeaturedEvents(): EventsResponse

    /**
     * Obtener eventos próximos
     */
    @GET("events/upcoming")
    suspend fun getUpcomingEvents(): EventsResponse

    /**
     * Obtener eventos en curso
     */
    @GET("events/ongoing")
    suspend fun getOngoingEvents(): EventsResponse

    /**
     * Obtener eventos por lugar
     */
    @GET("events/place/{placeId}")
    suspend fun getEventsByPlace(@Path("placeId") placeId: String): EventsResponse

    /**
     * Obtener evento por ID
     */
    @GET("events/{id}")
    suspend fun getEventById(@Path("id") eventId: String): EventDto

    // ============= AUTHENTICATION =============

    /**
     * Login user with email and password
     */
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    /**
     * Register new user
     */
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): LoginResponse

    /**
     * Logout user
     */
    @POST("logout")
    suspend fun logout(): LoginResponse

    // ============= FAVORITES =============

    /**
     * Obtener lugares favoritos del usuario
     */
    @GET("favorites")
    suspend fun getFavoritePlaces(): List<PlaceDto>

    /**
     * Agregar lugar a favoritos
     */
    @POST("places/{placeId}/favorite")
    suspend fun addFavorite(@Path("placeId") placeId: String): Any

    /**
     * Eliminar lugar de favoritos
     */
    @DELETE("places/{placeId}/favorite")
    suspend fun removeFavorite(@Path("placeId") placeId: String): Any
}

