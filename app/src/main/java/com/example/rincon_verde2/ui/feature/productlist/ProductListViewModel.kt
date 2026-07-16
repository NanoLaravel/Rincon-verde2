package com.example.rincon_verde2.ui.feature.productlist

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

data class ProductListUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val products: List<Product> = emptyList()
)

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    fun loadProducts() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val products = productRepository.getAllProducts()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    products = products
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error al cargar productos"
                )
            }
        }
    }
}
