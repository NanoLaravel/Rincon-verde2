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

@Composable
fun HeaderSection(
  modifier: Modifier = Modifier

) {
  Box(
    modifier = modifier
  ) {
    // Imagen de fondo del pueblo
    AsyncImage(
      // model = "https://images.unsplash.com/photo-1544551763-46a013bb70d5?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80",
      //model = R.drawable.entertainment.xml,
      model= "https://images.unsplash.com/photo-1702055328255-515c1c2c9e81",
      contentDescription = "Pueblo de Chinácota",
      modifier = Modifier.fillMaxSize(),
      contentScale = ContentScale.Crop
    )

    // Overlay gradiente para mejorar legibilidad
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(
          brush = Brush.verticalGradient(
            colors = listOf(
              Color.Black.copy(alpha = 0.5f),
              Color.Black.copy(alpha = 0.3f),
              Color.Black.copy(alpha = 0.1f),
              Color.Transparent
            ),
            startY = 0f,
            endY = Float.POSITIVE_INFINITY
          )
        )
    )

    // Contenido del header
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.SpaceBetween,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Espaciador para empujar el contenido hacia abajo
      Spacer(modifier = Modifier.weight(1f))

      // Título y subtítulo
      Column {
        Text(
          text = "Descubre CHINÁCOTA",
          style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp),
          color = Color.White,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
          text = "Clima perfecto • Naturaleza exuberante",
          style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
          color = Color.White.copy(alpha = 0.95f)
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Buscador sobre fondo blanco
      SearchBar(
        modifier = Modifier.fillMaxWidth()
          .padding(horizontal = 48.dp)
      )
    }
  }
}

@Composable
fun SearchBar(
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .height(44.dp)
      .clip(RoundedCornerShape(12.dp))
      //.background(MaterialTheme.colorScheme.onSurfaceVariant)
      .background(Color.White.copy(alpha = 0.4f))
      .padding(horizontal = 24.dp),
    contentAlignment = Alignment.CenterStart
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "Buscar",
        // tint = MaterialTheme.colorScheme.primary,
        tint = Color.White,
        modifier = Modifier.size(20.dp)
      )

      BasicTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier.weight(1f),
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
        visualTransformation = VisualTransformation.None,
        decorationBox = { innerTextField ->
          if ("" == "") {
            Text(
              text = "Buscar",
              style = MaterialTheme.typography.bodyMedium,
              // color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
              color = Color.White
            )
          }
          innerTextField()
        }
      )
    }
  }
}

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
      //horizontalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceEvenly
    ) {
      // Categorías principales
      items(categoryConfig.filter { it.key != PlaceCategory.FAVORITES }.entries.toList()) { (category, config) ->
        CategoryItem(
          config = config,
          onClick = { }//onCategoryClick(category)
        )
      }

      // Favoritos separado
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
      .padding(2.dp) // Pequeño margen entre columnas
    //modifier = Modifier
    //.width(90.dp)
    //.clickable { onClick()
    //}
  ) {
    Box(
      modifier = Modifier
        .size(64.dp)
        .clip(CircleShape)
        .background(config.color.copy(alpha = 0.8f)),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = config.icon,
        contentDescription = config.title,
        //tint = config.color,
        tint = Color.White, // Icono en blanco puro
        modifier = Modifier.size(32.dp)
      )
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = config.title,
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.onBackground,
      textAlign = androidx.compose.ui.text.style.TextAlign.Center,
      maxLines = 2,
      minLines = 2, // Mantiene la alineación aunque el texto sea de una sola línea
      overflow = TextOverflow.Ellipsis
    )
  }
}

@Composable
fun TopRatedSection(
  places: List<Place>,
  onPlaceClick: (Place) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "Mejor Valorados",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.SemiBold
      )

      Text(
        text = "Ver todos",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.clickable { /* Navegar a todos */ }
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      items(places) { place ->
        TopRatedCard(
          place = place,
          onClick = { onPlaceClick(place) }
        )
      }
    }
  }
}

@Composable
fun TopRatedCard(
  place: Place,
  onClick: () -> Unit
) {
  Card(
    onClick = onClick,
    modifier = Modifier.width(120.dp)
      .height(98.dp),
    shape = RoundedCornerShape(12.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Column {
      // Imagen
      Box(
        modifier = Modifier
          .fillMaxWidth()
          //.height(100.dp)
      ) {
        AsyncImage(
          model = "https://images.unsplash.com/photo-1702055328255-515c1c2c9e81",
          contentDescription = place.name,
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop
        )

        // Rating overlay como en Figma
        Box(
          modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 6.dp, vertical = 2.dp)
            .align(Alignment.TopEnd)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = "Rating",
              tint = Color.White,
              modifier = Modifier.size(10.dp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
              text = "%.1f".format(place.rating),
              color = Color.White,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      // Contenido
      Column(
        modifier = Modifier.padding(12.dp)
      ) {
        Text(
          text = place.name,
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onSurface,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = place.location,
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }
    }
  }
}

@Composable
fun EventsSection(
  events: List<Event>,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "Próximos Eventos",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.SemiBold
      )

      Icon(
        imageVector = Icons.Default.Event,
        contentDescription = "Eventos",
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(20.dp)
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    Column(
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      events.take(2).forEach { event ->
        EventCard(event = event)
      }
    }
  }
}

@Composable
fun EventCard(event: Event) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(8.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Indicador de fecha
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(end = 16.dp)
      ) {
        // Imagen
        AsyncImage(
          model = "https://images.unsplash.com/photo-1506744038136-46273834b3fb",
          contentDescription = event.title,
          modifier = Modifier
            .width(96.dp)
            .fillMaxHeight(),
          contentScale = ContentScale.Crop)
    /*    Text(
          text = event.date.split("-").lastOrNull() ?: "15",
          style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp),
          color = MaterialTheme.colorScheme.primary,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "MAR",
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.primary,
          fontWeight = FontWeight.Medium
        )*/
      }

      // Información del evento
      Column(
        modifier = Modifier.weight(1f)
      ) {
        Text(
          text = event.title,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurface,
          fontWeight = FontWeight.Medium,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Place,
            contentDescription = "Ubicación",
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.size(12.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = event.location,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }
      }
    }
  }
}

// Versión alternativa de EventCard más parecida a Figma (simple)
@Composable
fun SimpleEventCard(event: Event) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Column(
      modifier = Modifier.padding(16.dp)
    ) {
      Text(
        text = event.title,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(8.dp))

      Row {
        Text(
          text = event.date,
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.primary,
          fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
          text = "•",
          color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
          text = event.location,
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
      }
    }
  }
}
