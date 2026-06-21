package com.example.rincon_verde2.domain.model

enum class PlaceCategory {
    ACTIVITY, EAT, STAY, FAVORITES
}

data class Place(
    val id: String,
    val name: String,
    val description: String,
    val category: PlaceCategory,
    val location: String,
    val rating: Float,
    val reviewCount: Int,
    val imageUrl: String,
    val imageUrls: List<String> = emptyList(),
    val phone: String,
    val address: String,
    val hours: String
)

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val image: String
)

data class ContactInfo(
    val phone: String,
    val hours: String
)

data class Amenity(
    val id: String,
    val name: String,
    val icon: androidx.compose.material.icons.Icons? = null
)

data class Review(
    val id: String,
    val author: String,
    val rating: Float,
    val title: String,
    val date: String,
    val content: String
)

data class PlaceDetail(
    val id: String,
    val name: String,
    val imageUrl: String,
    val rating: Float,
    val location: String,
    val description: String,
    val amenities: List<Amenity>,
    val contact: ContactInfo,
    val reviews: List<Review>,
    val isFavorite: Boolean
)
