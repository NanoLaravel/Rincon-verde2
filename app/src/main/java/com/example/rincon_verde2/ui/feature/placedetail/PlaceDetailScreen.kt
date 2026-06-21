package com.example.rincon_verde2.ui.feature.placedetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rincon_verde2.ui.feature.placedetail.components.ActionButtons
import com.example.rincon_verde2.ui.feature.placedetail.components.AmenitiesSection
import com.example.rincon_verde2.ui.feature.placedetail.components.ContactInfoSection
import com.example.rincon_verde2.ui.feature.placedetail.components.DescriptionSection
import com.example.rincon_verde2.ui.feature.placedetail.components.DetailHeader
import com.example.rincon_verde2.ui.feature.placedetail.components.HeroImage
import com.example.rincon_verde2.ui.feature.placedetail.components.PlaceTitleSection
import com.example.rincon_verde2.ui.feature.placedetail.components.RecentReviewsSection

@Composable
fun PlaceDetailScreen(
  place: PlaceDetail,
  onBackClick: () -> Unit = { },
  onShareClick: () -> Unit = { },
  onToggleFavorite: () -> Unit = { },
  onGetDirections: () -> Unit = { },
  onCall: () -> Unit = { }
) {
  val isFavorite = remember { mutableStateOf(place.isFavorite) }

  Scaffold(
    topBar = {
      DetailHeader(
        onBackClick = onBackClick,
        onShareClick = onShareClick,
        onFavoriteClick = {
          isFavorite.value = !isFavorite.value
          onToggleFavorite()
        },
        isFavorite = isFavorite.value
      )
    }
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      item {
        HeroImage(
          imageUrl = place.imageUrl,
          imageUrls = place.imageUrls,
          rating = place.rating,
          priceCategory = place.priceCategory
        )
      }

      item {
        PlaceTitleSection(
          name = place.name,
          location = place.location
        )
      }

      item {
        DescriptionSection(
          description = place.description
        )
      }

      item {
        AmenitiesSection(
          amenities = place.amenities
        )
      }

      item {
        ContactInfoSection(
          contact = place.contact
        )
      }

      item {
        ActionButtons(
          onGetDirections = onGetDirections,
          onCall = onCall
        )
      }

      item {
        RecentReviewsSection(
          reviews = place.reviews
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun PlaceDetailScreenPreview() {
  val samplePlace = PlaceDetail(
    id = "1",
    name = "Restaurante Bella Italia",
    rating = 4.8f,
    priceCategory = 2,
    imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=600&h=400&fit=crop",
    location = "Centro Histórico",
    description = "Un hermoso restaurante con vistas al mar, especializado en cocina italiana tradicional. Ofrece una experiencia culinaria única con ingredientes frescos importados de Italia y un servicio excepcional. El ambiente es acogedor y perfecto para cenas románticas o reuniones familiares.",
    amenities = listOf(
      Amenity("1", "WiFi", Icons.Default.Wifi, true),
      Amenity("2", "Piscina", Icons.Default.Pool, true),
      Amenity("3", "Estacionamiento", Icons.Default.CheckCircle, true),
      Amenity("4", "Aire Acondicionado", Icons.Default.CheckCircle, true)
    ),
    contact = ContactInfo(
      phone = "+1 (555) 123-4567",
      email = "info@bella-italia.com",
      website = "www.bella-italia.com",
      hours = "Lun-Dom: 12:00 PM - 10:00 PM"
    ),
    reviews = listOf(
      Review(
        id = "1",
        author = "Juan Pérez",
        rating = 4.8f,
        text = "Excelente experiencia, la comida estaba deliciosa y el servicio fue impecable. Definitivamente volvería a visitarlo.",
        date = "Hace 1 semana",
        avatar = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=100&h=100&fit=crop"
      ),
      Review(
        id = "2",
        author = "María López",
        rating = 4.5f,
        text = "Buena comida y buen servicio. El ambiente es muy agradable.",
        date = "Hace 2 semanas",
        avatar = "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=100&h=100&fit=crop"
      ),
      Review(
        id = "3",
        author = "Carlos Ruiz",
        rating = 5.0f,
        text = "Lo mejor que he probado. El equipo es muy atento y la comida de calidad premium.",
        date = "Hace 3 semanas",
        avatar = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=100&h=100&fit=crop"
      )
    ),
    isFavorite = false
  )

  PlaceDetailScreen(place = samplePlace)
}
