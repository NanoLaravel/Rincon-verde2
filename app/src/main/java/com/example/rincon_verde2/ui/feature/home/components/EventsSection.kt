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
import com.example.rincon_verde2.ui.theme.IconSize
import com.example.rincon_verde2.ui.theme.ComponentSize

@Composable
fun EventsSection(
  events: List<Event>,
  modifier: Modifier = Modifier
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
      events.take(2).forEach { event ->
        EventCard(event = event)
      }
    }
  }
}

@Composable
fun EventCard(event: Event) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(CornerRadius.radiusMd),
    elevation = CardDefaults.cardElevation(defaultElevation = Elevation.subtle),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(Spacing.spacingMd),
      verticalAlignment = Alignment.CenterVertically
    ) {
      AsyncImage(
        model = event.image,
        contentDescription = event.title,
        modifier = Modifier
          .width(ComponentSize.thumbnailLarge)
          .height(ComponentSize.thumbnailMedium)
          .clip(RoundedCornerShape(CornerRadius.radiusSm)),
        contentScale = ContentScale.Crop
      )

      Spacer(modifier = Modifier.width(Spacing.spacingMd))

      Column(
        modifier = Modifier.weight(1f)
      ) {
        Text(
          text = event.title,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurface,
          fontWeight = FontWeight.Medium,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(Spacing.spacingXs))

        Row(
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Place,
            contentDescription = Strings.cdLocation,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.width(IconSize.xs)
          )
          Spacer(modifier = Modifier.width(Spacing.spacingXs))
          Text(
            text = event.location,
            style = MaterialTheme.typography.labelSmall,
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
