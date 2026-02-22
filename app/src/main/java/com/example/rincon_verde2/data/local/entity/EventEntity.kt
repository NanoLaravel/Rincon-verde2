package com.example.rincon_verde2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val image: String,
    val timestamp: Long = System.currentTimeMillis()
)
