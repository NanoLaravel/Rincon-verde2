package com.example.rincon_verde2.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.rincon_verde2.R

/**
 * Rincón Verde Typography System
 * 
 * A comprehensive typographic scale following Material Design 3 guidelines
 * with custom adjustments for the Rincón Verde brand.
 * 
 * Usage:
 * - Display: Hero headlines, splash screens
 * - Headline: Screen titles, major section headers
 * - Title: Card titles, list item titles
 * - Body: Main content, descriptions
 * - Label: Buttons, tags, small UI elements
 */

// ============================================================
// FONT FAMILIES
// ============================================================

/**
 * Primary font family for the app
 * Falls back to system default if custom fonts are not available
 * 
 * To add custom fonts:
 * 1. Place font files in res/font/
 * 2. Uncomment the lines below
 */
val AppFontFamily = FontFamily.Default

// Example for custom fonts:
// val AppFontFamily = FontFamily(
//     Font(R.font.poppins_regular, FontWeight.Normal),
//     Font(R.font.poppins_medium, FontWeight.Medium),
//     Font(R.font.poppins_semibold, FontWeight.SemiBold),
//     Font(R.font.poppins_bold, FontWeight.Bold)
// )

// ============================================================
// TYPOGRAPHY SCALE
// ============================================================

/**
 * Main typography configuration for Rincón Verde
 */
val RinconVerdeTypography = Typography(
    // ============================================================
    // DISPLAY - Hero headlines, splash screens
    // ============================================================
    displayLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.5).sp
    ),
    displayMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.25).sp
    ),
    displaySmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    
    // ============================================================
    // HEADLINE - Screen titles, major section headers
    // ============================================================
    headlineLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    
    // ============================================================
    // TITLE - Card titles, list item titles
    // ============================================================
    titleLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    titleSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.1.sp
    ),
    
    // ============================================================
    // BODY - Main content, descriptions
    // ============================================================
    bodyLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    
    // ============================================================
    // LABEL - Buttons, tags, small UI elements
    // ============================================================
    labelLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    )
)

// ============================================================
// CUSTOM TEXT STYLES (App-specific)
// ============================================================

/**
 * Custom text styles for specific use cases
 */
object AppTextStyles {
    /** Hero text for splash/welcome screens */
    val Hero = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = (-0.5).sp
    )
    
    /** Section header with emphasis */
    val SectionHeader = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    )
    
    /** Card title with emphasis */
    val CardTitle = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
    
    /** Caption for subtle text */
    val Caption = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    )
    
    /** Overline for category labels */
    val Overline = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 12.sp,
        letterSpacing = 1.5.sp
    )
    
    /** Button text */
    val Button = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    )
    
    /** Input field text */
    val Input = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    
    /** Rating badge text */
    val RatingBadge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.sp
    )
    
    /** Tab text */
    val Tab = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
}

// ============================================================
// LEGACY SUPPORT (Deprecated - Use RinconVerdeTypography instead)
// ============================================================

@Deprecated(
    message = "Use RinconVerdeTypography instead",
    replaceWith = ReplaceWith("RinconVerdeTypography")
)
val Typography = RinconVerdeTypography
