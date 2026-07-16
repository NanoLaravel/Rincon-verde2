package com.example.rincon_verde2.ui.feature.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rincon_verde2.data.repository.ProductRepository
import com.example.rincon_verde2.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProductDetailUiState {
    object Loading : ProductDetailUiState()
    data class Success(val product: Product) : ProductDetailUiState()
    data class Error(val message: String) : ProductDetailUiState()
}

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun loadProductDetail(productId: String) {
        viewModelScope.launch {
            _uiState.value = ProductDetailUiState.Loading
            val product = productRepository.getProductById(productId)
            if (product != null) {
                _uiState.value = ProductDetailUiState.Success(product)
            } else {
                _uiState.value = ProductDetailUiState.Error("Producto no encontrado")
            }
        }
    }
}
