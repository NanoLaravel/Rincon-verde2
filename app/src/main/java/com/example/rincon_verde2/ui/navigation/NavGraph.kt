package com.example.rincon_verde2.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rincon_verde2.domain.model.User
import com.example.rincon_verde2.ui.components.RinconVerdeBottomBar
import com.example.rincon_verde2.ui.feature.auth.AuthScreen
// FilterSheet removed: use Search's FilterBottomSheet instead
import com.example.rincon_verde2.domain.model.Event
import com.example.rincon_verde2.ui.feature.home.HomeScreen
import com.example.rincon_verde2.ui.feature.home.HomeViewModel
import com.example.rincon_verde2.ui.feature.placelist.PlaceListViewModel
import com.example.rincon_verde2.domain.model.Place
import com.example.rincon_verde2.domain.model.PlaceCategory
import com.example.rincon_verde2.ui.feature.placelist.PlaceListScreen
import com.example.rincon_verde2.ui.feature.placedetail.PlaceDetailScreen
import com.example.rincon_verde2.ui.feature.placedetail.Amenity
import com.example.rincon_verde2.ui.feature.placedetail.ContactInfo
import com.example.rincon_verde2.ui.feature.placedetail.PlaceDetail
import com.example.rincon_verde2.ui.feature.placedetail.PlaceDetailViewModel
import com.example.rincon_verde2.ui.feature.placedetail.PlaceDetailUiState
import com.example.rincon_verde2.ui.feature.placedetail.Review
import com.example.rincon_verde2.ui.feature.productdetail.ProductDetailScreen
import com.example.rincon_verde2.ui.feature.productdetail.ProductDetailViewModel
import com.example.rincon_verde2.ui.feature.productdetail.ProductDetailUiState
import com.example.rincon_verde2.ui.feature.productlist.ProductListScreen
import com.example.rincon_verde2.ui.feature.productlist.ProductListViewModel
import com.example.rincon_verde2.ui.feature.eventdetail.EventDetailScreen
import com.example.rincon_verde2.ui.feature.eventdetail.EventDetailViewModel
import com.example.rincon_verde2.ui.feature.eventdetail.EventDetailUiState
import com.example.rincon_verde2.ui.feature.profile.ProfileScreen
import com.example.rincon_verde2.ui.feature.search.SearchScreen
import com.example.rincon_verde2.ui.feature.splash.SplashScreen
import com.example.rincon_verde2.ui.feature.onboarding.OnboardingScreen
import com.example.rincon_verde2.ui.feature.onboarding.getDefaultOnboardingPages

