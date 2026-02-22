package com.example.rincon_verde2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val placeId: String,
    val timestamp: Long = System.currentTimeMillis()
)
