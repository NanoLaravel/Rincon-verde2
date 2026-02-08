package com.example.rincon_verde2.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rincon_verde2.ui.feature.home.Place

@Composable
fun PlaceCard(
  place: Place,
  onClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() },
    shape = RoundedCornerShape(8.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Column(modifier = Modifier.padding(8.dp)) {
      if (place.imageUrl.isNotEmpty()) {
        AsyncImage(
          model = place.imageUrl,
          contentDescription = place.name,
          modifier = Modifier
            .fillMaxWidth()
        )
      }
      Text(text = place.name, style = MaterialTheme.typography.titleMedium)
    }
  }
}
