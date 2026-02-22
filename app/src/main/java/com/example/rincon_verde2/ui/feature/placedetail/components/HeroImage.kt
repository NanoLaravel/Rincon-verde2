package com.example.rincon_verde2.ui.feature.placedetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun HeroImage(
  imageUrl: String,
  rating: Float,
  priceCategory: Int = 1, // 1 = $, 2 = $$, 3 = $$$
  modifier: Modifier = Modifier
) {
  val priceText = when (priceCategory) {
    1 -> "$"
    2 -> "$$"
    3 -> "$$$"
    else -> "$"
  }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(280.dp)
  ) {
    AsyncImage(
      model = imageUrl,
      contentDescription = "Imagen del lugar",
      modifier = Modifier
        .fillMaxWidth()
        .height(280.dp),
      contentScale = ContentScale.Crop
    )

    // Rating badge - esquina superior derecha
    Card(
      modifier = Modifier
        .padding(12.dp)
        .align(Alignment.TopEnd),
      shape = RoundedCornerShape(8.dp),
      colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.7f))
    ) {
      Box(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
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
