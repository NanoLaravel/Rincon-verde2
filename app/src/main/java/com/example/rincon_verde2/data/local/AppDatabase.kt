package com.example.rincon_verde2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rincon_verde2.data.local.dao.EventDao
import com.example.rincon_verde2.data.local.dao.PlaceDao
import com.example.rincon_verde2.data.local.dao.UserDao
import com.example.rincon_verde2.data.local.entity.EventEntity
import com.example.rincon_verde2.data.local.entity.FavoriteEntity
import com.example.rincon_verde2.data.local.entity.PlaceEntity
import com.example.rincon_verde2.data.local.entity.UserEntity

@Database(
    entities = [
        PlaceEntity::class,
        EventEntity::class,
        FavoriteEntity::class,
        UserEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
    abstract fun eventDao(): EventDao
    abstract fun userDao(): UserDao
}
