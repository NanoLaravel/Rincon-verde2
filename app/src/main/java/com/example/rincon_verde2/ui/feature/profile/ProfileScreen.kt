package com.example.rincon_verde2.ui.feature.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rincon_verde2.domain.model.User

@Composable
fun ProfileScreen(
    user: User = User(
        id = "1",
        email = "user@example.com",
        displayName = "Juan López",
        favoriteCount = 15,
        reviewCount = 8
    ),
    onEditClick: () -> Unit = {},
    onLogout: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Profile Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .padding(8.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = user.displayName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Stats Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly
            ) {
                StatCard(label = "Favoritos", value = user.favoriteCount.toString())
                StatCard(label = "Reseñas", value = user.reviewCount.toString())
                StatCard(label = "Calificación", value = "4.8")
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = onEditClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Editar perfil")
            }
        }
        
        Divider(modifier = Modifier.padding(vertical = 16.dp))
        
        // Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Perfil") }
            )
            
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Configuración") }
            )
        }
        
        when (selectedTab) {
            0 -> ProfileContent(user)
            1 -> SettingsContent(onLogout)
        }
    }
}

@Composable
private fun ProfileContent(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        ProfileInfoRow(label = "Email", value = user.email)
        Divider(modifier = Modifier.padding(vertical = 16.dp))
        
        ProfileInfoRow(label = "Ubicación", value = "Bogotá, Colombia")
        Divider(modifier = Modifier.padding(vertical = 16.dp))
        
        ProfileInfoRow(label = "Miembro desde", value = "Octubre 2023")
        Divider(modifier = Modifier.padding(vertical = 16.dp))
        
        Text(
            text = "Acerca de",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = "Viajero apasionado en busca de nuevas experiencias. Me encanta descubrir lugares ocultos y compartir mis hallazgos con otros viajeros.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SettingsContent(onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        SettingItem(
            title = "Notificaciones",
            subtitle = "Recibe alertas sobre nuevos lugares y eventos",
            enabled = true
        )
        
        Divider(modifier = Modifier.padding(vertical = 16.dp))
        
        SettingItem(
            title = "Privacidad",
            subtitle = "Controla quién ve tu perfil",
            enabled = true
        )
        
        Divider(modifier = Modifier.padding(vertical = 16.dp))
        
        SettingItem(
            title = "Sincronizar favoritos",
            subtitle = "Sincroniza tu lista de favoritos en todos tus dispositivos",
            enabled = true
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Cerrar sesión")
        }
    }
}

@Composable
private fun StatCard(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun SettingItem(
    title: String,
    subtitle: String,
    enabled: Boolean = true,
    onToggle: (Boolean) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Icon(
            imageVector = Icons.Filled.Settings,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}