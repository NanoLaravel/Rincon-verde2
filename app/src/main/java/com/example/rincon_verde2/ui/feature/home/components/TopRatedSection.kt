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
import com.example.rincon_verde2.ui.theme.Strings
import com.example.rincon_verde2.ui.theme.Spacing
import com.example.rincon_verde2.ui.theme.CornerRadius
import com.example.rincon_verde2.ui.theme.Elevation
import com.example.rincon_verde2.ui.theme.IconSize
import com.example.rincon_verde2.ui.theme.ComponentSize
import com.example.rincon_verde2.ui.components.common.ShimmerEffect

@Composable
fun TopRatedSection(
  places: List<Place>,
  onPlaceClick: (Place) -> Unit,
  modifier: Modifier = Modifier,
  isLoading: Boolean = false
) {
  Column(modifier = modifier) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
      verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
      Text(
        text = Strings.homeTopRated,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.SemiBold
      )
    }

    Spacer(modifier = Modifier.height(Spacing.spacingMd))

    LazyRow(
      horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(Spacing.spacingMd),
      modifier = Modifier.fillMaxWidth()
    ) {
      if (isLoading) {
        items(3) {
          TopRatedSkeleton()
        }
      } else {
        items(places) { place ->
          TopRatedCard(
            place = place,
            onClick = { onPlaceClick(place) }
          )
        }
      }
    }
  }
}

@Composable
fun TopRatedSkeleton() {
  Card(
    modifier = Modifier
      .width(140.dp)
      .height(180.dp),
    shape = RoundedCornerShape(CornerRadius.radiusMd),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
  ) {
    Column {
      ShimmerEffect(
        modifier = Modifier
          .fillMaxWidth()
          .height(115.dp),
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
      )
      Spacer(modifier = Modifier.height(8.dp))
      ShimmerEffect(
        modifier = Modifier
          .padding(horizontal = 8.dp)
          .fillMaxWidth(0.7f)
          .height(14.dp)
      )
      Spacer(modifier = Modifier.height(4.dp))
      ShimmerEffect(
        modifier = Modifier
          .padding(horizontal = 8.dp)
          .fillMaxWidth(0.5f)
          .height(10.dp)
      )
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
    modifier = Modifier
      .width(140.dp)
      .height(180.dp),
    shape = RoundedCornerShape(CornerRadius.radiusMd),
    elevation = CardDefaults.cardElevation(defaultElevation = Elevation.low),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Column {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(115.dp)
      ) {
        AsyncImage(
          model = place.imageUrl,
          contentDescription = place.name,
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop
        )

        // Rating badge - esquina superior derecha, más pequeño y redondeado
        Box(
          modifier = Modifier
            .padding(Spacing.spacingSm)
            .clip(RoundedCornerShape(CornerRadius.radiusLg))
            .background(Color.Black.copy(alpha = 0.6f))
            .padding(horizontal = Spacing.spacingSm, vertical = Spacing.spacingXxs)
            .align(androidx.compose.ui.Alignment.TopEnd)
        ) {
          Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = Strings.cdRating,
              tint = Color.Yellow,
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
        modifier = Modifier.padding(Spacing.spacingSm)
      ) {
        Text(
          text = place.name,
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onSurface,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(2.dp))

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
