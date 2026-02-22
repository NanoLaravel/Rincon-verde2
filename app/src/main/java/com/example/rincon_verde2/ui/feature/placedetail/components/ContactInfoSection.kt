package com.example.rincon_verde2.ui.feature.placedetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rincon_verde2.ui.feature.placedetail.ContactInfo

@Composable
fun ContactInfoSection(
  contact: ContactInfo,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 12.dp)
  ) {
    Text(
      text = "Información de Contacto",
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.SemiBold,
      color = MaterialTheme.colorScheme.onBackground
    )

    Spacer(modifier = Modifier.height(12.dp))

    // Teléfono
    ContactItemRow(
      icon = Icons.Default.Phone,
      label = "Teléfono",
      value = contact.phone
    )

    Spacer(modifier = Modifier.height(10.dp))

    // Horarios
    ContactItemRow(
      icon = Icons.Default.Schedule,
      label = "Horarios",
      value = contact.hours
    )

    if (contact.email != null) {
      Spacer(modifier = Modifier.height(10.dp))
      ContactItemRow(
        icon = Icons.Default.Email,
        label = "Email",
        value = contact.email
      )
    }

    if (contact.website != null) {
      Spacer(modifier = Modifier.height(10.dp))
      ContactItemRow(
        icon = Icons.Default.Language,
        label = "Sitio Web",
        value = contact.website
      )
    }
  }
}

@Composable
private fun ContactItemRow(
  icon: ImageVector,
  label: String,
  value: String
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.Top
  ) {
    Icon(
      imageVector = icon,
      contentDescription = label,
      tint = MaterialTheme.colorScheme.primary,
      modifier = Modifier.padding(top = 2.dp)
    )
    Spacer(modifier = Modifier.width(12.dp))
    Column {
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
      )
      Text(
        text = value,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Medium
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun ContactInfoSectionPreview() {
  ContactInfoSection(
    contact = ContactInfo(
      phone = "+1 (555) 123-4567",
      email = "info@restaurant.com",
      website = "www.restaurant.com",
      hours = "Lun-Dom: 12:00 PM - 10:00 PM"
    )
  )
}
