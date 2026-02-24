package com.example.rincon_verde2.data.repository

import com.example.rincon_verde2.domain.model.Event

interface EventRepository {
    suspend fun getEvents(): List<Event>
    suspend fun getEventById(id: String): Event?
    suspend fun getFeaturedEvents(): List<Event>
    suspend fun getUpcomingEvents(): List<Event>
    suspend fun getOngoingEvents(): List<Event>
    suspend fun getEventsByPlace(placeId: String): List<Event>
}
