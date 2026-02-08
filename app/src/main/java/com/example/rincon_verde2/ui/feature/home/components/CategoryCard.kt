package com.example.rincon_verde2.ui.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.rincon_verde2.ui.feature.home.CategoryConfig
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun CategoryItem(
    config: CategoryConfig,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    showBadge: Boolean = false
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier // Usamos el modificador que viene del padre
      .clickable { onClick() }
      .width(64.dp)
      .padding(2.dp)
  ) {
    androidx.compose.foundation.layout.Box(
      modifier = Modifier
        .size(64.dp)
        .clip(CircleShape)
        .background(config.color.copy(alpha = 0.8f)),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = config.icon,
        contentDescription = config.title,
        tint = Color.White,
        modifier = Modifier.size(32.dp)
      )
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = config.title,
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.onBackground,
      textAlign = TextAlign.Center,
      maxLines = 2,
      overflow = TextOverflow.Ellipsis
    )
  }
}

