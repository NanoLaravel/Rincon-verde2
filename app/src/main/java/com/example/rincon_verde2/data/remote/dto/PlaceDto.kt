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
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String? = null,
    @SerialName("start_date")
    val startDate: String? = null,
    @SerialName("end_date")
    val endDate: String? = null,
    @SerialName("location")
    val location: String? = null,
    @SerialName("image_path")
    val imagePath: String? = null,
    @SerialName("is_featured")
    val isFeatured: Boolean = false,
    @SerialName("is_active")
    val isActive: Boolean = true,
    @SerialName("price")
    val price: String? = null,
    @SerialName("latitude")
    val latitude: String? = null,
    @SerialName("longitude")
    val longitude: String? = null,
    @SerialName("place_id")
    val placeId: Int? = null,
    @SerialName("place")
    val place: EventPlaceDto? = null
)

@Serializable
data class EventPlaceDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("address") val address: String? = null
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
