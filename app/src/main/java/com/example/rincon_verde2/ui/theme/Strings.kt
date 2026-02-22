package com.example.rincon_verde2.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.rincon_verde2.R

/**
 * Rincón Verde String Resources
 * 
 * Type-safe access to all string resources for use in Compose.
 * This provides compile-time safety and autocomplete support.
 * 
 * Usage:
 * ```kotlin
 * Text(text = Strings.authLogin)
 * Text(text = Strings.searchResultsFor(query))
 * ```
 */

object Strings {
    
    // ============================================================
    // APP GENERAL
    // ============================================================
    
    val appName: String
        @Composable get() = stringResource(R.string.app_name)
    
    // ============================================================
    // AUTHENTICATION
    // ============================================================
    
    val authAppTitle: String
        @Composable get() = stringResource(R.string.auth_app_title)
    
    val authWelcomeBack: String
        @Composable get() = stringResource(R.string.auth_welcome_back)
    
    val authJoinCommunity: String
        @Composable get() = stringResource(R.string.auth_join_community)
    
    val authEmail: String
        @Composable get() = stringResource(R.string.auth_email)
    
    val authPassword: String
        @Composable get() = stringResource(R.string.auth_password)
    
    val authConfirmPassword: String
        @Composable get() = stringResource(R.string.auth_confirm_password)
    
    val authFullName: String
        @Composable get() = stringResource(R.string.auth_full_name)
    
    val authLogin: String
        @Composable get() = stringResource(R.string.auth_login)
    
    val authSignUp: String
        @Composable get() = stringResource(R.string.auth_sign_up)
    
    val authNoAccount: String
        @Composable get() = stringResource(R.string.auth_no_account)
    
    val authCreateAccount: String
        @Composable get() = stringResource(R.string.auth_create_account)
    
    val authHaveAccount: String
        @Composable get() = stringResource(R.string.auth_have_account)
    
    val authLoginPrompt: String
        @Composable get() = stringResource(R.string.auth_login_prompt)
    
    // ============================================================
    // HOME SCREEN
    // ============================================================
    
    val homeDiscover: String
        @Composable get() = stringResource(R.string.home_discover)
    
    val homeSubtitle: String
        @Composable get() = stringResource(R.string.home_subtitle)
    
    val homeSearch: String
        @Composable get() = stringResource(R.string.home_search)
    
    val homeWhatToDo: String
        @Composable get() = stringResource(R.string.home_what_to_do)
    
    val homeWhereToEat: String
        @Composable get() = stringResource(R.string.home_where_to_eat)
    
    val homeWhereToStay: String
        @Composable get() = stringResource(R.string.home_where_to_stay)
    
    val homeFavorites: String
        @Composable get() = stringResource(R.string.home_favorites)
    
    val homeTopRated: String
        @Composable get() = stringResource(R.string.home_top_rated)
    
    val homeViewAll: String
        @Composable get() = stringResource(R.string.home_view_all)
    
    val homeUpcomingEvents: String
        @Composable get() = stringResource(R.string.home_upcoming_events)
    
    val homeHeaderImage: String
        @Composable get() = stringResource(R.string.home_header_image)
    
    // ============================================================
    // SEARCH SCREEN
    // ============================================================
    
    val searchPlaceholder: String
        @Composable get() = stringResource(R.string.search_placeholder)
    
    val searchAllCategories: String
        @Composable get() = stringResource(R.string.search_all_categories)
    
    val searchFiltersActive: String
        @Composable get() = stringResource(R.string.search_filters_active)
    
    val searchClear: String
        @Composable get() = stringResource(R.string.search_clear)
    
    val searchRecent: String
        @Composable get() = stringResource(R.string.search_recent)
    
    val searchNoRecent: String
        @Composable get() = stringResource(R.string.search_no_recent)
    
    val searchNoResults: String
        @Composable get() = stringResource(R.string.search_no_results)
    
    val searchNoResultsMessage: String
        @Composable get() = stringResource(R.string.search_no_results_message)
    
    val searchClearFilters: String
        @Composable get() = stringResource(R.string.search_clear_filters)
    
    @Composable
    fun searchResultsFor(query: String): String = 
        stringResource(R.string.search_results_for, query)
    
    @Composable
    fun searchPlacesFound(count: Int): String {
        val plural = if (count != 1) stringResource(R.string.search_places_plural) else ""
        return stringResource(R.string.search_places_found, count, plural)
    }
    
    // ============================================================
    // FILTER BOTTOM SHEET
    // ============================================================
    
    val filterTitle: String
        @Composable get() = stringResource(R.string.filter_title)
    
