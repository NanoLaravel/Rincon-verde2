package com.example.rincon_verde2.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// ============================================================
// LIGHT COLOR SCHEME
// ============================================================

private val LightColorScheme = lightColorScheme(
    // Primary colors
    primary = GreenPrimary,
    onPrimary = Color.White,
    primaryContainer = GreenSurface,
    onPrimaryContainer = GreenPrimaryDark,
    
    // Secondary colors
    secondary = EarthSecondary,
    onSecondary = Color.White,
    secondaryContainer = EarthLight,
    onSecondaryContainer = EarthDark,
    
    // Tertiary colors (accent)
    tertiary = OrangeAccent,
    onTertiary = Color.White,
    tertiaryContainer = OrangeAccentLight,
    onTertiaryContainer = Color(0xFFBF360C),
    
    // Error colors
    error = Error,
    onError = Color.White,
    errorContainer = ErrorLight,
    onErrorContainer = Color(0xFFB3261E),
    
    // Background colors
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    
    // Surface colors
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    
    // Outline colors
    outline = OutlineLight,
    outlineVariant = OutlineVariantLight,
    
    // Inverse colors
    inverseSurface = OnBackgroundLight,
    inverseOnSurface = BackgroundLight,
    inversePrimary = GreenPrimaryLight,
    
    // Scrim
    scrim = Scrim
)

// ============================================================
// DARK COLOR SCHEME
// ============================================================

private val DarkColorScheme = darkColorScheme(
    // Primary colors
    primary = GreenPrimaryLight,
    onPrimary = GreenPrimaryDark,
    primaryContainer = GreenPrimaryDark,
    onPrimaryContainer = GreenSurface,
    
    // Secondary colors
    secondary = EarthLight,
    onSecondary = EarthDark,
    secondaryContainer = EarthDark,
    onSecondaryContainer = EarthLight,
    
    // Tertiary colors (accent)
    tertiary = OrangeAccent,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFBF360C),
    onTertiaryContainer = OrangeAccentLight,
    
    // Error colors
    error = Color(0xFFCF6679),
    onError = Color.Black,
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC),
    
    // Background colors
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    
    // Surface colors
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    
    // Outline colors
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    
    // Inverse colors
    inverseSurface = BackgroundLight,
    inverseOnSurface = OnBackgroundLight,
    inversePrimary = GreenPrimary,
    
    // Scrim
    scrim = Scrim
)

// ============================================================
// THEME COMPOSABLE
// ============================================================

/**
 * Rincón Verde Theme
 * 
 * The main theme composable for the entire application.
 * Wraps Material3 theme with custom color scheme, typography, and shapes.
 * 
 * @param darkTheme Whether to use dark theme (defaults to system setting)
 * @param dynamicColor Whether to use dynamic colors on Android 12+ (defaults to false for brand consistency)
 * @param content The content to be themed
 */
@Composable
fun RinconVerdeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is disabled by default to maintain brand identity
    // Set to true if you want Material You dynamic colors on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Dynamic colors on Android 12+ (optional)
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        // Dark theme
        darkTheme -> DarkColorScheme
        // Light theme (default)
        else -> LightColorScheme
    }
    
    // Configure status bar and navigation bar
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            
            // Set status bar content color (light/dark icons)
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = RinconVerdeTypography,
        shapes = RinconVerdeShapes,
        content = content
    )
}

// ============================================================
// THEME PREVIEW HELPERS
// ============================================================

/**
 * Theme preview wrapper for light theme
 * Use this in @Preview composables to preview light theme
 */
@Composable
fun RinconVerdeLightThemePreview(
    content: @Composable () -> Unit
) {
    RinconVerdeTheme(darkTheme = false, content = content)
}

/**
 * Theme preview wrapper for dark theme
 * Use this in @Preview composables to preview dark theme
 */
@Composable
fun RinconVerdeDarkThemePreview(
    content: @Composable () -> Unit
) {
    RinconVerdeTheme(darkTheme = true, content = content)
}

// ============================================================
// LEGACY SUPPORT (Deprecated)
// ============================================================

@Deprecated(
    message = "Use RinconVerdeTheme instead",
    replaceWith = ReplaceWith("RinconVerdeTheme(darkTheme, dynamicColor, content)")
)
@Composable
fun Rinconverde2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    RinconVerdeTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}
