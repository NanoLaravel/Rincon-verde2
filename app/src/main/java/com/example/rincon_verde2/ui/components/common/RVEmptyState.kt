package com.example.rincon_verde2.ui.components.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.rincon_verde2.ui.theme.Strings
import com.example.rincon_verde2.ui.theme.Spacing
import com.example.rincon_verde2.ui.theme.IconSize

/**
 * Rincón Verde Empty State
 * 
 * A standardized empty state component for displaying when there's no content.
 * Used throughout the app for consistent empty state displays.
 * 
 * @param title The main title/message
 * @param modifier Optional modifier
 * @param description Optional description text
 * @param icon Optional icon to display
 * @param actionText Optional action button text
 * @param onActionClick Optional callback when action button is clicked
 */
@Composable
fun RVEmptyState(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    icon: ImageVector? = null,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Spacing.spacingXxl),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(IconSize.massive)
                    .padding(bottom = Spacing.spacingLg),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        }
        
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        if (description != null) {
            Spacer(modifier = Modifier.height(Spacing.spacingMd))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
        
        if (actionText != null && onActionClick != null) {
            Spacer(modifier = Modifier.height(Spacing.spacingXxl))
            OutlinedButton(onClick = onActionClick) {
                Text(actionText)
            }
        }
    }
}

/**
 * Empty State for search results
 */
@Composable
fun RVEmptySearchResults(
    query: String,
    modifier: Modifier = Modifier,
    onClearFilters: (() -> Unit)? = null
) {
    RVEmptyState(
        title = Strings.searchNoResults,
        description = Strings.searchNoResultsMessage,
        icon = Icons.Default.Search,
        actionText = if (onClearFilters != null) Strings.searchClearFilters else null,
        onActionClick = onClearFilters,
        modifier = modifier
    )
}

/**
 * Empty State for favorites
 */
@Composable
fun RVEmptyFavorites(
    modifier: Modifier = Modifier,
    onExplore: (() -> Unit)? = null
) {
    RVEmptyState(
        title = Strings.favoritesEmpty,
        description = Strings.favoritesEmptyMessage,
        actionText = if (onExplore != null) Strings.favoritesExplore else null,
        onActionClick = onExplore,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun RVEmptyStatePreview() {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.spacingLg)) {
        RVEmptyState(
            title = "Sin resultados",
            description = "No se encontraron lugares que coincidan con tu búsqueda.",
            icon = Icons.Default.Search
        )
    }
}
