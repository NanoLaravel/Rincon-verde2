package com.example.rincon_verde2.ui.feature.placedetail

sealed class PlaceDetailUiState {
    object Loading : PlaceDetailUiState()
    data class Success(val place: PlaceDetail) : PlaceDetailUiState()
    data class Error(val message: String) : PlaceDetailUiState()
}
