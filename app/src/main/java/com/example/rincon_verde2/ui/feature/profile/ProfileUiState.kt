package com.example.rincon_verde2.ui.feature.profile

import com.example.rincon_verde2.domain.model.User

data class ProfileUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSaving: Boolean = false,
    val updateSuccess: Boolean = false,
    val favoriteCount: Int = 0,
    val reviewCount: Int = 0
)
