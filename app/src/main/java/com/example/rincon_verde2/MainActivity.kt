package com.example.rincon_verde2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.rincon_verde2.ui.feature.home.Event
import com.example.rincon_verde2.ui.feature.home.HomeScreen
import com.example.rincon_verde2.ui.feature.home.Place
import com.example.rincon_verde2.ui.feature.home.PlaceCategory
import com.example.rincon_verde2.ui.theme.Rinconverde2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // enableEdgeToEdge()

    setContent {
      Rinconverde2Theme {
        // Datos de ejemplo
        val samplePlaces = listOf(
          Place(
            id = "1",
            name = "Restaurante Ejemplo",
            rating = 4.5f,
            imageUrl = "url_imagen",
            category = PlaceCategory.EAT,
            location = "Ciudad"
          ),
          Place(
            id = "2",
            name = "Hotel Ejemplo",
            rating = 4.2f,
            imageUrl = "url_imagen",
            category = PlaceCategory.STAY,
            location = "Ciudad"
          ),
          Place(id = "3",
            name = "recreo Ejemplo",
            rating = 4.2f,
            imageUrl = "url_imagen",
            category = PlaceCategory.ACTIVITY,
            location = "Ciudad"
          )
        )

        val sampleEvents = listOf(
          Event(
            id = "1",
            title = "Festival de Música",
            date = "2024-12-25",
            location = "Plaza Central",
            image = "url_evento"
          ),
          Event(
            id = "2",
            title = "Festival de la pachanga",
            date = "2025-12-25",
            location = "Plaza Central",
            image = "url_evento"
          ),
          Event(
            id = "3",
            title = "Festival del mangostino biche",
            date = "2025-01-25",
            location = "Parque principal",
            image = "url_evento"
          )
        )

        val sampleFavorites = samplePlaces.take(1)

        HomeScreen(
          places = samplePlaces,
          events = sampleEvents,
          favorites = sampleFavorites,
          onPlaceClick = { place ->
            // Manejar clic en lugar
            println("Clic en: ${place.name}")
            // Navegar a detalle del lugar
          },
          onCategoryClick = { category ->
            // Manejar clic en categoría
            println("Categoría seleccionada: $category")
            // Filtrar o navegar
          },
          onToggleFavorite = { placeId ->
            // Toggle favorito
            println("Toggle favorite: $placeId")
            // Actualizar estado
          },
          onFilterClick = {
            // Mostrar filtros
            println("Mostrar filtros")
          },
          onNavigate = { route ->
            // Navegar a otra pantalla
            println("Navegar a: $route")
          }
        )
      }
    }
  }
}




