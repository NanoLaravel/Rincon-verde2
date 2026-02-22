package com.example.rincon_verde2.ui.feature.placelist.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ResultsCounter(
  count: Int,
  modifier: Modifier = Modifier
) {
  Text(
    text = "$count lugares encontrados",
    style = MaterialTheme.typography.bodyMedium,
    fontWeight = FontWeight.Medium,
    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
    modifier = modifier
      .padding(horizontal = 16.dp, vertical = 8.dp)
  )
}

@Preview(showBackground = true)
@Composable
fun ResultsCounterPreview() {
  ResultsCounter(count = 12)
}
