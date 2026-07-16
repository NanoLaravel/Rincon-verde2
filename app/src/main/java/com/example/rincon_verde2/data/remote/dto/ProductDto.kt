package com.example.rincon_verde2.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String? = null,
    @SerialName("price") val price: Double = 0.0,
    @SerialName("image_path") val imagePath: String? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("path") val path: String? = null,
    @SerialName("images") val images: List<ProductImageDto> = emptyList(),
    @SerialName("category") val category: String? = null,
    @SerialName("place_id") val placeId: Int? = null,
    @SerialName("place") val place: ProductPlaceDto? = null
)

@Serializable
data class ProductImageDto(
    @SerialName("id") val id: Int,
    @SerialName("path") val path: String
)

@Serializable
data class ProductPlaceDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)

@Serializable
data class ProductsResponse(
    @SerialName("data") val data: List<ProductDto>,
    @SerialName("current_page") val currentPage: Int? = null,
    @SerialName("last_page") val lastPage: Int? = null,
    @SerialName("total") val total: Int? = null,
    @SerialName("success") val success: Boolean? = null
)
