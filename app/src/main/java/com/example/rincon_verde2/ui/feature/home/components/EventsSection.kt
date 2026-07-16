package com.example.rincon_verde2.ui.feature.home.components

import com.example.rincon_verde2.domain.model.Place
import com.example.rincon_verde2.domain.model.Event

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event as EventIcon
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImage
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.remember
import com.example.rincon_verde2.ui.theme.Strings
import com.example.rincon_verde2.ui.theme.Spacing
import com.example.rincon_verde2.ui.theme.CornerRadius
import com.example.rincon_verde2.ui.theme.Elevation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rincon_verde2.ui.theme.IconSize
import com.example.rincon_verde2.ui.theme.ComponentSize
import com.example.rincon_verde2.ui.components.common.ShimmerEffect

@Composable
fun EventsSection(
  events: List<Event>,
  modifier: Modifier = Modifier,
  isLoading: Boolean = false,
  onEventClick: (Event) -> Unit = {}
) {
  Column(modifier = modifier) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = Strings.homeUpcomingEvents,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.SemiBold
      )

      Icon(
        imageVector = Icons.Default.EventIcon,
        contentDescription = Strings.cdEventImage,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.width(IconSize.lg)
      )
    }

    Spacer(modifier = Modifier.height(Spacing.spacingMd))

    Column(
      verticalArrangement = Arrangement.spacedBy(Spacing.spacingMd)
    ) {
      if (isLoading) {
        repeat(2) {
          EventSkeleton()
        }
      } else {
        events.take(2).forEach { event ->
          EventCard(
            event = event,
            onClick = { onEventClick(event) }
          )
        }
      }
    }
  }
}

@Composable
fun EventSkeleton() {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(CornerRadius.radiusMd),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(Spacing.spacingMd),
      verticalAlignment = Alignment.CenterVertically
    ) {
      ShimmerEffect(
        modifier = Modifier
          .width(ComponentSize.thumbnailMedium)
          .height(ComponentSize.thumbnailSmall),
        shape = RoundedCornerShape(CornerRadius.radiusSm)
      )

      Spacer(modifier = Modifier.width(Spacing.spacingMd))

      Column(
        modifier = Modifier.weight(1f)
      ) {
        ShimmerEffect(
          modifier = Modifier
            .fillMaxWidth(0.6f)
            .height(14.dp)
        )
        Spacer(modifier = Modifier.height(Spacing.spacingXs))
        ShimmerEffect(
          modifier = Modifier
            .fillMaxWidth(0.4f)
            .height(10.dp)
        )
      }
    }
  }
}

@Composable
fun EventCard(
  event: Event,
  onClick: () -> Unit = {}
) {
  Card(
    onClick = onClick,
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(CornerRadius.radiusMd),
    elevation = CardDefaults.cardElevation(defaultElevation = Elevation.subtle),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      AsyncImage(
        model = event.image,
        contentDescription = event.title,
        modifier = Modifier
          .width(64.dp)
          .height(56.dp)
          .clip(RoundedCornerShape(CornerRadius.radiusSm)),
        contentScale = ContentScale.Crop
      )

      Spacer(modifier = Modifier.width(Spacing.spacingMd))

      Column(
        modifier = Modifier.weight(1f)
      ) {
        Text(
          text = event.title,
          style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp, fontWeight = FontWeight.SemiBold),
          color = MaterialTheme.colorScheme.onSurface,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(2.dp))

        Row(
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Place,
            contentDescription = Strings.cdLocation,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.size(12.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = event.location,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun EventsSectionPreview() {
  val sample = listOf(
    com.example.rincon_verde2.domain.model.Event("1","Festival Local","2026-03-01","Plaza Central","", image = ""),
    com.example.rincon_verde2.domain.model.Event("2","Concierto","2026-04-12","Parque","", image = "")
  )
  EventsSection(events = sample, modifier = Modifier)
}

@Composable
fun SimpleEventCard(event: Event) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = Spacing.spacingXs),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Column(
      modifier = Modifier.padding(Spacing.spacingLg)
    ) {
      Text(
        text = event.title,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(Spacing.spacingMd))

      Row {
        Text(
          text = event.date,
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.primary,
          fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.width(Spacing.spacingMd))

        Text(
          text = "•",
          color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        )

        Spacer(modifier = Modifier.width(Spacing.spacingMd))

        Text(
          text = event.location,
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
      }
    }
  }
}
