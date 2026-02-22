package com.example.rincon_verde2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val email: String,
    val displayName: String,
    val profileImageUrl: String = "",
    val favoriteCount: Int = 0,
    val reviewCount: Int = 0,
    val createdAt: String = "",
    val updatedAt: String = "",
    val lastSyncTimestamp: Long = 0
)
