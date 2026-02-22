package com.example.rincon_verde2.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Rincón Verde Shape System
 * 
 * A comprehensive set of shapes for consistent component styling
 * throughout the app. Based on Material Design 3 guidelines with
 * custom adjustments for the Rincón Verde brand.
 * 
 * Usage:
 * - ExtraSmall: Chips, small buttons
 * - Small: Cards, text fields
 * - Medium: Cards, dialogs
 * - Large: Bottom sheets, large cards
 * - ExtraLarge: Modal bottom sheets
 */

// ============================================================
// MATERIAL 3 SHAPES
// ============================================================

/**
 * Main shape configuration for Rincón Verde
 * Following Material 3 shape system
 */
val RinconVerdeShapes = Shapes(
    // Extra small - Chips, small buttons
    extraSmall = RoundedCornerShape(CornerRadius.radiusSm),
    
    // Small - Cards, text fields, FAB
    small = RoundedCornerShape(CornerRadius.radiusMd),
    
    // Medium - Cards, dialogs
    medium = RoundedCornerShape(CornerRadius.radiusLg),
    
    // Large - Bottom sheets, large cards
    large = RoundedCornerShape(CornerRadius.radiusXl),
    
    // Extra large - Modal bottom sheets
    extraLarge = RoundedCornerShape(CornerRadius.radiusXxl)
)

// ============================================================
// CUSTOM SHAPES
// ============================================================

/**
 * Custom shapes for specific use cases
 */
object AppShapes {
    // --------------------------------------------------------
    // BUTTON SHAPES
    // --------------------------------------------------------
    
    /** Standard button shape */
    val button = RoundedCornerShape(CornerRadius.radiusMd)
    
    /** Pill button shape (fully rounded) */
    val buttonPill = RoundedCornerShape(CornerRadius.full)
    
    /** Small button shape */
    val buttonSmall = RoundedCornerShape(CornerRadius.radiusSm)
    
    // --------------------------------------------------------
    // CARD SHAPES
    // --------------------------------------------------------
    
    /** Standard card shape */
    val card = RoundedCornerShape(CornerRadius.radiusLg)
    
    /** Small card shape */
    val cardSmall = RoundedCornerShape(CornerRadius.radiusMd)
    
    /** Large card shape */
    val cardLarge = RoundedCornerShape(CornerRadius.radiusXl)
    
    /** Card with top corners rounded only */
    val cardTopRounded = RoundedCornerShape(
        topStart = CornerRadius.radiusLg,
        topEnd = CornerRadius.radiusLg,
        bottomStart = CornerRadius.none,
        bottomEnd = CornerRadius.none
    )
    
    // --------------------------------------------------------
    // INPUT SHAPES
    // --------------------------------------------------------
    
    /** Standard input field shape */
    val input = RoundedCornerShape(CornerRadius.radiusMd)
    
    /** Search bar shape */
    val searchBar = RoundedCornerShape(CornerRadius.radiusLg)
    
    /** Pill input shape */
    val inputPill = RoundedCornerShape(CornerRadius.full)
    
    // --------------------------------------------------------
    // BADGE SHAPES
    // --------------------------------------------------------
    
    /** Rating badge shape */
    val ratingBadge = RoundedCornerShape(CornerRadius.radiusSm)
    
    /** Notification badge shape */
    val notificationBadge = RoundedCornerShape(CornerRadius.full)
    
    /** Tag/chip shape */
    val tag = RoundedCornerShape(CornerRadius.radiusMd)
    
    // --------------------------------------------------------
    // AVATAR SHAPES
    // --------------------------------------------------------
    
    /** Circular avatar */
    val avatarCircle = RoundedCornerShape(CornerRadius.full)
    
    /** Rounded square avatar */
    val avatarRounded = RoundedCornerShape(CornerRadius.radiusLg)
    
    // --------------------------------------------------------
    // BOTTOM SHEET SHAPES
    // --------------------------------------------------------
    
    /** Standard bottom sheet (top corners rounded) */
    val bottomSheet = RoundedCornerShape(
        topStart = CornerRadius.radiusXxl,
        topEnd = CornerRadius.radiusXxl,
        bottomStart = CornerRadius.none,
        bottomEnd = CornerRadius.none
    )
    
    /** Modal bottom sheet */
    val bottomSheetModal = RoundedCornerShape(
        topStart = CornerRadius.radiusXxxl,
        topEnd = CornerRadius.radiusXxxl,
        bottomStart = CornerRadius.none,
        bottomEnd = CornerRadius.none
    )
    
    // --------------------------------------------------------
    // DIALOG SHAPES
    // --------------------------------------------------------
    
    /** Standard dialog shape */
    val dialog = RoundedCornerShape(CornerRadius.radiusXl)
    
    /** Alert dialog shape */
    val alertDialog = RoundedCornerShape(CornerRadius.radiusLg)
    
    // --------------------------------------------------------
    // IMAGE SHAPES
    // --------------------------------------------------------
    
    /** Standard image shape */
    val image = RoundedCornerShape(CornerRadius.radiusMd)
    
    /** Large image shape */
    val imageLarge = RoundedCornerShape(CornerRadius.radiusLg)
    
    /** Hero image shape (bottom corners rounded) */
    val heroImage = RoundedCornerShape(
        topStart = CornerRadius.none,
        topEnd = CornerRadius.none,
        bottomStart = CornerRadius.radiusXl,
        bottomEnd = CornerRadius.radiusXl
    )
    
    // --------------------------------------------------------
    // CATEGORY CARD SHAPES
    // --------------------------------------------------------
    
    /** Category card shape */
    val categoryCard = RoundedCornerShape(CornerRadius.radiusLg)
    
    /** Category chip shape */
    val categoryChip = RoundedCornerShape(CornerRadius.radiusMd)
    
    // --------------------------------------------------------
    // SPECIAL SHAPES
    // --------------------------------------------------------
    
    /** No rounding (sharp corners) */
    val none = RoundedCornerShape(CornerRadius.none)
    
    /** Fully rounded (circle/pill) */
    val full = RoundedCornerShape(CornerRadius.full)
    
    /** Top only rounded */
    val topOnly = RoundedCornerShape(
        topStart = CornerRadius.radiusLg,
        topEnd = CornerRadius.radiusLg,
        bottomStart = CornerRadius.none,
        bottomEnd = CornerRadius.none
    )
    
    /** Bottom only rounded */
    val bottomOnly = RoundedCornerShape(
        topStart = CornerRadius.none,
        topEnd = CornerRadius.none,
        bottomStart = CornerRadius.radiusLg,
        bottomEnd = CornerRadius.radiusLg
    )
    
    /** Start only rounded (left in LTR) */
    val startOnly = RoundedCornerShape(
        topStart = CornerRadius.radiusMd,
        topEnd = CornerRadius.none,
        bottomStart = CornerRadius.radiusMd,
        bottomEnd = CornerRadius.none
    )
    
    /** End only rounded (right in LTR) */
    val endOnly = RoundedCornerShape(
        topStart = CornerRadius.none,
        topEnd = CornerRadius.radiusMd,
        bottomStart = CornerRadius.none,
        bottomEnd = CornerRadius.radiusMd
    )
}

// ============================================================
// LEGACY SUPPORT (Deprecated - Use RinconVerdeShapes instead)
// ============================================================

@Deprecated(
    message = "Use RinconVerdeShapes instead",
    replaceWith = ReplaceWith("RinconVerdeShapes")
)
val Shapes = RinconVerdeShapes
