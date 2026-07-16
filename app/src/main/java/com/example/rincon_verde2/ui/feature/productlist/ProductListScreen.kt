package com.example.rincon_verde2.ui.feature.productlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rincon_verde2.domain.model.Product
import com.example.rincon_verde2.ui.feature.home.components.ProductCard
import com.example.rincon_verde2.ui.feature.home.components.ProductSkeleton
import com.example.rincon_verde2.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    products: List<Product>,
    isLoading: Boolean,
    onBackClick: () -> Unit,
    onProductClick: (Product) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Productos Locales") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(Spacing.spacingMd),
                horizontalArrangement = Arrangement.spacedBy(Spacing.spacingMd),
                verticalArrangement = Arrangement.spacedBy(Spacing.spacingMd),
                modifier = Modifier.padding(paddingValues)
            ) {
                items(10) {
                    ProductSkeleton()
                }
            }
        } else if (products.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No se encontraron productos")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3), // 3 columnas para que sean más pequeñas
                contentPadding = PaddingValues(Spacing.spacingMd),
                horizontalArrangement = Arrangement.spacedBy(Spacing.spacingMd),
                verticalArrangement = Arrangement.spacedBy(Spacing.spacingMd),
                modifier = Modifier.padding(paddingValues)
            ) {
                items(products) { product ->
                    ProductCard(
                        product = product,
                        onClick = { onProductClick(product) }
                    )
                }
            }
        }
    }
}
