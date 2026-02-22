package com.example.rincon_verde2.ui.theme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Rincón Verde Dimension System
 * 
 * A comprehensive set of dimension constants for consistent spacing,
 * sizing, and layout throughout the app.
 * 
 * Usage:
 * - Spacing: Use for padding, margins, gaps
 * - Size: Use for fixed component sizes
 * - Height: Use for component heights
 * - Width: Use for component widths
 * - Radius: Use for corner radii
 * - Elevation: Use for shadow depths
 * - Icon: Use for icon sizes
 */

// ============================================================
// SPACING - Padding, Margins, Gaps
// ============================================================

object Spacing {
    /** Extra tight spacing - 2dp */
    val spacingXxs = 2.dp
    
    /** Tight spacing - 4dp */
    val spacingXs = 4.dp
    
    /** Small spacing - 8dp */
    val spacingSm = 8.dp
    
    /** Standard spacing - 12dp */
    val spacingMd = 12.dp
    
    /** Medium spacing - 16dp */
    val spacingLg = 16.dp
    
    /** Large spacing - 20dp */
    val spacingXl = 20.dp
    
    /** Extra large spacing - 24dp */
    val spacingXxl = 24.dp
    
    /** Huge spacing - 32dp */
    val spacingHuge = 32.dp
    
    /** Extra huge spacing - 40dp */
    val spacingXxxl = 40.dp
    
    /** Massive spacing - 48dp */
    val spacingMassive = 48.dp
}

// ============================================================
// COMPONENT SIZES - Fixed Dimensions
// ============================================================

object ComponentSize {
    /** Touch target minimum - 48dp (Material guideline) */
    val touchTarget = 48.dp
    
    /** Small touch target - 40dp */
    val touchTargetSmall = 40.dp
    
    /** Icon button size */
    val iconButton = 48.dp
    
    /** Small icon button size */
    val iconButtonSmall = 40.dp
    
    /** Avatar sizes */
    val avatarSmall = 32.dp
    val avatarMedium = 48.dp
    val avatarLarge = 64.dp
    val avatarXLarge = 80.dp
    
    /** Thumbnail sizes */
    val thumbnailSmall = 48.dp
    val thumbnailMedium = 72.dp
    val thumbnailLarge = 96.dp
    
    /** Card heights */
    val cardSmall = 80.dp
    val cardMedium = 120.dp
    val cardLarge = 160.dp
    val cardXLarge = 200.dp
    
    /** List item heights */
    val listItemSmall = 48.dp
    val listItemMedium = 64.dp
    val listItemLarge = 72.dp
    val listItemXLarge = 80.dp
    
    /** Input field height */
    val inputField = 56.dp
    
    /** Button heights */
    val buttonSmall = 36.dp
    val buttonMedium = 44.dp
    val buttonLarge = 48.dp
    
    /** Header heights */
    val headerSmall = 56.dp
    val headerMedium = 120.dp
    val headerLarge = 180.dp
    val headerXLarge = 220.dp
    
    /** Bottom navigation height */
    val bottomNav = 56.dp
    
    /** Top app bar height */
    val topAppBar = 56.dp
    
    /** Tab height */
    val tabHeight = 48.dp
    
    /** Divider thickness */
    val dividerThin = 0.5.dp
    val dividerMedium = 1.dp
    val dividerThick = 2.dp
    
    /** Border width */
    val borderThin = 0.5.dp
    val borderMedium = 1.dp
    val borderThick = 2.dp
}

// ============================================================
// CORNER RADIUS
// ============================================================

object CornerRadius {
    /** No radius - sharp corners */
    val none = 0.dp
    
    /** Extra small radius - 2dp */
    val radiusXs = 2.dp
    
    /** Small radius - 4dp */
    val radiusSm = 4.dp
    
    /** Medium radius - 8dp */
    val radiusMd = 8.dp
    
    /** Large radius - 12dp */
    val radiusLg = 12.dp
    
    /** Extra large radius - 16dp */
    val radiusXl = 16.dp
    
    /** Huge radius - 20dp */
    val radiusXxl = 20.dp
    
