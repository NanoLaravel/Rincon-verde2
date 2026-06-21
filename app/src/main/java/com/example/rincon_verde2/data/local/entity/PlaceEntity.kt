package com.example.rincon_verde2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val location: String,
    val rating: Float,
    val reviewCount: Int,
    val imageUrl: String,
    val imageUrls: String = "", // URLs separadas por comas
    val phone: String,
    val address: String,
    val hours: String,
    val isFavorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
