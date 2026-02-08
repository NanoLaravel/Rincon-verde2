package com.example.rincon_verde2.ui.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rincon_verde2.ui.feature.home.CategoryConfig
import com.example.rincon_verde2.ui.feature.home.Event
import com.example.rincon_verde2.ui.feature.home.PlaceCategory

@Composable
fun CategoriesSection(
  categoryConfig: Map<PlaceCategory, CategoryConfig>,
  favoritesCount: Int,
  onCategoryClick: (PlaceCategory) -> Unit,
  onFavoritesClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Text(
      text = "Explorar",
      style = MaterialTheme.typography.titleMedium,
      color = MaterialTheme.colorScheme.onBackground,
      fontWeight = FontWeight.SemiBold,
      modifier = Modifier.padding(bottom = 16.dp)
    )

    LazyRow(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceEvenly
    ) {
      items(categoryConfig.filter { it.key != PlaceCategory.FAVORITES }.entries.toList()) { (category, config) ->
        CategoryItem(
          config = config,
          onClick = { /*onCategoryClick(category)*/ }
        )
      }

      item {
        val favoritesConfig = categoryConfig[PlaceCategory.FAVORITES]!!
        CategoryItem(
          config = favoritesConfig.copy(
            title = if (favoritesCount > 0) "Favoritos ($favoritesCount)" else "Favoritos"
          ),
          onClick = onFavoritesClick,
          showBadge = favoritesCount > 0
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun CategoriesSectionPreview() {
  val sample = mapOf(
    PlaceCategory.EAT to CategoryConfig("Comer", Icons.Default.Restaurant, androidx.compose.ui.graphics.Color(0xFFF97316)),
    PlaceCategory.STAY to CategoryConfig("Alojar", Icons.Default.Bed, androidx.compose.ui.graphics.Color(0xFF0284C7)),
    PlaceCategory.FAVORITES to CategoryConfig("Favoritos", Icons.Default.Favorite, androidx.compose.ui.graphics.Color(0xFFDC2626))
  )
  CategoriesSection(categoryConfig = sample, favoritesCount = 2, onCategoryClick = {}, onFavoritesClick = {}, modifier = Modifier)
}

