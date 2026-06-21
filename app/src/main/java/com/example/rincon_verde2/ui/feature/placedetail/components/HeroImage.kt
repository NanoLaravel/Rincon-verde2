package com.example.rincon_verde2.ui.feature.placedetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun HeroImage(
  imageUrl: String,
  imageUrls: List<String> = emptyList(),
  rating: Float,
  priceCategory: Int = 1, // 1 = $, 2 = $$, 3 = $$$
  modifier: Modifier = Modifier
) {
  val priceText = when (priceCategory) {
    1 -> "$"
    2 -> "$$$"
    3 -> "$$$$"
    else -> "$"
  }

  // Si no hay lista de imágenes, usamos la única disponible
  val displayImages = if (imageUrls.isEmpty()) listOf(imageUrl) else imageUrls
  val pagerState = rememberPagerState(pageCount = { displayImages.size })

  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(280.dp)
  ) {
    HorizontalPager(
      state = pagerState,
      modifier = Modifier.fillMaxSize()
    ) { page ->
      AsyncImage(
        model = displayImages[page],
        contentDescription = "Imagen del lugar ${page + 1}",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
      )
    }

    // Indicador de páginas (Dots)
    if (displayImages.size > 1) {
      Row(
        Modifier
          .wrapContentHeight()
          .fillMaxWidth()
          .align(Alignment.BottomCenter)
          .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
      ) {
        repeat(displayImages.size) { iteration ->
          val color = if (pagerState.currentPage == iteration) Color.White else Color.White.copy(alpha = 0.5f)
          Box(
            modifier = Modifier
              .padding(2.dp)
              .clip(CircleShape)
              .background(color)
              .size(8.dp)
          )
        }
      }
    }

    // Rating badge - esquina superior derecha
    Card(
      modifier = Modifier
        .padding(12.dp)
        .align(Alignment.TopEnd),
      shape = RoundedCornerShape(8.dp),
      colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.7f))
    ) {
      Row(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.Star,
          contentDescription = "Rating",
          tint = Color.Yellow,
          modifier = Modifier.size(16.dp)
        )
        Text(
          text = "$rating",
          style = MaterialTheme.typography.labelSmall,
          color = Color.White,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.padding(start = 4.dp)
        )
      }
    }

    // Price category badge - esquina inferior derecha
    Card(
      modifier = Modifier
        .padding(12.dp)
        .align(Alignment.BottomEnd),
      shape = RoundedCornerShape(8.dp),
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
      )
    ) {
      Text(
        text = priceText,
        style = MaterialTheme.typography.labelMedium,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun HeroImagePreview() {
  HeroImage(
    imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=600&h=400&fit=crop",
    rating = 4.8f,
    priceCategory = 2
  )
}
