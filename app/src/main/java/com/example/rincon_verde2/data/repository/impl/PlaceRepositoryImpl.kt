package com.example.rincon_verde2.data.repository.impl

import android.util.Log
import com.example.rincon_verde2.data.local.dao.PlaceDao
import com.example.rincon_verde2.data.local.entity.PlaceEntity
import com.example.rincon_verde2.data.remote.ApiService
import com.example.rincon_verde2.data.remote.dto.PlaceDto
import com.example.rincon_verde2.data.remote.dto.PlacesResponse
import com.example.rincon_verde2.data.repository.PlaceRepository
import com.example.rincon_verde2.domain.model.Place
import com.example.rincon_verde2.domain.model.PlaceCategory
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val placeDao: PlaceDao
) : PlaceRepository {

    override suspend fun getPlaces(): List<Place> {
        return try {
            Log.d("PlaceRepositoryImpl", "Fetching places from: https://api.nortedesantander.com/api/places")
            val response = apiService.getPlaces()
            Log.d("PlaceRepositoryImpl", "Response success. Found ${response.data.size} places")
            val places = response.data.map { it.toDomain() }
            
            placeDao.insertPlaces(response.data.map { it.toEntity() })
            places
        } catch (e: Exception) {
            Log.e("PlaceRepositoryImpl", "Error fetching places: ${e.message}", e)
            placeDao.getAllPlaces().first().map { it.toDomain() }
        }
    }

    override suspend fun getPlaceById(id: String): Place? {
        return try {
            // Intentar obtener de API
            val placeDto = apiService.getPlaceById(id)
            val place = placeDto.toDomain()
            
            // Guardar en local
            placeDao.insertPlace(placeDto.toEntity())
            
            place
        } catch (e: Exception) {
            // Fallback a local
            placeDao.getPlaceById(id)?.toDomain()
        }
    }

    override suspend fun getPlacesByCategory(category: String): List<Place> {
        return try {
            val response = apiService.getPlacesByCategory(category)
            val places = response.data.map { it.toDomain() }

            placeDao.insertPlaces(response.data.map { it.toEntity() })

            places
        } catch (e: Exception) {
            placeDao.getPlacesByCategory(category).first().map { it.toDomain() }
        }
    }

    override suspend fun searchPlaces(
        name: String?,
        category: String?,
        minRating: Float?,
        maxRating: Float?,
        type: String?
    ): List<Place> {
        return try {
            Log.d("PlaceRepositoryImpl", "====== searchPlaces() START ======")
            Log.d("PlaceRepositoryImpl", "Parameters received: name=$name, category=$category")
            
            // Determinar qué endpoint usar según los filtros
            val response: PlacesResponse = when {
                !name.isNullOrEmpty() -> {
                    apiService.searchPlaces(name)
                }
                minRating != null || maxRating != null -> {
                    val min = minRating ?: 0f
                    val max = maxRating ?: 5f
                    apiService.getPlacesByRating(min, max)
                }
                !type.isNullOrEmpty() -> {
                    apiService.getPlacesByType(type)
                }
                !category.isNullOrEmpty() -> {
                    apiService.getPlacesByCategory(category)
                }
                else -> {
                    apiService.getPlaces()
                }
            }

            val places = response.data.map { it.toDomain() }
            placeDao.insertPlaces(response.data.map { it.toEntity() })
            
            Log.d("PlaceRepositoryImpl", "searchPlaces() returned ${places.size} places")
            places
        } catch (e: Exception) {
            Log.e("PlaceRepositoryImpl", "Error searching places", e)
            if (!name.isNullOrEmpty()) {
                placeDao.searchPlaces(name).map { it.toDomain() }
            } else {
                emptyList()
            }
        }
    }

    override suspend fun getFavoritePlaces(): List<Place> {
        return placeDao.getFavoritePlaces().first().map { it.toDomain() }
    }

    override suspend fun addFavorite(placeId: String): Boolean {
        return try {
            placeDao.addFavorite(placeId)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun removeFavorite(placeId: String): Boolean {
        return try {
            placeDao.removeFavorite(placeId)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun isFavorite(placeId: String): Boolean {
        return placeDao.isFavorite(placeId) ?: false
    }

    // Mappers
    private fun PlaceDto.toDomain(): Place {
        val allImageUrls = images.map { "https://api.nortedesantander.com/storage/${it.path}" }
        val imageUrl = allImageUrls.firstOrNull() ?: ""
        Log.d("PlaceRepositoryImpl", "PlaceDto.toDomain: $name, imageUrl=$imageUrl, images=${images.size}")
        
        val latStr = latitude?.jsonPrimitive?.content
        val lonStr = longitude?.jsonPrimitive?.content
        
        return Place(
            id = id.toString(),
            name = name,
            description = description ?: "",
            category = when (type?.lowercase()) {
                "hotel", "stay" -> PlaceCategory.STAY
                "restaurant", "eat" -> PlaceCategory.EAT
                "recreation", "other", "activity" -> PlaceCategory.ACTIVITY
                else -> PlaceCategory.ACTIVITY
            },
            location = address ?: listOfNotNull(latStr, lonStr).joinToString(","),
            rating = rating,
            reviewCount = reviews.size,
            imageUrl = imageUrl,
            imageUrls = allImageUrls,
            phone = phone ?: "",
            address = address ?: "",
            hours = ""
        )
    }

    private fun PlaceDto.toEntity(): PlaceEntity {
        val latStr = latitude?.jsonPrimitive?.content
        val lonStr = longitude?.jsonPrimitive?.content
        val allImageUrls = images.joinToString(",") { "https://api.nortedesantander.com/storage/${it.path}" }
        
        return PlaceEntity(
            id = id.toString(),
            name = name,
            description = description ?: "",
            category = type ?: "",
            location = address ?: listOfNotNull(latStr, lonStr).joinToString(","),
            rating = rating,
            reviewCount = reviews.size,
            imageUrl = images.firstOrNull()?.let { "https://api.nortedesantander.com/storage/${it.path}" } ?: "",
            imageUrls = allImageUrls,
            phone = phone ?: "",
            address = address ?: "",
            hours = ""
        )
    }

    private fun PlaceEntity.toDomain(): Place {
        return Place(
            id = id,
            name = name,
            description = description,
            category = when (category.lowercase()) {
                "hotel", "stay" -> PlaceCategory.STAY
                "restaurant", "eat" -> PlaceCategory.EAT
                "recreation", "other", "activity" -> PlaceCategory.ACTIVITY
                else -> PlaceCategory.ACTIVITY
            },
            location = location,
            rating = rating,
            reviewCount = reviewCount,
            imageUrl = imageUrl,
            imageUrls = imageUrls.split(",").filter { it.isNotBlank() },
            phone = phone,
            address = address,
            hours = hours
        )
    }
}
