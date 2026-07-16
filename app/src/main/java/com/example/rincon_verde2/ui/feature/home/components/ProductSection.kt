package com.example.rincon_verde2.ui.feature.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rincon_verde2.domain.model.Product
import com.example.rincon_verde2.ui.theme.Strings
import com.example.rincon_verde2.ui.theme.Spacing
import com.example.rincon_verde2.ui.theme.CornerRadius
import com.example.rincon_verde2.ui.theme.Elevation
import com.example.rincon_verde2.ui.components.common.ShimmerEffect

@Composable
fun ProductSection(
    products: List<Product>,
    isLoading: Boolean,
    onProductClick: (Product) -> Unit,
    onViewAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(
                text = "Productos Locales",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = Strings.homeViewAll,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { onViewAllClick() }
            )
        }

        Spacer(modifier = Modifier.height(Spacing.spacingMd))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacingMd),
            contentPadding = PaddingValues(end = Spacing.spacingMd)
        ) {
            if (isLoading) {
                items(5) {
                    ProductSkeleton()
                }
            } else if (products.isEmpty()) {
                // Si no hay productos pero no está cargando, mostramos skeletons o un mensaje
                items(5) {
                    ProductSkeleton()
                }
            } else {
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

@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .width(95.dp)
            .height(100.dp),
        shape = RoundedCornerShape(CornerRadius.radiusMd),
        elevation = CardDefaults.cardElevation(defaultElevation = Elevation.subtle),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 8.sp),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "$${product.price.toInt()}",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ProductSkeleton() {
    Card(
        modifier = Modifier
            .width(95.dp)
            .height(100.dp),
        shape = RoundedCornerShape(CornerRadius.radiusMd),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
    ) {
        Column {
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp),
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            ShimmerEffect(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxWidth(0.8f)
                    .height(8.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            ShimmerEffect(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxWidth(0.4f)
                    .height(6.dp)
            )
        }
    }
}
