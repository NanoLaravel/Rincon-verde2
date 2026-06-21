package com.example.rincon_verde2.ui.feature.placedetail

import androidx.compose.ui.graphics.vector.ImageVector

data class Amenity(
  val id: String,
  val name: String,
  val icon: ImageVector,
  val enabled: Boolean = true
)

data class ContactInfo(
  val phone: String,
  val email: String? = null,
  val website: String? = null,
  val hours: String
)

data class Review(
  val id: String,
  val author: String,
  val rating: Float,
  val text: String,
  val date: String,
  val avatar: String
)

data class PlaceDetail(
  val id: String,
  val name: String,
  val rating: Float,
  val priceCategory: Int = 1, // 1 = $, 2 = $$, 3 = $$$
  val imageUrl: String,
  val imageUrls: List<String> = emptyList(),
  val location: String,
  val description: String,
  val amenities: List<Amenity>,
  val contact: ContactInfo,
  val reviews: List<Review>,
  val isFavorite: Boolean = false
)
