package com.example.rincon_verde2.ui.feature.placedetail.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
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
fun DetailHeader(
  onBackClick: () -> Unit,
  onShareClick: () -> Unit,
  onFavoriteClick: () -> Unit,
  isFavorite: Boolean = false,
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
      IconButton(onClick = onShareClick) {
        Icon(
          imageVector = Icons.Default.Share,
          contentDescription = "Compartir",
          tint = MaterialTheme.colorScheme.onSurface
        )
      }
      IconButton(onClick = onFavoriteClick) {
        Icon(
          imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
          contentDescription = "Agregar a favoritos",
          tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
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
fun DetailHeaderPreview() {
  DetailHeader(
    onBackClick = { },
    onShareClick = { },
    onFavoriteClick = { },
    isFavorite = false
  )
}
