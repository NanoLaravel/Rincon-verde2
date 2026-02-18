package com.example.rincon_verde2.ui.feature.home.components

import android.util.Log
import com.example.rincon_verde2.domain.model.PlaceCategory
import com.example.rincon_verde2.domain.model.Place
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.remember

@Composable
fun TopRatedSection(
  places: List<Place>,
  onPlaceClick: (Place) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
      verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
      Text(
        text = "Mejor Valorados",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.SemiBold
      )

      Text(
        text = "Ver todos",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
        modifier = Modifier.clickable { /* Navegar a todos */ }
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    LazyRow(
      horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      items(places) { place ->
        TopRatedCard(
          place = place,
          onClick = { onPlaceClick(place) }
        )
      }
    }
  }
}

@Composable
fun TopRatedCard(
  place: Place,
  onClick: () -> Unit
) {
  Log.d("TopRatedCard", "${place.name} imageUrl=${place.imageUrl}")
  Card(
    onClick = onClick,
    modifier = Modifier.width(120.dp)
      .height(98.dp),
    shape = RoundedCornerShape(12.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Column {
      Box(
        modifier = Modifier
          .fillMaxWidth()
      ) {
        AsyncImage(
          model = place.imageUrl,
          contentDescription = place.name,
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop
        )

        Box(
          modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 6.dp, vertical = 2.dp)
            .align(androidx.compose.ui.Alignment.TopEnd)
        ) {
          Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = "Rating",
              tint = Color.White,
              modifier = Modifier.size(10.dp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
              text = "%.1f".format(place.rating),
              color = Color.White,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Column(
        modifier = Modifier.padding(12.dp)
      ) {
        Text(
          text = place.name,
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onSurface,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = place.location,
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun TopRatedSectionPreview() {
  val sample = listOf(
    Place(id = "1", name = "Restaurante Prueba", description = "Comida deliciosa", rating = 4.6f, imageUrl = "", category = PlaceCategory.EAT, location = "Ciudad", reviewCount = 120, phone = "+57 123", address = "Dirección", hours = "12-10"),
    Place(id = "2", name = "Hotel Prueba", description = "Hotel cómodo", rating = 4.2f, imageUrl = "", category = PlaceCategory.STAY, location = "Ciudad", reviewCount = 85, phone = "+57 456", address = "Dirección 2", hours = "24H")
  )
  TopRatedSection(places = sample, onPlaceClick = {}, modifier = Modifier)
}

