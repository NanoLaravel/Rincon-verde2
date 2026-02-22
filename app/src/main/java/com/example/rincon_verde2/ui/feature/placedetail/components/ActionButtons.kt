package com.example.rincon_verde2.ui.feature.placedetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ActionButtons(
  onGetDirections: () -> Unit,
  onCall: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    Button(
      onClick = onGetDirections,
      modifier = Modifier
        .fillMaxWidth()
        .height(48.dp),
      shape = MaterialTheme.shapes.medium
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Icon(
          imageVector = Icons.Default.LocationOn,
          contentDescription = "Ubicación",
          modifier = Modifier.width(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Obtener Direcciones",
          fontWeight = FontWeight.SemiBold
        )
      }
    }

    Button(
      onClick = onCall,
      modifier = Modifier
        .fillMaxWidth()
        .height(48.dp),
      shape = MaterialTheme.shapes.medium
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Icon(
          imageVector = Icons.Default.Call,
          contentDescription = "Llamar",
          modifier = Modifier.width(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Llamar",
          fontWeight = FontWeight.SemiBold
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun ActionButtonsPreview() {
  ActionButtons(
    onGetDirections = { },
    onCall = { }
  )
}
