package com.example.rincon_verde2.domain.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val imageUrls: List<String> = emptyList(),
    val category: String,
    val placeId: String? = null,
    val placeName: String? = null
)
