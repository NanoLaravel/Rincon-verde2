package com.example.rincon_verde2.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Rincón Verde Color Palette
 * 
 * A nature-inspired design system reflecting the green corner brand identity.
 * Colors are organized into semantic groups for consistent usage.
 */

// ============================================================
// PRIMARY COLORS - Green (Nature, Growth, Main Brand)
// ============================================================

/** Main brand color - Use for primary actions, key UI elements */
val GreenPrimary = Color(0xFF2E7D32)

/** Light variant - Use for hover states, subtle backgrounds */
val GreenPrimaryLight = Color(0xFF4CAF50)

/** Dark variant - Use for pressed states, status bar */
val GreenPrimaryDark = Color(0xFF1B5E20)

/** Surface tint - Use for subtle green backgrounds */
val GreenSurface = Color(0xFFE8F5E9)

/** Very light green - Use for chip backgrounds, tags */
val GreenSurfaceLight = Color(0xFFF1F8E9)

// ============================================================
// SECONDARY COLORS - Earth/Natural (Warmth, Grounding)
// ============================================================

/** Secondary brand color - Use for secondary actions */
val EarthSecondary = Color(0xFF8D6E63)

/** Light variant - Use for subtle earth-tone backgrounds */
val EarthLight = Color(0xFFD7CCC8)

/** Dark variant - Use for text on light backgrounds */
val EarthDark = Color(0xFF5D4037)

// ============================================================
// ACCENT COLORS - Activity/Highlight
// ============================================================

/** Orange accent - Use for activities, events, CTAs */
val OrangeAccent = Color(0xFFFF7043)

/** Orange light - Use for activity backgrounds */
val OrangeAccentLight = Color(0xFFFFCCBC)

/** Blue accent - Use for stay/accommodation category */
val BlueAccent = Color(0xFF29B6F6)

/** Blue light - Use for stay backgrounds */
val BlueAccentLight = Color(0xFFB3E5FC)

/** Red accent - Use for favorites, alerts, important actions */
val RedAccent = Color(0xFFEF5350)

/** Red light - Use for favorite backgrounds */
val RedAccentLight = Color(0xFFFFCDD2)

/** Yellow accent - Use for ratings, stars, highlights */
val YellowAccent = Color(0xFFFFC107)

/** Yellow light - Use for rating backgrounds */
val YellowAccentLight = Color(0xFFFFECB3)

// ============================================================
// SEMANTIC COLORS - Status & Feedback
// ============================================================

/** Success - Use for positive feedback, confirmations */
val Success = Color(0xFF4CAF50)

/** Success light - Use for success backgrounds */
val SuccessLight = Color(0xFFC8E6C9)

/** Warning - Use for caution states */
val Warning = Color(0xFFFF9800)

/** Warning light - Use for warning backgrounds */
val WarningLight = Color(0xFFFFE0B2)

/** Error - Use for error states, destructive actions */
val Error = Color(0xFFF44336)

/** Error light - Use for error backgrounds */
val ErrorLight = Color(0xFFFFCDD2)

/** Info - Use for informational states */
val Info = Color(0xFF2196F3)

/** Info light - Use for info backgrounds */
val InfoLight = Color(0xFFBBDEFB)

// ============================================================
// NEUTRAL COLORS - Light Theme
// ============================================================

/** Main background color */
val BackgroundLight = Color(0xFFFAFAFA)

/** Card/surface background */
val SurfaceLight = Color(0xFFFFFFFF)

/** Secondary surface (elevated cards, sheets) */
val SurfaceVariantLight = Color(0xFFF5F5F5)

/** Tertiary surface (input backgrounds) */
val SurfaceTertiaryLight = Color(0xFFEEEEEE)

/** Primary text on background */
val OnBackgroundLight = Color(0xFF212121)

/** Primary text on surface */
val OnSurfaceLight = Color(0xFF424242)

/** Secondary text (captions, hints) */
val OnSurfaceVariantLight = Color(0xFF757575)

/** Tertiary text (disabled, placeholders) */
val OnSurfaceTertiaryLight = Color(0xFF9E9E9E)

/** Borders and dividers */
val OutlineLight = Color(0xFFBDBDBD)

/** Subtle borders */
val OutlineVariantLight = Color(0xFFE0E0E0)

// ============================================================
// NEUTRAL COLORS - Dark Theme
// ============================================================

/** Main background color (dark) */
val BackgroundDark = Color(0xFF121212)

/** Card/surface background (dark) */
val SurfaceDark = Color(0xFF1E1E1E)

/** Secondary surface (dark) */
val SurfaceVariantDark = Color(0xFF2C2C2C)

/** Tertiary surface (dark) */
val SurfaceTertiaryDark = Color(0xFF383838)

/** Primary text on background (dark) */
val OnBackgroundDark = Color(0xFFECECEC)

/** Primary text on surface (dark) */
val OnSurfaceDark = Color(0xFFE0E0E0)

/** Secondary text (dark) */
val OnSurfaceVariantDark = Color(0xFFB0B0B0)

/** Tertiary text (dark) */
val OnSurfaceTertiaryDark = Color(0xFF808080)

/** Borders and dividers (dark) */
val OutlineDark = Color(0xFF424242)

/** Subtle borders (dark) */
val OutlineVariantDark = Color(0xFF333333)

// ============================================================
// OVERLAY COLORS
// ============================================================

/** Semi-transparent black for image overlays */
val OverlayDark = Color(0xFF000000)

/** Semi-transparent white for light overlays */
val OverlayLight = Color(0xFFFFFFFF)

/** Scrim for modals and dialogs */
val Scrim = Color(0xFF000000)

// ============================================================
// CATEGORY COLORS (Pre-configured for consistent usage)
// ============================================================

/**
 * Category color configuration
 * Use these for consistent category styling across the app
 */
object CategoryColors {
    /** Activity category - Orange */
    val Activity = OrangeAccent
    val ActivityBackground = OrangeAccentLight
    
    /** Eat category - Green */
    val Eat = GreenPrimary
    val EatBackground = GreenSurface
    
    /** Stay category - Blue */
    val Stay = BlueAccent
    val StayBackground = BlueAccentLight
    
    /** Favorites - Red */
    val Favorites = RedAccent
    val FavoritesBackground = RedAccentLight
}

// ============================================================
// LEGACY COLORS (For backward compatibility - Deprecated)
// ============================================================

@Deprecated("Use GreenPrimary instead", ReplaceWith("GreenPrimary"))
val Purple80 = GreenPrimaryLight

@Deprecated("Use EarthLight instead", ReplaceWith("EarthLight"))
val PurpleGrey80 = EarthLight

@Deprecated("Use OrangeAccent instead", ReplaceWith("OrangeAccent"))
val Pink80 = OrangeAccent

@Deprecated("Use GreenPrimaryDark instead", ReplaceWith("GreenPrimaryDark"))
val Purple40 = GreenPrimary

@Deprecated("Use EarthSecondary instead", ReplaceWith("EarthSecondary"))
val PurpleGrey40 = EarthSecondary

@Deprecated("Use RedAccent instead", ReplaceWith("RedAccent"))
val Pink40 = RedAccent
