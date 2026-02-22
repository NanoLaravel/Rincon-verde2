package com.example.rincon_verde2.ui.feature.placedetail.components

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

@Composable
fun DescriptionSection(
  description: String,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 12.dp)
  ) {
    Text(
      text = "Descripción",
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.SemiBold,
      color = MaterialTheme.colorScheme.onBackground
    )

    Text(
      text = description,
      style = MaterialTheme.typography.bodyMedium,
      color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
      modifier = Modifier.padding(top = 8.dp)
    )
  }
}

@Preview(showBackground = true)
@Composable
fun DescriptionSectionPreview() {
  DescriptionSection(
    description = "Un hermoso restaurante con vistas al mar, especializado en cocina italiana tradicional. Ofrece una experiencia culinaria única con ingredientes frescos importados de Italia y un servicio excepcional."
  )
}
