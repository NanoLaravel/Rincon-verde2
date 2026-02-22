package com.example.rincon_verde2.ui.feature.placelist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rincon_verde2.ui.components.PlaceCard
import com.example.rincon_verde2.domain.model.Place
import com.example.rincon_verde2.domain.model.PlaceCategory
import com.example.rincon_verde2.ui.feature.placelist.components.CategoryHeader
import com.example.rincon_verde2.ui.feature.placelist.components.PlaceListHeader
import com.example.rincon_verde2.ui.feature.placelist.components.ResultsCounter

data class CategoryInfo(
  val category: PlaceCategory,
  val title: String,
  val subtitle: String,
  val icon: ImageVector,
  val color: Color
)

@Composable
fun PlaceListScreen(
  category: PlaceCategory = PlaceCategory.ACTIVITY,
  places: List<Place> = emptyList(),
  onBackClick: () -> Unit = { },
  onFilterClick: () -> Unit = { },
  onPlaceClick: (Place) -> Unit = { },
  onBottomBarVisibilityChange: (Boolean) -> Unit = {},
  showTopBar: Boolean = true
) {
  val lazyGridState = rememberLazyGridState()
  
  // Detectar dirección del scroll para ocultar/mostrar barra
  var previousScrollValue by remember { mutableIntStateOf(0) }
  
  LaunchedEffect(lazyGridState.firstVisibleItemScrollOffset) {
    val currentScroll = lazyGridState.firstVisibleItemScrollOffset + 
                       lazyGridState.firstVisibleItemIndex * 1000
    val scrollDiff = currentScroll - previousScrollValue
    
    if (kotlin.math.abs(scrollDiff) > 10) {
      if (scrollDiff > 0) {
        onBottomBarVisibilityChange(false)
      } else {
        onBottomBarVisibilityChange(true)
      }
    }
    
    if (lazyGridState.firstVisibleItemIndex == 0 && lazyGridState.firstVisibleItemScrollOffset < 50) {
      onBottomBarVisibilityChange(true)
    }
    
    previousScrollValue = currentScroll
  }
  
  val categoryMap = mapOf(
    PlaceCategory.ACTIVITY to CategoryInfo(
      category = PlaceCategory.ACTIVITY,
      title = "Qué Hacer",
      subtitle = "Explora actividades y experiencias únicas",
      icon = Icons.Default.Place,
      color = Color(0xFF10B981) // Green
    ),
    PlaceCategory.EAT to CategoryInfo(
      category = PlaceCategory.EAT,
      title = "Dónde Comer",
      subtitle = "Descubre los mejores restaurantes y cafés",
      icon = Icons.Default.Restaurant,
      color = Color(0xFFF97316) // Orange
    ),
    PlaceCategory.STAY to CategoryInfo(
      category = PlaceCategory.STAY,
      title = "Dónde Alojarse",
      subtitle = "Encuentra hospedaje cómodo y acogedor",
      icon = Icons.Default.Bed,
      color = Color(0xFF0284C7) // Blue
    ),
    PlaceCategory.FAVORITES to CategoryInfo(
      category = PlaceCategory.FAVORITES,
      title = "Mis Favoritos",
      subtitle = "Tus lugares favoritos guardados",
      icon = Icons.Default.Favorite,
      color = Color(0xFFDC2626) // Red
    )
  )

  val currentCategory = categoryMap[category] ?: categoryMap[PlaceCategory.ACTIVITY]!!

  // Si showTopBar es true, usamos Scaffold con topBar
  // Si es false, asumimos que estamos dentro de otro Scaffold
  if (showTopBar) {
    Scaffold(
      topBar = {
        PlaceListHeader(
          onBackClick = onBackClick,
          onFilterClick = onFilterClick
        )
      }
    ) { paddingValues ->
      PlaceListContent(
        places = places,
        currentCategory = currentCategory,
        lazyGridState = lazyGridState,
        onPlaceClick = onPlaceClick,
        contentPadding = paddingValues
      )
    }
  } else {
    // Sin Scaffold interno, mostrar header como parte del contenido
    Column(modifier = Modifier.fillMaxSize()) {
      PlaceListHeader(
        onBackClick = onBackClick,
        onFilterClick = onFilterClick
      )
      PlaceListContent(
        places = places,
        currentCategory = currentCategory,
        lazyGridState = lazyGridState,
        onPlaceClick = onPlaceClick,
        contentPadding = PaddingValues(0.dp)
      )
    }
  }
}

