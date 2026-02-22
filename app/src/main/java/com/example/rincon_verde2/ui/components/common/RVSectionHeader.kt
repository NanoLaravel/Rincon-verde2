package com.example.rincon_verde2.ui.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.rincon_verde2.ui.theme.Strings
import com.example.rincon_verde2.ui.theme.Spacing
import com.example.rincon_verde2.ui.theme.IconSize

/**
 * Rincón Verde Section Header
 * 
 * A standardized section header component with optional "See all" action.
 * Used throughout the app for consistent section titles.
 * 
 * @param title The section title
 * @param modifier Optional modifier
 * @param actionText Optional action text (e.g., "Ver todos")
 * @param onActionClick Optional callback when action is clicked
 * @param showArrow Whether to show an arrow icon after action text
 */
@Composable
fun RVSectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
    showArrow: Boolean = false
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold
        )
        
        if (actionText != null && onActionClick != null) {
            Row(
                modifier = Modifier.clickable { onActionClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = actionText,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
                
                if (showArrow) {
                    Spacer(modifier = Modifier.width(Spacing.spacingXs))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(IconSize.sm)
                    )
                }
            }
        }
    }
}

/**
 * Section Header with icon
 */
@Composable
fun RVSectionHeaderWithIcon(
    title: String,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold
        )
        
        icon()
    }
}

@Preview(showBackground = true)
@Composable
fun RVSectionHeaderPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.spacingLg)) {
        RVSectionHeader(
            title = "Mejor Valorados"
        )
        
        RVSectionHeader(
            title = "Mejor Valorados",
            actionText = "Ver todos",
            onActionClick = {}
        )
        
        RVSectionHeader(
            title = "Mejor Valorados",
            actionText = "Ver todos",
            onActionClick = {},
            showArrow = true
        )
    }
}
