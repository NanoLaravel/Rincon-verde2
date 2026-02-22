package com.example.rincon_verde2.ui.navigation

sealed class Screen(val route: String) {
  data object Splash : Screen("splash")
  data object Onboarding : Screen("onboarding")
  data object Auth : Screen("auth/{mode}") {
    fun createRoute(mode: String = "login") = "auth/$mode"
  }
  data object Home : Screen("home")
  data object Search : Screen("search/{filterTab}?autoOpen={autoOpen}") {
    fun createRoute(filterTab: Int = 0, autoOpen: Boolean = false) = "search/$filterTab?autoOpen=$autoOpen"
  }
  data object PlaceList : Screen("placeList/{category}") {
    fun createRoute(category: String) = "placeList/$category"
  }
  data object PlaceDetail : Screen("placeDetail/{placeId}") {
    fun createRoute(placeId: String) = "placeDetail/$placeId"
  }
  data object Filters : Screen("filters")
  data object Favorites : Screen("favorites")
  data object Profile : Screen("profile")
  data object EditProfile : Screen("editProfile")
}
