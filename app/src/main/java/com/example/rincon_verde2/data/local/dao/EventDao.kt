package com.example.rincon_verde2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rincon_verde2.data.local.entity.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)
    
    @Query("SELECT * FROM events ORDER BY date DESC")
    fun getAllEvents(): Flow<List<EventEntity>>
    
    @Query("SELECT * FROM events WHERE id = :eventId")
    suspend fun getEventById(eventId: String): EventEntity?
    
    @Query("SELECT * FROM events WHERE date >= :dateString ORDER BY date ASC")
    fun getUpcomingEvents(dateString: String): Flow<List<EventEntity>>
    
    @Query("DELETE FROM events")
    suspend fun deleteAllEvents()
}
