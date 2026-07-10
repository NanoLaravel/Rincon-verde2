package com.example.rincon_verde2.ui.feature.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.rincon_verde2.R
import com.example.rincon_verde2.ui.theme.Strings
import com.example.rincon_verde2.ui.theme.Spacing
import com.example.rincon_verde2.ui.theme.CornerRadius
import com.example.rincon_verde2.ui.theme.IconSize
import com.example.rincon_verde2.ui.theme.ComponentSize

@Composable
fun HeaderSection(
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
  ) {

    Image(
      painterResource(id= R.drawable.foto_chinacota),
      contentDescription = Strings.homeHeaderImage,
      modifier = Modifier.fillMaxSize(),
      contentScale = ContentScale.Crop
    )

    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(
          brush = Brush.verticalGradient(
            colors = listOf(
          //    Color.Black.copy(alpha = 0.5f),
           //   Color.Black.copy(alpha = 0.3f),
              Color.Black.copy(alpha = 0.1f),
              Color.Transparent
            ),
            startY = 0f,
            endY = Float.POSITIVE_INFINITY
          )
        )
    )

    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = Spacing.spacingLg, vertical = Spacing.spacingMd),
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Spacer(modifier = Modifier.height(Spacing.spacingLg))

      Text(
        text = Strings.homeDiscover,
        style = MaterialTheme.typography.headlineLarge.copy(fontSize = 28.sp),
        color = Color.White,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = Spacing.spacingXs)
      )

      Text(
        text = Strings.homeSubtitle,
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
        color = Color.White.copy(alpha = 0.95f),
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(Spacing.spacingMd))
    }
  }
}

@Preview(showBackground = true)
@Composable
fun HeaderSectionPreview() {
  androidx.compose.material3.MaterialTheme {
    HeaderSection(modifier = Modifier.height(ComponentSize.headerXLarge))
  }
}

