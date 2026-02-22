package com.example.rincon_verde2.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
  onSearch: (String) -> Unit = {},
  modifier: Modifier = Modifier
) {
  val query = remember { mutableStateOf("") }

  Box(
    modifier = modifier
      .height(48.dp)
      .fillMaxWidth()
      .padding(8.dp)
      .background(Color.White.copy(alpha = 0.06f), RoundedCornerShape(8.dp)),
    contentAlignment = Alignment.CenterStart
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.padding(horizontal = 12.dp)
    ) {
      Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "Buscar",
        tint = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.size(20.dp)
      )

      BasicTextField(
        value = query.value,
        onValueChange = {
          query.value = it
          onSearch(it)
        }
      )
    }
  }
}
