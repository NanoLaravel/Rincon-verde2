package com.example.rincon_verde2.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rincon_verde2.domain.model.User
import com.example.rincon_verde2.ui.components.RinconVerdeBottomBar
import com.example.rincon_verde2.ui.feature.auth.AuthScreen
import com.example.rincon_verde2.ui.feature.filter.FilterSheet
import com.example.rincon_verde2.domain.model.Event
import com.example.rincon_verde2.ui.feature.home.HomeScreen
import com.example.rincon_verde2.domain.model.Place
import com.example.rincon_verde2.domain.model.PlaceCategory
import com.example.rincon_verde2.ui.feature.placelist.PlaceListScreen
import com.example.rincon_verde2.ui.feature.placedetail.PlaceDetailScreen
import com.example.rincon_verde2.ui.feature.placedetail.Amenity
import com.example.rincon_verde2.ui.feature.placedetail.ContactInfo
import com.example.rincon_verde2.ui.feature.placedetail.PlaceDetail
import com.example.rincon_verde2.ui.feature.placedetail.Review
import com.example.rincon_verde2.ui.feature.profile.ProfileScreen
import com.example.rincon_verde2.ui.feature.search.SearchScreen
import com.example.rincon_verde2.ui.feature.splash.SplashScreen

@Composable
fun RinconVerdeNavGraph(navController: NavHostController) {
  val isAuthenticated = remember { mutableStateOf(false) }
  val currentUser = remember { mutableStateOf<User?>(null) }
  
  // Sample data
  val samplePlaces = remember {
    listOf(
      Place(
        id = "1",
        name = "Restaurante Bella Italia",
        description = "Auténtica cocina italiana con ingredientes importados",
        rating = 4.8f,
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400&h=300&fit=crop",
        category = PlaceCategory.EAT,
        location = "Centro Histórico",
        reviewCount = 342,
        phone = "+57 (1) 1234-5678",
        address = "Carrera 5 #12-45",
        hours = "12:00 PM - 10:00 PM"
      ),
      Place(
        id = "2",
        name = "Hotel Paraíso Resort",
        description = "Resort de lujo con spa y piscina olímpica",
        rating = 4.6f,
        imageUrl = "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=400&h=300&fit=crop",
        category = PlaceCategory.STAY,
        location = "Zona Turística",
        reviewCount = 298,
        phone = "+57 (1) 2345-6789",
        address = "Avenida Turística #100",
        hours = "24 Horas"
      ),
      Place(
        id = "3",
        name = "Senderismo en Montaña",
        description = "Rutas guiadas con vistas panorámicas de la cordillera",
        rating = 4.7f,
        imageUrl = "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400&h=300&fit=crop",
        category = PlaceCategory.ACTIVITY,
        location = "Parque Nacional",
        reviewCount = 256,
        phone = "+57 (1) 3456-7890",
        address = "Entrada Parque Nacional km 30",
        hours = "6:00 AM - 5:00 PM"
      ),
      Place(
        id = "4",
        name = "Café Artesanal Sunrise",
        description = "Café especializado en café de origen con ambiente bohemio",
        rating = 4.5f,
        imageUrl = "https://images.unsplash.com/photo-1561181286-d3fee7d55364?w=400&h=300&fit=crop",
        category = PlaceCategory.EAT,
        location = "La Candelaria",
        reviewCount = 187,
        phone = "+57 (1) 4567-8901",
        address = "Calle 11 #5-25",
        hours = "7:00 AM - 9:00 PM"
      ),
      Place(
        id = "5",
        name = "Hostal Viajeros Felices",
        description = "Hostal con ambiente social ideal para mochileros",
        rating = 4.4f,
        imageUrl = "https://images.unsplash.com/photo-1566073771259-6a8506099945?w=400&h=300&fit=crop",
        category = PlaceCategory.STAY,
        location = "Centro",
        reviewCount = 421,
        phone = "+57 (1) 5678-9012",
        address = "Carrera 10 #15-60",
        hours = "24 Horas"
      ),
      Place(
        id = "6",
        name = "Ciclopaseo Urbano",
        description = "Recorrido dominical seguro por la ciclovía con paradas gastronómicas",
        rating = 4.9f,
        imageUrl = "https://images.unsplash.com/photo-1502920917128-1aa500764cbd?w=400&h=300&fit=crop",
        category = PlaceCategory.ACTIVITY,
        location = "Ciclovía Bogotá",
        reviewCount = 512,
        phone = "+57 (1) 6789-0123",
        address = "Carrera 7 zona ciclovía",
        hours = "7:00 AM - 2:00 PM (Domingos)"
      )
    )
  }

  val sampleEvents = remember {
    listOf(
      Event(
        id = "1",
        title = "Festival de Gastronomía 2024",
        date = "15 de Marzo",
        location = "Parque Central",
        description = "descripcion falsa",
        image = "https://images.unsplash.com/photo-1555939594-58d7cb561c1a?w=400&h=300&fit=crop"
      ),
      Event(
        id = "2",
        title = "Exposición de Arte Contemporáneo",
        date = "20 de Marzo",
        location = "Museo de Arte",
        description = "descripcion falsa",
        image = "https://images.unsplash.com/photo-1561214115-6d2f1b0609fa?w=400&h=300&fit=crop"
      ),
      Event(
        id = "3",
        title = "Concierto al Aire Libre",
        date = "25 de Marzo",
        location = "Plaza Mayor",
        description = "descripcion falsa",
        image = "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?w=400&h=300&fit=crop"
      )
    )
  }

  NavHost(navController = navController, startDestination = Screen.Splash.route) {
    composable(Screen.Splash.route) {
      SplashScreen(onNavigateToHome = {
        navController.navigate(
          if (isAuthenticated.value) Screen.Home.route else Screen.Auth.createRoute("login")
        ) {
          popUpTo(Screen.Splash.route) { inclusive = true }
        }
      })
    }

    composable(
      route = Screen.Auth.route,
      arguments = listOf(navArgument("mode") { type = NavType.StringType })
    ) {
      AuthScreen(
        mode = "login",
        onLoginSuccess = { email ->
          currentUser.value = User(
            id = email.hashCode().toString(),
            email = email,
            displayName = email.substringBefore("@")
          )
          isAuthenticated.value = true
          navController.navigate(Screen.Home.route) {
            popUpTo(Screen.Auth.route) { inclusive = true }
          }
        },
        onSignUpSuccess = { email, displayName ->
          currentUser.value = User(
            id = email.hashCode().toString(),
            email = email,
            displayName = displayName
          )
          isAuthenticated.value = true
          navController.navigate(Screen.Home.route) {
            popUpTo(Screen.Auth.route) { inclusive = true }
          }
        }
      )
    }

    composable(Screen.Home.route) {
        RinconVerdeScaffold(navController = navController, currentRoute = Screen.Home.route) {
          HomeScreen(
            places = samplePlaces,
            events = sampleEvents,
            favorites = samplePlaces.take(3),
            onPlaceClick = { navController.navigate(Screen.PlaceDetail.createRoute(it.id)) },
            onCategoryClick = { navController.navigate(Screen.PlaceList.createRoute(it.name)) },
            onToggleFavorite = { },
            onFilterClick = { navController.navigate(Screen.Filters.route) },
            onNavigate = { navController.navigate(it) }
          )
        }
      }

      composable(Screen.Search.route) {
        RinconVerdeScaffold(navController = navController, currentRoute = Screen.Search.route) {
          SearchScreen(onFilterClick = { navController.navigate(Screen.Filters.route) })
        }
      }

      composable(Screen.Favorites.route) {
        RinconVerdeScaffold(navController = navController, currentRoute = Screen.Favorites.route) {
          PlaceListScreen(
            category = PlaceCategory.FAVORITES,
            places = samplePlaces.take(3),
            onBackClick = { navController.navigateUp() },
            onFilterClick = { navController.navigate(Screen.Filters.route) },
            onPlaceClick = { navController.navigate(Screen.PlaceDetail.createRoute(it.id)) }
          )
        }
      }

      composable(Screen.Profile.route) {
        RinconVerdeScaffold(navController = navController, currentRoute = Screen.Profile.route) {
          ProfileScreen(
            user = currentUser.value ?: User("1", "user@example.com", "Usuario"),
            onEditClick = { navController.navigate(Screen.EditProfile.route) },
            onLogout = {
              isAuthenticated.value = false
              currentUser.value = null
              navController.navigate(Screen.Auth.createRoute("login")) {
                popUpTo(Screen.Home.route) { inclusive = true }
              }
            },
            onNavigateBack = { navController.navigateUp() }
          )
        }
      }

      composable(
        route = Screen.PlaceList.route,
        arguments = listOf(navArgument("category") { type = NavType.StringType })
      ) {
        val category = it.arguments?.getString("category")?.let { PlaceCategory.valueOf(it) }
          ?: PlaceCategory.ACTIVITY
        PlaceListScreen(
          category = category,
          places = samplePlaces.filter { p -> p.category == category },
          onBackClick = { navController.navigateUp() },
          onFilterClick = { navController.navigate(Screen.Filters.route) },
          onPlaceClick = { navController.navigate(Screen.PlaceDetail.createRoute(it.id)) }
        )
      }

      composable(
        route = Screen.PlaceDetail.route,
        arguments = listOf(navArgument("placeId") { type = NavType.StringType })
      ) { backStackEntry ->
        val place = samplePlaces.find { it.id == (backStackEntry.arguments?.getString("placeId") ?: "1") }
          ?: samplePlaces[0]
        PlaceDetailScreen(
          place = PlaceDetail(
            id = place.id,
            name = place.name,
            imageUrl = place.imageUrl,
            rating = place.rating,
            location = place.location,
            description = "Un excelente lugar con servicios de calidad.",
            amenities = listOf(
              Amenity("1", "WiFi", Icons.Filled.Wifi),
              Amenity("2", "Piscina", Icons.Filled.Pool),
              Amenity("3", "Seguridad", Icons.Filled.CheckCircle)
            ),
            contact = ContactInfo("+57 (1) 1234-5678", hours = "Lun-Dom: 12-10 PM"),
            reviews = listOf(
              Review("1", "Juan", 4.8f, "Excelente", "Hace 1 semana", "")
            ),
            isFavorite = false
          ),
          onBackClick = { navController.navigateUp() },
          onShareClick = { },
          onToggleFavorite = { },
          onGetDirections = { },
          onCall = { }
        )
      }

      composable(Screen.Filters.route) {
        FilterSheet(
          onApplyFilters = { _, _, _ -> },
          onDismiss = { navController.navigateUp() }
        )
      }

      composable(Screen.EditProfile.route) {
        ProfileScreen(
          user = currentUser.value ?: User("1", "user@example.com", "Usuario"),
          onEditClick = { navController.navigateUp() },
          onLogout = { isAuthenticated.value = false },
          onNavigateBack = { navController.navigateUp() }
        )
      }
  }
}

@Composable
private fun RinconVerdeScaffold(
  navController: NavHostController,
  currentRoute: String,
  content: @Composable (PaddingValues) -> Unit
) {
  Scaffold(
    bottomBar = {
      RinconVerdeBottomBar(
        currentRoute = currentRoute,
        onNavigate = { route ->
          navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) { saveState = true }
            launchSingleTop = true
            restoreState = true
          }
        }
      )
    }
  ) { padding ->
    content(padding)
  }
}
