package com.example.rincon_verde2.ui.feature.placedetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rincon_verde2.ui.feature.placedetail.Amenity

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AmenitiesSection(
  amenities: List<Amenity>,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 12.dp)
  ) {
    Text(
      text = "Características",
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.SemiBold,
      color = MaterialTheme.colorScheme.onBackground
    )

    FlowRow(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 12.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      amenities.forEach { amenity ->
        if (amenity.enabled) {
          Surface(
            modifier = Modifier.padding(vertical = 4.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
          ) {
            androidx.compose.foundation.layout.Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Icon(
                imageVector = amenity.icon,
                contentDescription = amenity.name,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(0.dp)
              )
              Text(
                text = amenity.name,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground
              )
            }
          }
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun AmenitiesSectionPreview() {
  AmenitiesSection(
    amenities = listOf(
      Amenity("1", "WiFi", Icons.Default.CheckCircle, true),
      Amenity("2", "Piscina", Icons.Default.CheckCircle, true),
      Amenity("3", "Estacionamiento", Icons.Default.CheckCircle, true),
      Amenity("4", "Aire Acondicionado", Icons.Default.CheckCircle, false)
    )
  )
}
