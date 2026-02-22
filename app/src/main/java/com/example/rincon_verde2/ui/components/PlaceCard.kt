package com.example.rincon_verde2.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rincon_verde2.domain.model.Place
import com.example.rincon_verde2.ui.theme.Strings
import com.example.rincon_verde2.ui.theme.Spacing
import com.example.rincon_verde2.ui.theme.CornerRadius
import com.example.rincon_verde2.ui.theme.Elevation
import com.example.rincon_verde2.ui.theme.IconSize
import com.example.rincon_verde2.ui.theme.ComponentSize
import com.example.rincon_verde2.ui.theme.YellowAccent

@Composable
fun PlaceCard(
  place: Place,
  onClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() },
    shape = RoundedCornerShape(CornerRadius.radiusLg),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = Elevation.medium)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
    ) {
      // Imagen
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(ComponentSize.cardLarge)
      ) {
        AsyncImage(
          model = place.imageUrl,
          contentDescription = place.name,
          modifier = Modifier
            .fillMaxWidth()
            .height(ComponentSize.cardLarge),
          contentScale = ContentScale.Crop
        )
        
        // Rating badge en la esquina
        Card(
          modifier = Modifier
            .padding(Spacing.spacingMd)
            .align(Alignment.TopEnd),
          shape = RoundedCornerShape(CornerRadius.radiusMd),
          colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.7f))
        ) {
          Row(
            modifier = Modifier.padding(horizontal = Spacing.spacingMd, vertical = Spacing.spacingXs),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = Strings.cdRating,
              tint = YellowAccent,
              modifier = Modifier.size(IconSize.xs)
            )
            Spacer(modifier = Modifier.width(Spacing.spacingXxs))
            Text(
              text = "${place.rating}",
              style = MaterialTheme.typography.labelSmall,
              color = Color.White,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
      
      // Contenido
      Column(
        modifier = Modifier.padding(Spacing.spacingMd)
      ) {
        Text(
          text = place.name,
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.onSurface,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis
        )
        
        Spacer(modifier = Modifier.height(Spacing.spacingXs))
        
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = Strings.cdLocation,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(IconSize.xs)
          )
          Spacer(modifier = Modifier.width(Spacing.spacingXs))
          Text(
            text = place.location,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }
      }
    }
  }
}