@Composable
fun RinconVerdeNavGraph(navController: NavHostController) {
  // El estado de autenticación ahora se maneja en SplashViewModel y AuthViewModel
  // No necesitamos mantener isAuthenticated y currentUser aquí
  
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
      SplashScreen(
        onNavigateToOnboarding = {
          navController.navigate(Screen.Onboarding.route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
          }
        },
        onNavigateToHome = {
          navController.navigate(Screen.Home.route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
          }
        },
        onNavigateToAuth = {
          navController.navigate(Screen.Auth.createRoute("login")) {
            popUpTo(Screen.Splash.route) { inclusive = true }
          }
        }
      )
    }

    composable(Screen.Onboarding.route) {
      OnboardingScreen(
        onboardingPages = getDefaultOnboardingPages(),
        onComplete = {
          navController.navigate(Screen.Auth.createRoute("login")) {
            popUpTo(Screen.Onboarding.route) { inclusive = true }
          }
        }
      )
    }

    composable(
      route = Screen.Auth.route,
      arguments = listOf(navArgument("mode") { type = NavType.StringType })
    ) {
      AuthScreen(
        mode = "login",
        onLoginSuccess = { _ ->
          // El usuario ya está guardado en UserRepository por AuthViewModel
          // Solo navegamos a Home
          navController.navigate(Screen.Home.route) {
            popUpTo(Screen.Auth.route) { inclusive = true }
          }
        },
        onSignUpSuccess = { _, _ ->
          // El usuario ya está guardado en UserRepository por AuthViewModel
          // Solo navegamos a Home
          navController.navigate(Screen.Home.route) {
            popUpTo(Screen.Auth.route) { inclusive = true }
          }
        }
      )
    }

    composable(Screen.Home.route) {
        val bottomBarVisible = remember { mutableStateOf(true) }
        RinconVerdeScaffold(
          navController = navController, 
          currentRoute = Screen.Home.route,
          bottomBarVisible = bottomBarVisible.value
        ) {
          val viewModel: HomeViewModel = hiltViewModel()
          val uiState = viewModel.uiState.collectAsState()
          
          LaunchedEffect(Unit) {
            viewModel.loadPlaces()
          }
          
          HomeScreen(
            places = uiState.value.places,
            events = uiState.value.events,
            products = uiState.value.products,
            isLoading = uiState.value.isLoading,
            favorites = uiState.value.favorites,
            onPlaceClick = { navController.navigate(Screen.PlaceDetail.createRoute(it.id)) },
            onProductClick = { navController.navigate(Screen.ProductDetail.createRoute(it.id)) },
            onViewAllProductsClick = { navController.navigate(Screen.ProductList.route) },
            onEventClick = { navController.navigate(Screen.EventDetail.createRoute(it.id)) },
            onCategoryClick = { navController.navigate(Screen.PlaceList.createRoute(it.name)) },
            onToggleFavorite = { viewModel.toggleFavorite(it) },
            onFilterClick = { navController.navigate(Screen.Search.createRoute(0, autoOpen = false)) },
            onNavigate = { navController.navigate(it) },
            onBottomBarVisibilityChange = { visible -> bottomBarVisible.value = visible }
          )
        }
      }

      composable(Screen.ProductList.route) {
        val bottomBarVisible = remember { mutableStateOf(true) }
        RinconVerdeScaffold(
          navController = navController, 
          currentRoute = Screen.ProductList.route,
          bottomBarVisible = bottomBarVisible.value
        ) {
          val viewModel: ProductListViewModel = hiltViewModel()
          val uiState = viewModel.uiState.collectAsState()
          
          LaunchedEffect(Unit) {
            viewModel.loadProducts()
          }
          
          ProductListScreen(
            products = uiState.value.products,
            isLoading = uiState.value.isLoading,
            onBackClick = { navController.navigateUp() },
            onProductClick = { navController.navigate(Screen.ProductDetail.createRoute(it.id)) }
          )
        }
      }

      composable(
        route = Screen.Search.route,
        arguments = listOf(
          navArgument("filterTab") { type = NavType.IntType; defaultValue = 0 },
          navArgument("autoOpen") { type = NavType.BoolType; defaultValue = false }
        )
      ) { backStackEntry ->
        val filterTab = backStackEntry.arguments?.getInt("filterTab") ?: 0
        val autoOpen = backStackEntry.arguments?.getBoolean("autoOpen") ?: false
        val bottomBarVisible = remember { mutableStateOf(true) }
        RinconVerdeScaffold(
          navController = navController, 
          currentRoute = Screen.Search.route,
          bottomBarVisible = bottomBarVisible.value
        ) {
          SearchScreen(
            initialFilterTab = filterTab,
            autoOpenFilters = autoOpen,
            onPlaceClick = { placeId ->
              navController.navigate("${Screen.PlaceDetail.route}/$placeId")
            },
            onBottomBarVisibilityChange = { visible -> bottomBarVisible.value = visible }
          )
        }
      }

      composable(Screen.Favorites.route) {
        val bottomBarVisible = remember { mutableStateOf(true) }
        RinconVerdeScaffold(
          navController = navController, 
          currentRoute = Screen.Favorites.route,
          bottomBarVisible = bottomBarVisible.value
        ) {
          val viewModel: PlaceListViewModel = hiltViewModel()
          val uiState = viewModel.uiState.collectAsState()
          
          LaunchedEffect(Unit) {
            viewModel.loadPlaces()
          }
          
          PlaceListScreen(
            category = PlaceCategory.FAVORITES,
            places = uiState.value.places.filter { it.rating >= 4.5 },
            onBackClick = { navController.navigateUp() },
            onFilterClick = { navController.navigate(Screen.Search.createRoute(0, autoOpen = false)) },
            onPlaceClick = { navController.navigate(Screen.PlaceDetail.createRoute(it.id)) },
            onBottomBarVisibilityChange = { visible -> bottomBarVisible.value = visible },
            showTopBar = false
          )
        }
      }

      composable(Screen.Profile.route) {
        val bottomBarVisible = remember { mutableStateOf(true) }
        RinconVerdeScaffold(
          navController = navController, 
          currentRoute = Screen.Profile.route,
          bottomBarVisible = bottomBarVisible.value
        ) {
          ProfileScreen(
            user = User("1", "user@example.com", "Usuario"),
            onEditClick = { navController.navigate(Screen.EditProfile.route) },
            onLogout = {
              // ProfileViewModel ya llama a userRepository.logout()
              // Solo navegamos a Auth
              navController.navigate(Screen.Auth.createRoute("login")) {
                popUpTo(Screen.Home.route) { inclusive = true }
              }
            },
            onNavigateBack = { navController.navigateUp() },
            onBottomBarVisibilityChange = { visible -> bottomBarVisible.value = visible }
          )
        }
      }
      composable(
        route = Screen.PlaceList.route,
        arguments = listOf(navArgument("category") { type = NavType.StringType })
      ) {
        val category = it.arguments?.getString("category")?.let { PlaceCategory.valueOf(it) }
          ?: PlaceCategory.ACTIVITY
        val viewModel: PlaceListViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsState()
        val bottomBarVisible = remember { mutableStateOf(true) }
        
        LaunchedEffect(category) {
          viewModel.loadPlacesByCategory(category)
        }
        
        RinconVerdeScaffold(
          navController = navController,
          currentRoute = Screen.PlaceList.route,
          bottomBarVisible = bottomBarVisible.value
        ) {
          PlaceListScreen(
            category = category,
            places = uiState.value.places,
            onBackClick = { navController.navigateUp() },
            onFilterClick = { 
              val filterTab = when(category) {
                PlaceCategory.EAT -> 0
                PlaceCategory.STAY -> 1
                PlaceCategory.ACTIVITY -> 2
                else -> 0
              }
              navController.navigate(Screen.Search.createRoute(filterTab, autoOpen = true))
            },
            onPlaceClick = { navController.navigate(Screen.PlaceDetail.createRoute(it.id)) },
            onBottomBarVisibilityChange = { visible -> bottomBarVisible.value = visible },
            showTopBar = false
          )
        }
      }

      composable(
        route = Screen.PlaceDetail.route,
        arguments = listOf(navArgument("placeId") { type = NavType.StringType })
      ) { backStackEntry ->
        val viewModel: PlaceDetailViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsState()
        val placeId = backStackEntry.arguments?.getString("placeId") ?: "1"
        
        LaunchedEffect(placeId) {
          viewModel.loadPlaceDetail(placeId)
        }
        
        when (val state = uiState.value) {
          is PlaceDetailUiState.Success -> {
            PlaceDetailScreen(
              place = state.place,
              onBackClick = { navController.navigateUp() },
              onShareClick = { },
              onToggleFavorite = { viewModel.toggleFavorite() },
              onGetDirections = { },
              onCall = { }
            )
          }
          is PlaceDetailUiState.Error -> {
            // Mostrar error
            PlaceDetailScreen(
              place = PlaceDetail(
                id = "1",
                name = "Error",
                imageUrl = "",
                rating = 0f,
                location = "",
                description = state.message,
                amenities = emptyList(),
                contact = ContactInfo("", hours = ""),
                reviews = emptyList()
              ),
              onBackClick = { navController.navigateUp() },
              onShareClick = { },
              onToggleFavorite = { },
              onGetDirections = { },
              onCall = { }
            )
          }
          is PlaceDetailUiState.Loading -> {
            // Mostrar loading - por ahora mostramos la pantalla vacía
            Box(modifier = Modifier.fillMaxSize())
          }
        }
      }

      composable(
        route = Screen.ProductDetail.route,
        arguments = listOf(navArgument("productId") { type = NavType.StringType })
      ) { backStackEntry ->
        val viewModel: ProductDetailViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsState()
        val productId = backStackEntry.arguments?.getString("productId") ?: ""
        
        LaunchedEffect(productId) {
          viewModel.loadProductDetail(productId)
        }
        
        when (val state = uiState.value) {
          is ProductDetailUiState.Success -> {
            ProductDetailScreen(
              product = state.product,
              onBackClick = { navController.navigateUp() }
            )
          }
          is ProductDetailUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
              Text(text = state.message, modifier = Modifier.align(Alignment.Center))
            }
          }
          is ProductDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize())
          }
        }
      }

      composable(
        route = Screen.EventDetail.route,
        arguments = listOf(navArgument("eventId") { type = NavType.StringType })
      ) { backStackEntry ->
        val viewModel: EventDetailViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsState()
        val eventId = backStackEntry.arguments?.getString("eventId") ?: ""
        
        LaunchedEffect(eventId) {
          viewModel.loadEventDetail(eventId)
        }
        
        when (val state = uiState.value) {
          is EventDetailUiState.Success -> {
            EventDetailScreen(
              event = state.event,
              onBackClick = { navController.navigateUp() }
            )
          }
          is EventDetailUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
              Text(text = state.message, modifier = Modifier.align(Alignment.Center))
            }
          }
          is EventDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize())
          }
        }
      }

      // Filters screen removed — use SearchScreen's FilterBottomSheet instead

      composable(Screen.EditProfile.route) {
        ProfileScreen(
          user = User("1", "user@example.com", "Usuario"),
          onEditClick = { navController.navigateUp() },
          onLogout = {
            // ProfileViewModel maneja el logout
            navController.navigate(Screen.Auth.createRoute("login")) {
              popUpTo(Screen.Home.route) { inclusive = true }
            }
          },
          onNavigateBack = { navController.navigateUp() }
        )
      }
  }
}

@Composable
private fun RinconVerdeScaffold(
  navController: NavHostController,
  currentRoute: String,
  bottomBarVisible: Boolean = true,
  content: @Composable (PaddingValues) -> Unit
) {
  Scaffold(
    bottomBar = {
      AnimatedVisibility(
        visible = bottomBarVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
      ) {
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
    }
  ) { padding ->
    content(padding)
  }
}
