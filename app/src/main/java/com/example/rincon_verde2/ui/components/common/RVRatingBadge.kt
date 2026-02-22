package com.example.rincon_verde2.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.rincon_verde2.ui.theme.Strings
import com.example.rincon_verde2.ui.theme.Spacing
import com.example.rincon_verde2.ui.theme.CornerRadius
import com.example.rincon_verde2.ui.theme.IconSize
import com.example.rincon_verde2.ui.theme.YellowAccent
import com.example.rincon_verde2.ui.theme.GreenPrimary

/**
 * Rincón Verde Rating Badge
 * 
 * A standardized rating display component used across the app.
 * Displays a star icon with the rating value.
 * 
 * @param rating The rating value to display
 * @param modifier Optional modifier
 * @param backgroundColor Background color of the badge (default: primary color)
 * @param starColor Color of the star icon (default: yellow)
 * @param textColor Color of the rating text (default: white)
 */
@Composable
fun RVRatingBadge(
    rating: Float,
    modifier: Modifier = Modifier,
    backgroundColor: Color = GreenPrimary,
    starColor: Color = YellowAccent,
    textColor: Color = Color.White
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(CornerRadius.radiusSm))
            .background(backgroundColor)
            .padding(horizontal = Spacing.spacingMd, vertical = Spacing.spacingXs),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = Strings.cdRating,
            tint = starColor,
            modifier = Modifier.size(IconSize.xs)
        )
        Spacer(modifier = Modifier.width(Spacing.spacingXxs))
        Text(
            text = "%.1f".format(rating),
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Compact Rating Badge for smaller spaces
 */
@Composable
fun RVRatingBadgeCompact(
    rating: Float,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Black.copy(alpha = 0.7f),
    starColor: Color = YellowAccent,
    textColor: Color = Color.White
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(CornerRadius.radiusSm))
            .background(backgroundColor)
            .padding(horizontal = Spacing.spacingSm, vertical = Spacing.spacingXxs),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = Strings.cdRating,
            tint = starColor,
            modifier = Modifier.size(IconSize.xxs)
        )
        Spacer(modifier = Modifier.width(Spacing.spacingXxs))
        Text(
            text = "%.1f".format(rating),
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Rating display with label
 */
@Composable
fun RVRatingWithLabel(
    rating: Float,
    reviewCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = Strings.cdRating,
            tint = YellowAccent,
            modifier = Modifier.size(IconSize.sm)
        )
        Spacer(modifier = Modifier.width(Spacing.spacingXs))
        Text(
            text = "%.1f".format(rating),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.width(Spacing.spacingXs))
        Text(
            text = "($reviewCount)",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RVRatingBadgePreview() {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.spacingMd)) {
        RVRatingBadge(rating = 4.8f)
        RVRatingBadgeCompact(rating = 4.5f)
        RVRatingWithLabel(rating = 4.7f, reviewCount = 120)
    }
}
