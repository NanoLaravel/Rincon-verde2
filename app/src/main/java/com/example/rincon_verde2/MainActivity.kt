package com.example.rincon_verde2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.rincon_verde2.ui.feature.home.Event
import com.example.rincon_verde2.ui.feature.home.HomeScreen
import com.example.rincon_verde2.ui.feature.home.Place
import com.example.rincon_verde2.ui.feature.home.PlaceCategory
import com.example.rincon_verde2.ui.feature.placelist.PlaceListScreen
import com.example.rincon_verde2.ui.feature.placedetail.PlaceDetailScreen
import com.example.rincon_verde2.ui.feature.placedetail.model.Amenity
import com.example.rincon_verde2.ui.feature.placedetail.model.ContactInfo
import com.example.rincon_verde2.ui.feature.placedetail.model.PlaceDetail
import com.example.rincon_verde2.ui.feature.placedetail.model.Review
import com.example.rincon_verde2.ui.theme.Rinconverde2Theme
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.Icons
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      Rinconverde2Theme {
        // Datos de ejemplo
        val samplePlaces = listOf(
          Place(
            id = "1",
            name = "Restaurante Bella Italia",
            rating = 4.8f,
            imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400&h=300&fit=crop",
            category = PlaceCategory.EAT,
            location = "Centro Histórico"
          ),
          Place(
            id = "2",
            name = "Hotel Paraíso Resort",
            rating = 4.6f,
            imageUrl = "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=400&h=300&fit=crop",
            category = PlaceCategory.STAY,
            location = "Zona Turística"
          ),
          Place(
            id = "3",
            name = "Senderismo en Montaña",
            rating = 4.7f,
            imageUrl = "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400&h=300&fit=crop",
            category = PlaceCategory.ACTIVITY,
            location = "Sierra Verde"
          ),
          Place(
            id = "4",
            name = "Café Artesanal",
            rating = 4.5f,
            imageUrl = "https://images.unsplash.com/photo-1442512595331-e89e73853f31?w=400&h=300&fit=crop",
            category = PlaceCategory.EAT,
            location = "Barrio Antiguo"
          ),
          Place(
            id = "5",
            name = "Kayaking en Río",
            rating = 4.9f,
            imageUrl = "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400&h=300&fit=crop",
            category = PlaceCategory.ACTIVITY,
            location = "Río Azul"
          ),
          Place(
            id = "6",
            name = "Posada de Campo",
            rating = 4.4f,
            imageUrl = "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=400&h=300&fit=crop",
            category = PlaceCategory.STAY,
            location = "Afueras"
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

        // Estado de navegación con remember
        val currentScreen = remember { mutableStateOf<String>("home") }
        val selectedCategory = remember { mutableStateOf<PlaceCategory>(PlaceCategory.ACTIVITY) }
        val selectedPlace = remember { mutableStateOf<Place?>(null) }

        if (currentScreen.value == "home") {
          HomeScreen(
            places = samplePlaces,
            events = sampleEvents,
            favorites = sampleFavorites,
            onPlaceClick = { place ->
              println("Clic en: ${place.name}")
            },
            onCategoryClick = { category ->
              println("Categoría seleccionada: $category")
              selectedCategory.value = category
              currentScreen.value = "placeList"
            },
            onToggleFavorite = { placeId ->
              println("Toggle favorite: $placeId")
            },
            onFilterClick = {
              println("Mostrar filtros")
            },
            onNavigate = { route ->
              println("Navegar a: $route")
              if (route == "favorites") {
                selectedCategory.value = PlaceCategory.FAVORITES
                currentScreen.value = "placeList"
              }
            }
          )
        } else if (currentScreen.value == "placeList") {
          val filteredPlaces = if (selectedCategory.value == PlaceCategory.FAVORITES) {
            sampleFavorites
          } else {
            samplePlaces.filter { it.category == selectedCategory.value }
          }

          PlaceListScreen(
            category = selectedCategory.value,
            places = filteredPlaces,
            onBackClick = {
              currentScreen.value = "home"
            },
            onFilterClick = {
              println("Mostrar filtros en PlaceList")
            },
            onPlaceClick = { place ->
              println("Clic en lugar desde lista: ${place.name}")
              selectedPlace.value = place
              currentScreen.value = "placeDetail"
            }
          )
        } else if (currentScreen.value == "placeDetail" && selectedPlace.value != null) {
          val place = selectedPlace.value!!
          
          // Convertir Place a PlaceDetail
          val placeDetail = PlaceDetail(
            id = place.id,
            name = place.name,
            rating = place.rating,
            priceCategory = 2,
            imageUrl = place.imageUrl,
            location = place.location,
            description = "Un hermoso lugar que representa lo mejor de la región. Ofrece una experiencia única con servicios de calidad y atención al detalle.",
            amenities = listOf(
              Amenity("1", "WiFi", Icons.Default.Wifi, true),
              Amenity("2", "Piscina", Icons.Default.Pool, true),
              Amenity("3", "Estacionamiento", Icons.Default.CheckCircle, true),
              Amenity("4", "Aire Acondicionado", Icons.Default.CheckCircle, true)
            ),
            contact = ContactInfo(
              phone = "+1 (555) 123-4567",
              email = "info@${place.name.lowercase().replace(" ", "")}.com",
              website = "www.${place.name.lowercase().replace(" ", "")}.com",
              hours = "Lun-Dom: 12:00 PM - 10:00 PM"
            ),
            reviews = listOf(
              Review(
                id = "1",
                author = "Juan Pérez",
                rating = 4.8f,
                text = "Excelente experiencia, todo fue perfecto.",
                date = "Hace 1 semana",
                avatar = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=100&h=100&fit=crop"
              ),
              Review(
                id = "2",
                author = "María López",
                rating = 4.5f,
                text = "Muy bueno, recomendado.",
                date = "Hace 2 semanas",
                avatar = "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=100&h=100&fit=crop"
              )
            ),
            isFavorite = false
          )
          
          PlaceDetailScreen(
            place = placeDetail,
            onBackClick = {
              currentScreen.value = "placeList"
            },
            onShareClick = {
              println("Compartir: ${place.name}")
            },
            onToggleFavorite = {
              println("Favorito toggled")
            },
            onGetDirections = {
              println("Obtener direcciones a ${place.name}")
            },
            onCall = {
              println("Llamar al lugar")
            }
          )
        }
      }
    }
  }
}
