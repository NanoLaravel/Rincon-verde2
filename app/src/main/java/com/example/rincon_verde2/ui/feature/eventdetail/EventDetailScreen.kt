package com.example.rincon_verde2.ui.feature.eventdetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rincon_verde2.domain.model.Event
import com.example.rincon_verde2.ui.feature.placedetail.components.HeroImage
import com.example.rincon_verde2.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    event: Event,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Evento") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                HeroImage(
                    imageUrl = event.image,
                    imageUrls = event.imageUrls,
                    rating = 0f,
                    priceCategory = 0
                )
            }

            item {
                Column(modifier = Modifier.padding(Spacing.spacingLg)) {
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(Spacing.spacingMd))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(Spacing.spacingSm))
                        Text(text = event.date, style = MaterialTheme.typography.bodyLarge)
                    }

                    Spacer(modifier = Modifier.height(Spacing.spacingSm))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(Spacing.spacingSm))
                        Text(text = event.location, style = MaterialTheme.typography.bodyLarge)
                    }

                    if (!event.price.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(Spacing.spacingSm))
                        Text(
                            text = "Precio: ${event.price}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(Spacing.spacingLg))

                    Text(
                        text = "Sobre este evento",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(Spacing.spacingSm))
                    
                    Text(
                        text = event.description,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 24.sp
                    )
                }
            }
        }
    }
}
