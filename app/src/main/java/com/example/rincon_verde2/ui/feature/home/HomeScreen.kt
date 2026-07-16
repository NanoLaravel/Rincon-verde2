package com.example.rincon_verde2.ui.feature.home

import com.example.rincon_verde2.domain.model.Place
import com.example.rincon_verde2.domain.model.Event
import com.example.rincon_verde2.domain.model.PlaceCategory
import com.example.rincon_verde2.domain.model.Product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.rincon_verde2.ui.theme.CategoryColors
import com.example.rincon_verde2.ui.theme.Strings
import com.example.rincon_verde2.ui.theme.Spacing
import com.example.rincon_verde2.ui.theme.ComponentSize

// Data classes
data class CategoryConfig(
  val title: String,
  val icon: ImageVector,
  val color: Color
)

@Composable
fun HomeScreen(
  places: List<Place>,
  events: List<Event>,
  products: List<Product> = emptyList(),
  isLoading: Boolean = false,
  favorites: List<Place>,
  onPlaceClick: (Place) -> Unit,
  onProductClick: (Product) -> Unit = {},
  onViewAllProductsClick: () -> Unit = {},
  onEventClick: (Event) -> Unit = {},
  onCategoryClick: (PlaceCategory) -> Unit,
  onToggleFavorite: (String) -> Unit,
  onFilterClick: () -> Unit,
  onNavigate: (String) -> Unit,
  onBottomBarVisibilityChange: (Boolean) -> Unit = {}
) {
  val scrollState = rememberScrollState()
  val topRatedPlaces = remember(places) {
    places.sortedByDescending { it.rating }.take(6)
  }

  // Detectar dirección del scroll para ocultar/mostrar barra
  var previousScrollValue by remember { mutableIntStateOf(0) }
  
  LaunchedEffect(scrollState.value) {
    val currentScroll = scrollState.value
    val scrollDiff = currentScroll - previousScrollValue
    
    // Solo cambiar si el scroll es significativo (más de 10px)
    if (kotlin.math.abs(scrollDiff) > 10) {
      if (scrollDiff > 0) {
        // Scrolling down - ocultar barra
        onBottomBarVisibilityChange(false)
      } else {
        // Scrolling up - mostrar barra
        onBottomBarVisibilityChange(true)
      }
    }
    
    // Si estamos cerca del inicio, siempre mostrar la barra
    if (currentScroll < 50) {
      onBottomBarVisibilityChange(true)
    }
    
    previousScrollValue = currentScroll
  }

  // Configuración de categorías usando la paleta de colores
  val categoryConfig = mapOf(
    PlaceCategory.ACTIVITY to CategoryConfig(
      title = Strings.homeWhatToDo,
      icon = Icons.Default.Place,
      color = CategoryColors.Activity
    ),
    PlaceCategory.EAT to CategoryConfig(
      title = Strings.homeWhereToEat,
      icon = Icons.Default.Restaurant,
      color = CategoryColors.Eat
    ),
    PlaceCategory.STAY to CategoryConfig(
      title = Strings.homeWhereToStay,
      icon = Icons.Default.Bed,
      color = CategoryColors.Stay
    ),
    PlaceCategory.FAVORITES to CategoryConfig(
      title = Strings.homeFavorites,
      icon = Icons.Default.Favorite,
      color = CategoryColors.Favorites
    )
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .verticalScroll(scrollState)
  ) {
    // 1. Header más pequeño
    HeaderSection(
      modifier = Modifier.padding(bottom = Spacing.spacingXs)
        .fillMaxWidth()
        .height(ComponentSize.headerMedium)
    )

    // 2. Categorías en fila horizontal (más pequeño)
    CategoriesSection(
      categoryConfig = categoryConfig,
      favoritesCount = favorites.size,
      onCategoryClick = onCategoryClick,
      onFavoritesClick = { onNavigate("favorites") },
      modifier = Modifier.padding(horizontal = Spacing.spacingMd, vertical = Spacing.spacingXxs)
    )

    Spacer(modifier = Modifier.height(Spacing.spacingMd))

    // 3. Mejor Valorados (Sin cambios en cards)
    TopRatedSection(
      places = topRatedPlaces,
      onPlaceClick = onPlaceClick,
      modifier = Modifier.padding(horizontal = Spacing.spacingMd),
      isLoading = isLoading
    )

    Spacer(modifier = Modifier.height(Spacing.spacingMd))

    // 4. NUEVA SECCIÓN: Productos Locales
    ProductSection(
      products = products,
      isLoading = isLoading,
      onProductClick = onProductClick,
      onViewAllClick = onViewAllProductsClick,
      modifier = Modifier.padding(horizontal = Spacing.spacingMd)
    )

    Spacer(modifier = Modifier.height(Spacing.spacingMd))

    // 5. Próximos Eventos (más pequeño)
    EventsSection(
      events = events,
      modifier = Modifier.padding(horizontal = Spacing.spacingMd),
      isLoading = isLoading,
      onEventClick = onEventClick
    )

    Spacer(modifier = Modifier.height(Spacing.spacingLg))
  }
}

// Header & SearchBar moved to `ui/feature/home/components/HeaderSection.kt`

// CategoriesSection moved to `ui/feature/home/components/CategoryGrid.kt` and CategoryItem to `CategoryCard.kt`

// CategoriesSection and CategoryItem moved to components/CategoryGrid.kt and CategoryCard.kt

// TopRatedSection and TopRatedCard moved to components/TopRatedSection.kt`

// EventsSection, EventCard and SimpleEventCard moved to `ui/feature/home/components/EventsSection.kt`