    /** Extra huge radius - 24dp */
    val radiusXxxl = 24.dp
    
    /** Full radius - circular/pill shape */
    val full = 50.dp
}

// ============================================================
// ELEVATION - Shadow Depths
// ============================================================

object Elevation {
    /** No elevation */
    val none = 0.dp
    
    /** Subtle elevation - 1dp */
    val subtle = 1.dp
    
    /** Low elevation - 2dp */
    val low = 2.dp
    
    /** Medium elevation - 4dp */
    val medium = 4.dp
    
    /** High elevation - 6dp */
    val high = 6.dp
    
    /** Raised elevation - 8dp */
    val raised = 8.dp
    
    /** Floating elevation - 12dp */
    val floating = 12.dp
    
    /** Modal elevation - 16dp */
    val modal = 16.dp
    
    /** Dialog elevation - 24dp */
    val dialog = 24.dp
}

// ============================================================
// ICON SIZES
// ============================================================

object IconSize {
    /** Extra small icon - 12dp */
    val xxs = 12.dp
    
    /** Small icon - 14dp */
    val xs = 14.dp
    
    /** Medium small icon - 16dp */
    val sm = 16.dp
    
    /** Medium icon - 20dp */
    val md = 20.dp
    
    /** Large icon - 24dp (standard) */
    val lg = 24.dp
    
    /** Extra large icon - 28dp */
    val xl = 28.dp
    
    /** Huge icon - 32dp */
    val xxl = 32.dp
    
    /** Extra huge icon - 36dp */
    val xxxl = 36.dp
    
    /** Massive icon - 48dp */
    val massive = 48.dp
}

// ============================================================
// SCREEN DIMENSIONS
// ============================================================

object ScreenDimension {
    /** Minimum edge padding */
    val edgePadding = 16.dp
    
    /** Maximum content width for tablets */
    val maxContentWidth = 600.dp
    
    /** Minimum button width */
    val minButtonWidth = 120.dp
    
    /** Maximum button width */
    val maxButtonWidth = 280.dp
    
    /** Minimum input width */
    val minInputWidth = 200.dp
    
    /** Maximum input width */
    val maxInputWidth = 400.dp
}

// ============================================================
// ANIMATION DURATIONS (in milliseconds)
// ============================================================

object AnimationDuration {
    /** Very short animation - 100ms */
    const val veryShort = 100
    
    /** Short animation - 200ms */
    const val short = 200
    
    /** Medium animation - 300ms */
    const val medium = 300
    
    /** Long animation - 400ms */
    const val long = 400
    
    /** Very long animation - 500ms */
    const val veryLong = 500
    
    /** Page transition - 350ms */
    const val pageTransition = 350
    
    /** Bottom sheet - 400ms */
    const val bottomSheet = 400
    
    /** Dialog - 300ms */
    const val dialog = 300
    
    /** Snackbar - 300ms */
    const val snackbar = 300
}

// ============================================================
// ASPECT RATIOS
// ============================================================

object AspectRatio {
    /** Square - 1:1 */
    const val square = 1f
    
    /** Portrait photo - 3:4 */
    const val portrait = 0.75f
    
    /** Landscape photo - 4:3 */
    const val landscape = 1.333f
    
    /** Wide photo - 16:9 */
    const val wide = 1.778f
    
    /** Card ratio - 2:3 */
    const val card = 0.667f
    
    /** Thumbnail ratio - 1:1.5 */
    const val thumbnail = 0.667f
    
    /** Hero image ratio - 3:2 */
    const val hero = 1.5f
}

// ============================================================
// EXTENSION FUNCTIONS FOR COMMON USAGE
// ============================================================

/**
 * Extension to apply standard padding
 */
val standardPadding = Spacing.spacingLg

/**
 * Extension to apply card padding
 */
val cardPadding = Spacing.spacingMd

/**
 * Extension to apply list item padding
 */
val listItemPadding = Spacing.spacingMd

/**
 * Extension to apply screen edge padding
 */
val screenEdgePadding = Spacing.spacingLg

/**
 * Extension to apply section spacing
 */
val sectionSpacing = Spacing.spacingXxl
