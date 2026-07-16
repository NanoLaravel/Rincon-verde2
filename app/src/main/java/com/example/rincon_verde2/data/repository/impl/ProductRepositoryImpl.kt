package com.example.rincon_verde2.data.repository.impl

import android.util.Log
import com.example.rincon_verde2.data.remote.ApiService
import com.example.rincon_verde2.data.remote.dto.ProductDto
import com.example.rincon_verde2.data.repository.ProductRepository
import com.example.rincon_verde2.domain.model.Product
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {

    private val BASE_URL = "https://api.nortedesantander.com"

    override suspend fun getFeaturedProducts(): List<Product> {
        return try {
            Log.d("ProductRepository", "Fetching featured products from API...")
            val response = apiService.getFeaturedProducts()
            Log.d("ProductRepository", "Fetched ${response.size} products")
            response.forEach { 
                Log.d("ProductRepository", "Product: ${it.name}, imagePath: ${it.imagePath}")
            }
            response.map { it.toDomain() }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error fetching featured products: ${e.message}", e)
            emptyList()
        }
    }

    override suspend fun getAllProducts(): List<Product> {
        return try {
            val allProductDtos = mutableListOf<ProductDto>()
            var currentPage = 1
            var lastPage: Int
            
            Log.d("ProductRepository", "Starting full sync of products...")
            
            do {
                val response = apiService.getProducts(page = currentPage, perPage = 50)
                allProductDtos.addAll(response.data)
                lastPage = response.lastPage ?: 1
                currentPage++
            } while (currentPage <= lastPage)
            
            Log.d("ProductRepository", "Fetched ${allProductDtos.size} total products")
            allProductDtos.map { it.toDomain() }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error fetching all products: ${e.message}", e)
            emptyList()
        }
    }

    override suspend fun getProductById(id: String): Product? {
        return try {
            apiService.getProductById(id).toDomain()
        } catch (e: Exception) {
            null
        }
    }

    private fun ProductDto.toDomain(): Product {
        // Buscar todas las imágenes disponibles
        val allImageUrls = mutableListOf<String>()
        
        // Agregar imagen principal si existe
        val mainPath = when {
            !imagePath.isNullOrEmpty() -> imagePath
            !image.isNullOrEmpty() -> image
            !path.isNullOrEmpty() -> path
            else -> null
        }
        mainPath?.let { allImageUrls.add("$BASE_URL/storage/$it") }
        
        // Agregar lista de imágenes
        images.forEach { img ->
            val fullUrl = "$BASE_URL/storage/${img.path}"
            if (!allImageUrls.contains(fullUrl)) {
                allImageUrls.add(fullUrl)
            }
        }

        val finalImageUrl = allImageUrls.firstOrNull() ?: ""
        Log.d("ProductRepository", "Mapped Product: $name, ImageURL: $finalImageUrl, Total Images: ${allImageUrls.size}")

        return Product(
            id = id.toString(),
            name = name,
            description = description ?: "",
            price = price,
            imageUrl = finalImageUrl,
            imageUrls = allImageUrls,
            category = category ?: "",
            placeId = placeId?.toString(),
            placeName = place?.name
        )
    }
}
