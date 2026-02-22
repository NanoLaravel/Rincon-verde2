package com.example.rincon_verde2.data.repository.impl

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

    override suspend fun getEvents(): List<Event> {
        return try {
            // Intentar obtener de API
            val response = apiService.getEvents()
            val events = response.data.map { it.toDomain() }
            
            // Guardar en BD local
            eventDao.insertEvents(response.data.map { it.toEntity() })
            
            events
        } catch (e: Exception) {
            // Fallback a datos locales
            eventDao.getAllEvents().first().map { it.toDomain() }
        }
    }

    override suspend fun getEventById(id: String): Event? {
        return try {
            // Intentar obtener de API
            val eventDto = apiService.getEventById(id)
            val event = eventDto.toDomain()
            
            // Guardar en local
            eventDao.insertEvent(eventDto.toEntity())
            
            event
        } catch (e: Exception) {
            // Fallback a local
            eventDao.getEventById(id)?.toDomain()
        }
    }

    override suspend fun getUpcomingEvents(): List<Event> {
        return try {
            val response = apiService.getEvents()
            val events = response.data.map { it.toDomain() }
            
            eventDao.insertEvents(response.data.map { it.toEntity() })
            
            events
        } catch (e: Exception) {
            // Para eventos futuros, usar fecha de hoy
            val today = java.time.LocalDate.now().toString()
            eventDao.getUpcomingEvents(today).first().map { it.toDomain() }
        }
    }

    // Mappers
    private fun EventDto.toDomain(): Event {
        return Event(
            id = id,
            title = title,
            description = description,
            date = date,
            location = location,
            image = image
        )
    }

    private fun EventDto.toEntity(): EventEntity {
        return EventEntity(
            id = id,
            title = title,
            description = description,
            date = date,
            location = location,
            image = image
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
