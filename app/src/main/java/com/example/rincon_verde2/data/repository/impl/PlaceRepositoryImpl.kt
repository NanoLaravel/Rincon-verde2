package com.example.rincon_verde2.data.repository.impl

import android.util.Log
import com.example.rincon_verde2.data.local.dao.PlaceDao
import com.example.rincon_verde2.data.local.entity.PlaceEntity
import com.example.rincon_verde2.data.remote.ApiService
import com.example.rincon_verde2.data.remote.dto.PlaceDto
import com.example.rincon_verde2.data.repository.PlaceRepository
import com.example.rincon_verde2.domain.model.Place
import com.example.rincon_verde2.domain.model.PlaceCategory
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val placeDao: PlaceDao
) : PlaceRepository {

    override suspend fun getPlaces(): List<Place> {
        return try {
            // Intentar obtener de API
            val response = apiService.getPlaces()
            val places = response.data.map { it.toDomain() }
            
            // Guardar en BD local
            placeDao.insertPlaces(response.data.map { it.toEntity() })
            
            places
        } catch (e: Exception) {
            // Fallback a datos locales
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
            val places = response.map { it.toDomain() }

            placeDao.insertPlaces(response.map { it.toEntity() })

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
            Log.d("PlaceRepositoryImpl", "Parameters received:")
            Log.d("PlaceRepositoryImpl", "  name=$name (isNullOrEmpty=${name.isNullOrEmpty()})")
            Log.d("PlaceRepositoryImpl", "  category=$category (isNullOrEmpty=${category.isNullOrEmpty()})")
            Log.d("PlaceRepositoryImpl", "  minRating=$minRating (!=null: ${minRating != null})")
            Log.d("PlaceRepositoryImpl", "  maxRating=$maxRating (!=null: ${maxRating != null})")
            Log.d("PlaceRepositoryImpl", "  type=$type")
            
            // Determinar qué endpoint usar según los filtros - PRIORIDAD es importante
            val placeDtos: List<PlaceDto> = when {
                // Si hay nombre, buscar por nombre
                !name.isNullOrEmpty() -> {
                    Log.d("PlaceRepositoryImpl", "CONDICION 1: Usando searchPlaces(name)")
                    apiService.searchPlaces(name)
                }
                // Si hay rating (al menos minRating o maxRating), usar ese filtro
                minRating != null || maxRating != null -> {
                    val min = minRating ?: 0f
                    val max = maxRating ?: 5f
                    Log.d("PlaceRepositoryImpl", "CONDICION 2: Usando getPlacesByRating($min, $max)")
                    apiService.getPlacesByRating(min, max)
                }
                // Si hay tipo, usar el filtro de tipo
                !type.isNullOrEmpty() -> {
                    Log.d("PlaceRepositoryImpl", "CONDICION 3: Usando getPlacesByType($type)")
                    apiService.getPlacesByType(type)
                }
                // Si hay categoría, usar el filtro de categoría
                !category.isNullOrEmpty() -> {
                    Log.d("PlaceRepositoryImpl", "CONDICION 4: Usando getPlacesByCategory($category)")
                    apiService.getPlacesByCategory(category)
                }
                // Si no hay filtros específicos, obtener todos
                else -> {
                    Log.d("PlaceRepositoryImpl", "CONDICION 5: Ningún filtro - Usando getPlaces()")
                    val allResponse = apiService.getPlaces()
                    allResponse.data
                }
            }

            val places = placeDtos.map { it.toDomain() }
            placeDao.insertPlaces(placeDtos.map { it.toEntity() })
            
            Log.d("PlaceRepositoryImpl", "searchPlaces() returned ${places.size} places")
            Log.d("PlaceRepositoryImpl", "====== searchPlaces() END ======")
            places
        } catch (e: Exception) {
            Log.e("PlaceRepositoryImpl", "Error searching places", e)
            // Fallback a búsqueda local si hay nombre
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
        val imageUrl = images.firstOrNull()?.let { "http://192.168.1.61/storage/${it.path}" } ?: ""
        Log.d("PlaceRepositoryImpl", "PlaceDto.toDomain: $name, imageUrl=$imageUrl, images=${images.size}")
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
            location = address ?: listOfNotNull(latitude, longitude).joinToString(","),
            rating = rating,
            reviewCount = reviews.size,
            imageUrl = imageUrl,
            phone = phone ?: "",
            address = address ?: "",
            hours = ""
        )
    }

    private fun PlaceDto.toEntity(): PlaceEntity {
        return PlaceEntity(
            id = id.toString(),
            name = name,
            description = description ?: "",
            category = type ?: "",
            location = address ?: listOfNotNull(latitude, longitude).joinToString(","),
            rating = rating,
            reviewCount = reviews.size,
            imageUrl = images.firstOrNull()?.let { "http://192.168.1.61/storage/${it.path}" } ?: "",
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
            phone = phone,
            address = address,
            hours = hours
        )
    }
}
