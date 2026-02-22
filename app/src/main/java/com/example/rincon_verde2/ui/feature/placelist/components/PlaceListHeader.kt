package com.example.rincon_verde2.ui.feature.placelist.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceListHeader(
  onBackClick: () -> Unit,
  onFilterClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  TopAppBar(
    title = { },
    navigationIcon = {
      IconButton(onClick = onBackClick) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = "Volver",
          tint = MaterialTheme.colorScheme.onSurface
        )
      }
    },
    actions = {
      IconButton(onClick = onFilterClick) {
        Icon(
          imageVector = Icons.Default.Tune,
          contentDescription = "Filtros",
          tint = MaterialTheme.colorScheme.onSurface
        )
      }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.background
    ),
    modifier = modifier
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PlaceListHeaderPreview() {
  PlaceListHeader(
    onBackClick = { },
    onFilterClick = { }
  )
}
