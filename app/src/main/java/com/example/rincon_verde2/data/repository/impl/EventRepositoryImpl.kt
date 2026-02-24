package com.example.rincon_verde2.data.repository.impl

import android.util.Log
import com.example.rincon_verde2.data.local.dao.EventDao
import com.example.rincon_verde2.data.local.entity.EventEntity
import com.example.rincon_verde2.data.remote.ApiService
import com.example.rincon_verde2.data.remote.dto.EventDto
import com.example.rincon_verde2.data.repository.EventRepository
import com.example.rincon_verde2.domain.model.Event
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao
) : EventRepository {

    companion object {
        private const val TAG = "EventRepository"
        private const val BASE_URL = "http://192.168.1.61"
    }

    override suspend fun getEvents(): List<Event> {
        return try {
            Log.d(TAG, "Fetching all events from API")
            val response = apiService.getEvents()
            Log.d(TAG, "Events response: success=${response.success}, count=${response.data.size}")
            
            val events = response.data.map { it.toDomain() }
            
            // Guardar en BD local
            eventDao.insertEvents(response.data.map { it.toEntity() })
            
            events
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching events: ${e.message}", e)
            // Fallback a datos locales
            eventDao.getAllEvents().first().map { it.toDomain() }
        }
    }

    override suspend fun getEventById(id: String): Event? {
        return try {
            Log.d(TAG, "Fetching event by id: $id")
            val eventDto = apiService.getEventById(id)
            val event = eventDto.toDomain()
            
            // Guardar en local
            eventDao.insertEvent(eventDto.toEntity())
            
            event
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching event by id: ${e.message}", e)
            // Fallback a local
            eventDao.getEventById(id)?.toDomain()
        }
    }

    override suspend fun getFeaturedEvents(): List<Event> {
        return try {
            Log.d(TAG, "Fetching featured events from API")
            val response = apiService.getFeaturedEvents()
            Log.d(TAG, "Featured events response: success=${response.success}, count=${response.data.size}")
            
            val events = response.data.map { it.toDomain() }
            
            // Guardar en BD local
            eventDao.insertEvents(response.data.map { it.toEntity() })
            
            events
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching featured events: ${e.message}", e)
            // Fallback a datos locales
            eventDao.getAllEvents().first().map { it.toDomain() }
        }
    }

    override suspend fun getUpcomingEvents(): List<Event> {
        return try {
            Log.d(TAG, "Fetching upcoming events from API")
            val response = apiService.getUpcomingEvents()
            Log.d(TAG, "Upcoming events response: success=${response.success}, count=${response.data.size}")
            
            val events = response.data.map { it.toDomain() }
            
            // Guardar en BD local
            eventDao.insertEvents(response.data.map { it.toEntity() })
            
            events
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching upcoming events: ${e.message}", e)
            // Para eventos futuros, usar fecha de hoy
            val today = java.time.LocalDate.now().toString()
            eventDao.getUpcomingEvents(today).first().map { it.toDomain() }
        }
    }

    override suspend fun getOngoingEvents(): List<Event> {
        return try {
            Log.d(TAG, "Fetching ongoing events from API")
            val response = apiService.getOngoingEvents()
            Log.d(TAG, "Ongoing events response: success=${response.success}, count=${response.data.size}")
            
            val events = response.data.map { it.toDomain() }
            
            // Guardar en BD local
            eventDao.insertEvents(response.data.map { it.toEntity() })
            
            events
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching ongoing events: ${e.message}", e)
            // Fallback a datos locales
            eventDao.getAllEvents().first().map { it.toDomain() }
        }
    }

    override suspend fun getEventsByPlace(placeId: String): List<Event> {
        return try {
            Log.d(TAG, "Fetching events by place: $placeId")
            val response = apiService.getEventsByPlace(placeId)
            Log.d(TAG, "Events by place response: success=${response.success}, count=${response.data.size}")
            
            val events = response.data.map { it.toDomain() }
            
            // Guardar en BD local
            eventDao.insertEvents(response.data.map { it.toEntity() })
            
            events
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching events by place: ${e.message}", e)
            // Fallback a datos locales
            eventDao.getAllEvents().first().map { it.toDomain() }
        }
    }

    // Mappers
    private fun EventDto.toDomain(): Event {
        val fullImageUrl = if (!imagePath.isNullOrEmpty()) {
            "$BASE_URL/storage/$imagePath"
        } else {
            ""
        }
        Log.d(TAG, "EventDto.toDomain: $title, image=$fullImageUrl")
        return Event(
            id = id.toString(),
            title = title,
            description = description ?: "",
            date = startDate?.substringBefore("T") ?: "",
            location = location ?: place?.name ?: "",
            image = fullImageUrl
        )
    }

    private fun EventDto.toEntity(): EventEntity {
        val fullImageUrl = if (!imagePath.isNullOrEmpty()) {
            "$BASE_URL/storage/$imagePath"
        } else {
            ""
        }
        return EventEntity(
            id = id.toString(),
            title = title,
            description = description ?: "",
            date = startDate?.substringBefore("T") ?: "",
            location = location ?: place?.name ?: "",
            image = fullImageUrl
        )
    }

    private fun EventEntity.toDomain(): Event {
        return Event(
            id = id,
            title = title,
            description = description,
            date = date,
            location = location,
            image = image
        )
    }
}
