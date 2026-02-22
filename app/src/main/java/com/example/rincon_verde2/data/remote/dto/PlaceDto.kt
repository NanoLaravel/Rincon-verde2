package com.example.rincon_verde2.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaceDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String? = null,
    @SerialName("address")
    val address: String? = null,
    @SerialName("latitude")
    val latitude: String? = null,
    @SerialName("longitude")
    val longitude: String? = null,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("website")
    val website: String? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("rating")
    val rating: Float = 0f,
    @SerialName("facilities")
    val facilities: String? = null,
    @SerialName("categories")
    val categories: List<CategoryDto> = emptyList(),
    @SerialName("images")
    val images: List<ImageDto> = emptyList(),
    @SerialName("reviews")
    val reviews: List<ReviewDto> = emptyList()
)

@Serializable
data class CategoryDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String? = null
)

@Serializable
data class ImageDto(
    @SerialName("id") val id: Int,
    @SerialName("place_id") val placeId: Int,
    @SerialName("path") val path: String,
    @SerialName("description") val description: String? = null
)

@Serializable
data class ReviewDto(
    @SerialName("id") val id: Int,
    @SerialName("place_id") val placeId: Int,
    @SerialName("user_id") val userId: Int,
    @SerialName("rating") val rating: Float,
    @SerialName("comment") val comment: String? = null,
    @SerialName("created_at") val createdAt: String? = null
)
@Serializable
data class EventDto(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("date")
    val date: String,
    @SerialName("location")
    val location: String,
    @SerialName("image")
    val image: String
)

@Serializable
data class PlacesResponse(
    @SerialName("data")
    val data: List<PlaceDto>,
    @SerialName("current_page")
    val currentPage: Int? = null,
    @SerialName("last_page")
    val lastPage: Int? = null,
    @SerialName("total")
    val total: Int? = null,
    @SerialName("per_page")
    val perPage: Int? = null,
    @SerialName("success")
    val success: Boolean? = null,
    @SerialName("message")
    val message: String? = null
)

@Serializable
data class EventsResponse(
    @SerialName("data")
    val data: List<EventDto>,
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String? = null
)
