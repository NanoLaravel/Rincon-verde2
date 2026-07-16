package com.example.rincon_verde2.data.repository

import com.example.rincon_verde2.domain.model.Product

interface ProductRepository {
    suspend fun getFeaturedProducts(): List<Product>
    suspend fun getAllProducts(): List<Product>
    suspend fun getProductById(id: String): Product?
}
