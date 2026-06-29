package com.example.rincon_verde2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rincon_verde2.data.local.entity.PlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaces(places: List<PlaceEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(place: PlaceEntity)
    
    @Query("SELECT * FROM places")
    fun getAllPlaces(): Flow<List<PlaceEntity>>
    
    @Query("SELECT * FROM places WHERE id = :placeId")
    suspend fun getPlaceById(placeId: String): PlaceEntity?
    
    @Query("SELECT * FROM places WHERE category = :category")
    fun getPlacesByCategory(category: String): Flow<List<PlaceEntity>>
    
    @Query("SELECT * FROM places WHERE name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    suspend fun searchPlaces(query: String): List<PlaceEntity>
    
    @Query("SELECT * FROM places WHERE isFavorite = 1")
    fun getFavoritePlaces(): Flow<List<PlaceEntity>>
    
    @Query("UPDATE places SET isFavorite = 1 WHERE id = :placeId")
    suspend fun addFavorite(placeId: String)
    
    @Query("UPDATE places SET isFavorite = 0 WHERE id = :placeId")
    suspend fun removeFavorite(placeId: String)
    
    @Query("SELECT isFavorite FROM places WHERE id = :placeId")
    suspend fun isFavorite(placeId: String): Boolean?
    
    @Query("DELETE FROM places WHERE id NOT IN (:ids)")
    suspend fun deletePlacesNotInList(ids: List<String>)
    
    @Query("DELETE FROM places WHERE category = :category AND id NOT IN (:ids)")
    suspend fun deletePlacesByCategoryNotInList(category: String, ids: List<String>)
    
    @Query("DELETE FROM places")
    suspend fun deleteAllPlaces()
    
    @Query("SELECT * FROM places WHERE timestamp > :timestamp ORDER BY timestamp DESC")
    suspend fun getPlacesSince(timestamp: Long): List<PlaceEntity>
}