    val filterCategory: String
        @Composable get() = stringResource(R.string.filter_category)
    
    val filterRating: String
        @Composable get() = stringResource(R.string.filter_rating)
    
    val filterPrice: String
        @Composable get() = stringResource(R.string.filter_price)
    
    val filterAmenities: String
        @Composable get() = stringResource(R.string.filter_amenities)
    
    val filterApply: String
        @Composable get() = stringResource(R.string.filter_apply)
    
    val filterClearAll: String
        @Composable get() = stringResource(R.string.filter_clear_all)
    
    val filterAny: String
        @Composable get() = stringResource(R.string.filter_any)
    
    val filterFree: String
        @Composable get() = stringResource(R.string.filter_free)
    
    val filterExpensive: String
        @Composable get() = stringResource(R.string.filter_expensive)
    
    // ============================================================
    // PROFILE SCREEN
    // ============================================================
    
    val profileFavorites: String
        @Composable get() = stringResource(R.string.profile_favorites)
    
    val profileReviews: String
        @Composable get() = stringResource(R.string.profile_reviews)
    
    val profileRating: String
        @Composable get() = stringResource(R.string.profile_rating)
    
    val profileEdit: String
        @Composable get() = stringResource(R.string.profile_edit)
    
    val profileTab: String
        @Composable get() = stringResource(R.string.profile_tab)
    
    val profileSettingsTab: String
        @Composable get() = stringResource(R.string.profile_settings_tab)
    
    val profileLogout: String
        @Composable get() = stringResource(R.string.profile_logout)
    
    val profileEmail: String
        @Composable get() = stringResource(R.string.profile_email)
    
    val profileLocation: String
        @Composable get() = stringResource(R.string.profile_location)
    
    val profileLocationValue: String
        @Composable get() = stringResource(R.string.profile_location_value)
    
    val profileMemberSince: String
        @Composable get() = stringResource(R.string.profile_member_since)
    
    val profileMemberSinceValue: String
        @Composable get() = stringResource(R.string.profile_member_since_value)
    
    val profileAbout: String
        @Composable get() = stringResource(R.string.profile_about)
    
    val profileAboutValue: String
        @Composable get() = stringResource(R.string.profile_about_value)
    
    val profileNotifications: String
        @Composable get() = stringResource(R.string.profile_notifications)
    
    val profileNotificationsSubtitle: String
        @Composable get() = stringResource(R.string.profile_notifications_subtitle)
    
    val profilePrivacy: String
        @Composable get() = stringResource(R.string.profile_privacy)
    
    val profilePrivacySubtitle: String
        @Composable get() = stringResource(R.string.profile_privacy_subtitle)
    
    val profileSyncFavorites: String
        @Composable get() = stringResource(R.string.profile_sync_favorites)
    
    val profileSyncFavoritesSubtitle: String
        @Composable get() = stringResource(R.string.profile_sync_favorites_subtitle)
    
    // ============================================================
    // PLACE DETAIL SCREEN
    // ============================================================
    
    val placeGetDirections: String
        @Composable get() = stringResource(R.string.place_get_directions)
    
    val placeCall: String
        @Composable get() = stringResource(R.string.place_call)
    
    val placeAmenities: String
        @Composable get() = stringResource(R.string.place_amenities)
    
    val placeContact: String
        @Composable get() = stringResource(R.string.place_contact)
    
    val placeReviews: String
        @Composable get() = stringResource(R.string.place_reviews)
    
    val placeDescription: String
        @Composable get() = stringResource(R.string.place_description)
    
    val placeShare: String
        @Composable get() = stringResource(R.string.place_share)
    
    val placeSave: String
        @Composable get() = stringResource(R.string.place_save)
    
    val placeWebsite: String
        @Composable get() = stringResource(R.string.place_website)
    
    val placeHours: String
        @Composable get() = stringResource(R.string.place_hours)
    
    val placePhone: String
        @Composable get() = stringResource(R.string.place_phone)
    
    val placeWriteReview: String
        @Composable get() = stringResource(R.string.place_write_review)
    
    // ============================================================
    // PLACE LIST SCREEN
    // ============================================================
    
    val placeListResults: String
        @Composable get() = stringResource(R.string.place_list_results)
    
    val placeListNoPlaces: String
        @Composable get() = stringResource(R.string.place_list_no_places)
    
    val placeListFilter: String
        @Composable get() = stringResource(R.string.place_list_filter)
    
    // ============================================================
    // FAVORITES SCREEN
    // ============================================================
    
    val favoritesTitle: String
        @Composable get() = stringResource(R.string.favorites_title)
    
    val favoritesEmpty: String
        @Composable get() = stringResource(R.string.favorites_empty)
    
