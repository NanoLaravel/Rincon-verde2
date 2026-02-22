package com.example.rincon_verde2.ui.feature.placedetail.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rincon_verde2.ui.feature.placedetail.Review

@Composable
fun RecentReviewsSection(
  reviews: List<Review>,
  modifier: Modifier = Modifier
) {
  if (reviews.isEmpty()) return

  Column(modifier = modifier.fillMaxWidth()) {
    Text(
      text = "Reseñas Recientes",
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.SemiBold,
      color = MaterialTheme.colorScheme.onBackground,
      modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
      reviews.forEach { review ->
        ReviewCard(review = review)
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun RecentReviewsSectionPreview() {
  RecentReviewsSection(
    reviews = listOf(
      Review(
        id = "1",
        author = "Juan Pérez",
        rating = 4.8f,
        text = "Excelente lugar, muy recomendado.",
        date = "Hace 1 semana",
        avatar = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=100&h=100&fit=crop"
      ),
      Review(
        id = "2",
        author = "María López",
        rating = 4.5f,
        text = "Buena comida y buen servicio.",
        date = "Hace 2 semanas",
        avatar = "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=100&h=100&fit=crop"
      )
    )
  )
}
