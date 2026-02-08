package com.example.rincon_verde2.ui.feature.home


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.rincon_verde2.ui.feature.home.components.*

// Data classes
data class Place(
  val id: String,
  val name: String,
  val rating: Float,
  val imageUrl: String,
  val category: PlaceCategory,
  val location: String
)

data class Event(
  val id: String,
  val title: String,
  val date: String,
  val location: String,
  val image: String
)

data class CategoryConfig(
  val title: String,
  val icon: ImageVector,
  val color: Color
)

enum class PlaceCategory {
  ACTIVITY, EAT, STAY, FAVORITES
}

@Composable
fun HomeScreen(
  places: List<Place>,
  events: List<Event>,
  favorites: List<Place>,
  onPlaceClick: (Place) -> Unit,
  onCategoryClick: (PlaceCategory) -> Unit,
  onToggleFavorite: (String) -> Unit,
  onFilterClick: () -> Unit,
  onNavigate: (String) -> Unit
) {
  val scrollState = rememberScrollState()
  val topRatedPlaces = remember(places) {
    places.sortedByDescending { it.rating }.take(6)
  }

  // Configuración de categorías usando la paleta de colores
  val categoryConfig = mapOf(
    PlaceCategory.ACTIVITY to CategoryConfig(
      title = "Qué Hacer",
      icon = Icons.Default.Place,
      color = MaterialTheme.colorScheme.primary // GreenPrimary
    ),
    PlaceCategory.EAT to CategoryConfig(
      title = "Dónde Comer",
      icon = Icons.Default.Restaurant,
      color = Color(0xFFF97316) // OrangeSecondary
    ),
    PlaceCategory.STAY to CategoryConfig(
      title = "Dónde Alojarse",
      icon = Icons.Default.Bed,
      color = Color(0xFF0284C7) // BlueAccent
    ),
    PlaceCategory.FAVORITES to CategoryConfig(
      title = "Favoritos",
      icon = Icons.Default.Favorite,
      color = Color(0xFFDC2626) // Rojo para favoritos
    )
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .verticalScroll(scrollState)
  ) {
    // 1. Header estilo Figma
    HeaderSection(
      modifier = Modifier.padding(bottom = 4.dp)
        .fillMaxWidth()
        .height(180.dp) // Dale una altura específica para probar
    )

    // 2. Categorías en fila horizontal
    CategoriesSection(
      categoryConfig = categoryConfig,
      favoritesCount = favorites.size,
      onCategoryClick = onCategoryClick,
      onFavoritesClick = { onNavigate("favorites") },
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
    )

    Spacer(modifier = Modifier.height(12.dp))

    // 3. Mejor Valorados
    TopRatedSection(
      places = topRatedPlaces,
      onPlaceClick = onPlaceClick,
      modifier = Modifier.padding(horizontal = 12.dp)
    )

    Spacer(modifier = Modifier.height(12.dp))

    // 4. Próximos Eventos
    EventsSection(
      events = events,
      modifier = Modifier.padding(horizontal = 12.dp)
    )

    Spacer(modifier = Modifier.height(14.dp))
  }
}

// Header & SearchBar moved to `ui/feature/home/components/HeaderSection.kt`

// CategoriesSection moved to `ui/feature/home/components/CategoryGrid.kt` and CategoryItem to `CategoryCard.kt`

// CategoriesSection and CategoryItem moved to components/CategoryGrid.kt and CategoryCard.kt

// TopRatedSection and TopRatedCard moved to components/TopRatedSection.kt

// EventsSection, EventCard and SimpleEventCard moved to `ui/feature/home/components/EventsSection.kt`
