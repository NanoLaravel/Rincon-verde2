package com.example.rincon_verde2.ui.feature.placelist.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place

@Composable
fun CategoryHeader(
  icon: ImageVector,
  title: String,
  subtitle: String,
  iconColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = icon,
      contentDescription = title,
      tint = iconColor,
      modifier = Modifier.size(28.dp)
    )

    Spacer(modifier = Modifier.width(12.dp))

    Column {
      Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onBackground
      )

      Text(
        text = subtitle,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun CategoryHeaderPreview() {
  CategoryHeader(
    icon = Icons.Default.Place,
    title = "Qué Hacer",
    subtitle = "Explora actividades y experiencias únicas",
    iconColor = MaterialTheme.colorScheme.primary
  )
}