    val favoritesEmptyMessage: String
        @Composable get() = stringResource(R.string.favorites_empty_message)
    
    val favoritesExplore: String
        @Composable get() = stringResource(R.string.favorites_explore)
    
    // ============================================================
    // CATEGORIES
    // ============================================================
    
    val categoryActivity: String
        @Composable get() = stringResource(R.string.category_activity)
    
    val categoryEat: String
        @Composable get() = stringResource(R.string.category_eat)
    
    val categoryStay: String
        @Composable get() = stringResource(R.string.category_stay)
    
    val categoryAll: String
        @Composable get() = stringResource(R.string.category_all)
    
    // ============================================================
    // GENERAL ACTIONS
    // ============================================================
    
    val actionShare: String
        @Composable get() = stringResource(R.string.action_share)
    
    val actionSave: String
        @Composable get() = stringResource(R.string.action_save)
    
    val actionCancel: String
        @Composable get() = stringResource(R.string.action_cancel)
    
    val actionConfirm: String
        @Composable get() = stringResource(R.string.action_confirm)
    
    val actionRetry: String
        @Composable get() = stringResource(R.string.action_retry)
    
    val actionClose: String
        @Composable get() = stringResource(R.string.action_close)
    
    val actionBack: String
        @Composable get() = stringResource(R.string.action_back)
    
    val actionNext: String
        @Composable get() = stringResource(R.string.action_next)
    
    val actionDone: String
        @Composable get() = stringResource(R.string.action_done)
    
    val actionApply: String
        @Composable get() = stringResource(R.string.action_apply)
    
    val actionClear: String
        @Composable get() = stringResource(R.string.action_clear)
    
    val actionEdit: String
        @Composable get() = stringResource(R.string.action_edit)
    
    val actionDelete: String
        @Composable get() = stringResource(R.string.action_delete)
    
    // ============================================================
    // CONTENT DESCRIPTIONS (Accessibility)
    // ============================================================
    
    val cdRating: String
        @Composable get() = stringResource(R.string.cd_rating)
    
    val cdLocation: String
        @Composable get() = stringResource(R.string.cd_location)
    
    val cdSearch: String
        @Composable get() = stringResource(R.string.cd_search)
    
    val cdFilter: String
        @Composable get() = stringResource(R.string.cd_filter)
    
    val cdClear: String
        @Composable get() = stringResource(R.string.cd_clear)
    
    val cdFavorite: String
        @Composable get() = stringResource(R.string.cd_favorite)
    
    val cdBack: String
        @Composable get() = stringResource(R.string.cd_back)
    
    val cdAvatar: String
        @Composable get() = stringResource(R.string.cd_avatar)
    
    val cdPlaceImage: String
        @Composable get() = stringResource(R.string.cd_place_image)
    
    val cdEventImage: String
        @Composable get() = stringResource(R.string.cd_event_image)
    
    val cdCategoryIcon: String
        @Composable get() = stringResource(R.string.cd_category_icon)
    
    val cdMenu: String
        @Composable get() = stringResource(R.string.cd_menu)
    
    val cdSettings: String
        @Composable get() = stringResource(R.string.cd_settings)
    
    val cdNotification: String
        @Composable get() = stringResource(R.string.cd_notification)
    
    val cdPassword: String
        @Composable get() = stringResource(R.string.cd_password)
    
    val cdEmail: String
        @Composable get() = stringResource(R.string.cd_email)
    
    val cdStar: String
        @Composable get() = stringResource(R.string.cd_star)
    
    val cdPrice: String
        @Composable get() = stringResource(R.string.cd_price)
    
    // ============================================================
    // ERROR MESSAGES
    // ============================================================
    
    val errorGeneric: String
        @Composable get() = stringResource(R.string.error_generic)
    
    val errorNetwork: String
        @Composable get() = stringResource(R.string.error_network)
    
    val errorLoading: String
        @Composable get() = stringResource(R.string.error_loading)
    
    val errorLogin: String
        @Composable get() = stringResource(R.string.error_login)
    
    val errorSignup: String
        @Composable get() = stringResource(R.string.error_signup)
    
    val errorInvalidEmail: String
        @Composable get() = stringResource(R.string.error_invalid_email)
    
    val errorInvalidPassword: String
        @Composable get() = stringResource(R.string.error_invalid_password)
    
    val errorPasswordMismatch: String
        @Composable get() = stringResource(R.string.error_password_mismatch)
    
    val errorEmptyField: String
        @Composable get() = stringResource(R.string.error_empty_field)
    
    // ============================================================
    // SUCCESS MESSAGES
    // ============================================================
    