@Composable
private fun PlaceListContent(
  places: List<Place>,
  currentCategory: CategoryInfo,
  lazyGridState: androidx.compose.foundation.lazy.grid.LazyGridState,
  onPlaceClick: (Place) -> Unit,
  contentPadding: PaddingValues
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .padding(contentPadding)
  ) {
    if (places.isEmpty()) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
      ) {
        CategoryHeader(
          icon = currentCategory.icon,
          title = currentCategory.title,
          subtitle = currentCategory.subtitle,
          iconColor = currentCategory.color,
          modifier = Modifier.align(Alignment.Start)
        )

        ResultsCounter(count = 0)
      }
    } else {
      Column(modifier = Modifier.fillMaxSize()) {
        CategoryHeader(
          icon = currentCategory.icon,
          title = currentCategory.title,
          subtitle = currentCategory.subtitle,
          iconColor = currentCategory.color
        )

        ResultsCounter(count = places.size)

        LazyVerticalGrid(
          columns = GridCells.Fixed(2),
          contentPadding = PaddingValues(12.dp),
          verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp),
          horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp),
          modifier = Modifier.fillMaxSize(),
          state = lazyGridState
        ) {
          items(places) { place ->
            PlaceCard(
              place = place,
              onClick = { onPlaceClick(place) }
            )
          }
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun PlaceListScreenPreview() {
  val samplePlaces = listOf(
    Place(
      id = "1",
      name = "Senderismo Montaña",
      description = "Caminata guiada por senderos montañosos con vistas panorámicas",
      rating = 4.8f,
      imageUrl = "https://images.unsplash.com/photo-1506905925346-21bda4d32df4",
      category = PlaceCategory.ACTIVITY,
      location = "Sierra de Rincón",
      reviewCount = 298,
      phone = "+57 (1) 7890-1234",
      address = "Entrada Sierra de Rincón km 15",
      hours = "7:00 AM - 5:00 PM"
    ),
    Place(
      id = "2",
      name = "Parapente",
      description = "Vuelo en parapente con instructores certificados",
      rating = 4.6f,
      imageUrl = "https://images.unsplash.com/photo-1544551763-46a013bb70d5",
      category = PlaceCategory.ACTIVITY,
      location = "Las Alturas",
      reviewCount = 187,
      phone = "+57 (1) 8901-2345",
      address = "Las Alturas - Zona de vuelo",
      hours = "9:00 AM - 4:00 PM"
    ),
    Place(
      id = "3",
      name = "Kayaking",
      description = "Navegación en kayak por aguas tranquilas del río",
      rating = 4.5f,
      imageUrl = "https://images.unsplash.com/photo-1506905925346-21bda4d32df4",
      category = PlaceCategory.ACTIVITY,
      location = "Río Verde",
      reviewCount = 245,
      phone = "+57 (1) 9012-3456",
      address = "Embarcadero Río Verde",
      hours = "8:00 AM - 6:00 PM"
    ),
    Place(
      id = "4",
      name = "Escalada",
      description = "Escalada en roca con equipo seguro y guías expertos",
      rating = 4.7f,
      imageUrl = "https://images.unsplash.com/photo-1522163182902-8424ffd6e5d5",
      category = PlaceCategory.ACTIVITY,
      location = "Cañón Rojo",
      reviewCount = 312,
      phone = "+57 (1) 0123-4567",
      address = "Cañón Rojo - Paredes de escalada",
      hours = "8:00 AM - 5:00 PM"
    )
  )

  PlaceListScreen(
    category = PlaceCategory.ACTIVITY,
    places = samplePlaces,
    onBackClick = { },
    onFilterClick = { },
    onPlaceClick = { }
  )
}