    val successLogin: String
        @Composable get() = stringResource(R.string.success_login)
    
    val successSignup: String
        @Composable get() = stringResource(R.string.success_signup)
    
    val successFavoriteAdded: String
        @Composable get() = stringResource(R.string.success_favorite_added)
    
    val successFavoriteRemoved: String
        @Composable get() = stringResource(R.string.success_favorite_removed)
    
    val successProfileUpdated: String
        @Composable get() = stringResource(R.string.success_profile_updated)
    
    // ============================================================
    // LOADING STATES
    // ============================================================
    
    val loading: String
        @Composable get() = stringResource(R.string.loading)
    
    val loadingPlaces: String
        @Composable get() = stringResource(R.string.loading_places)
    
    val loadingEvents: String
        @Composable get() = stringResource(R.string.loading_events)
    
    // ============================================================
    // EMPTY STATES
    // ============================================================
    
    val emptyNoResults: String
        @Composable get() = stringResource(R.string.empty_no_results)
    
    val emptyNoFavorites: String
        @Composable get() = stringResource(R.string.empty_no_favorites)
    
    val emptyNoEvents: String
        @Composable get() = stringResource(R.string.empty_no_events)
    
    val emptyNoReviews: String
        @Composable get() = stringResource(R.string.empty_no_reviews)
    
    // ============================================================
    // PRICE RANGES
    // ============================================================
    
    val priceFree: String
        @Composable get() = stringResource(R.string.price_free)
    
    val priceLow: String
        @Composable get() = stringResource(R.string.price_low)
    
    val priceMedium: String
        @Composable get() = stringResource(R.string.price_medium)
    
    val priceHigh: String
        @Composable get() = stringResource(R.string.price_high)
    
    val priceExpensive: String
        @Composable get() = stringResource(R.string.price_expensive)
    
    // ============================================================
    // AMENITIES
    // ============================================================
    
    val amenityWifi: String
        @Composable get() = stringResource(R.string.amenity_wifi)
    
    val amenityParking: String
        @Composable get() = stringResource(R.string.amenity_parking)
    
    val amenityPool: String
        @Composable get() = stringResource(R.string.amenity_pool)
    
    val amenityAc: String
        @Composable get() = stringResource(R.string.amenity_ac)
    
    val amenityRestaurant: String
        @Composable get() = stringResource(R.string.amenity_restaurant)
    
    val amenityBar: String
        @Composable get() = stringResource(R.string.amenity_bar)
    
    val amenityGym: String
        @Composable get() = stringResource(R.string.amenity_gym)
    
    val amenitySpa: String
        @Composable get() = stringResource(R.string.amenity_spa)
    
    val amenityPets: String
        @Composable get() = stringResource(R.string.amenity_pets)
    
    val amenityKids: String
        @Composable get() = stringResource(R.string.amenity_kids)
    
    val amenityVegetarian: String
        @Composable get() = stringResource(R.string.amenity_vegetarian)
    
    val amenityVegan: String
        @Composable get() = stringResource(R.string.amenity_vegan)
    
    val amenityTerrace: String
        @Composable get() = stringResource(R.string.amenity_terrace)
    
    val amenityDelivery: String
        @Composable get() = stringResource(R.string.amenity_delivery)
    
    val amenityTakeaway: String
        @Composable get() = stringResource(R.string.amenity_takeaway)
    
    // ============================================================
    // SPLASH SCREEN
    // ============================================================
    
    val splashTagline: String
        @Composable get() = stringResource(R.string.splash_tagline)
    
    val splashLoading: String
        @Composable get() = stringResource(R.string.splash_loading)
    
    // ============================================================
    // ONBOARDING SCREEN
    // ============================================================
    
    val onboardingSkip: String
        @Composable get() = stringResource(R.string.onboarding_skip)
    
    val onboardingNext: String
        @Composable get() = stringResource(R.string.onboarding_next)
    
    val onboardingStart: String
        @Composable get() = stringResource(R.string.onboarding_start)
    
    val onboardingDiscoverTitle: String
        @Composable get() = stringResource(R.string.onboarding_discover_title)
    
    val onboardingDiscoverDescription: String
        @Composable get() = stringResource(R.string.onboarding_discover_description)
    
    val onboardingStayTitle: String
        @Composable get() = stringResource(R.string.onboarding_stay_title)
    
    val onboardingStayDescription: String
        @Composable get() = stringResource(R.string.onboarding_stay_description)
    
    val onboardingExploreTitle: String
        @Composable get() = stringResource(R.string.onboarding_explore_title)
    
    val onboardingExploreDescription: String
        @Composable get() = stringResource(R.string.onboarding_explore_description)
}
