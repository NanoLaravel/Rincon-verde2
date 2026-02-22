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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rincon_verde2.domain.model.User
import com.example.rincon_verde2.ui.theme.Strings
import com.example.rincon_verde2.ui.theme.Spacing
import com.example.rincon_verde2.ui.theme.IconSize
import com.example.rincon_verde2.ui.theme.ComponentSize

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
    onNavigateBack: () -> Unit = {},
    onBottomBarVisibilityChange: (Boolean) -> Unit = {},
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    
    // Detectar dirección del scroll para ocultar/mostrar barra
    var previousScrollValue by remember { mutableIntStateOf(0) }
    
    LaunchedEffect(scrollState.value) {
        val currentScroll = scrollState.value
        val scrollDiff = currentScroll - previousScrollValue
        
        // Solo cambiar si el scroll es significativo (más de 10px)
        if (kotlin.math.abs(scrollDiff) > 10) {
            if (scrollDiff > 0) {
                // Scrolling down - ocultar barra
                onBottomBarVisibilityChange(false)
            } else {
                // Scrolling up - mostrar barra
                onBottomBarVisibilityChange(true)
            }
        }
        
        // Si estamos cerca del inicio, siempre mostrar la barra
        if (currentScroll < 50) {
            onBottomBarVisibilityChange(true)
        }
        
        previousScrollValue = currentScroll
    }
    
    // Cargar perfil al iniciar
    LaunchedEffect(Unit) {
        viewModel.loadProfile(user.id)
    }
    
    // Usar el usuario del estado si está disponible, sino el pasado por parámetro
    val displayUser = uiState.user ?: user
    
    var selectedTab by remember { mutableIntStateOf(0) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // Profile Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.spacingXxl),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = Strings.cdAvatar,
                modifier = Modifier
                    .size(ComponentSize.avatarXLarge)
                    .clip(CircleShape)
                    .padding(Spacing.spacingMd),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(Spacing.spacingLg))
            
            Text(
                text = displayUser.displayName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = displayUser.email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(Spacing.spacingXxl))
            
            // Stats Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.spacingLg),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly
            ) {
                StatCard(label = Strings.profileFavorites, value = displayUser.favoriteCount.toString())
                StatCard(label = Strings.profileReviews, value = displayUser.reviewCount.toString())
                StatCard(label = Strings.profileRating, value = "4.8")
            }
            
            Spacer(modifier = Modifier.height(Spacing.spacingXxl))
            
            Button(
                onClick = onEditClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ComponentSize.buttonLarge)
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = Strings.actionEdit,
                    modifier = Modifier.padding(end = Spacing.spacingMd)
                )
                Text(Strings.profileEdit)
            }
        }
        
        Divider(modifier = Modifier.padding(vertical = Spacing.spacingLg))
        
        // Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text(Strings.profileTab) }
            )
            
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text(Strings.profileSettingsTab) }
            )
        }
        
        when (selectedTab) {
            0 -> ProfileContent(displayUser)
            1 -> SettingsContent(onLogout)
        }
    }
}

@Composable
private fun ProfileContent(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Spacing.spacingXxl)
    ) {
        ProfileInfoRow(label = Strings.profileEmail, value = user.email)
        Divider(modifier = Modifier.padding(vertical = Spacing.spacingLg))
        
        ProfileInfoRow(label = Strings.profileLocation, value = Strings.profileLocationValue)
        Divider(modifier = Modifier.padding(vertical = Spacing.spacingLg))
        
        ProfileInfoRow(label = Strings.profileMemberSince, value = Strings.profileMemberSinceValue)
        Divider(modifier = Modifier.padding(vertical = Spacing.spacingLg))
        
        Text(
            text = Strings.profileAbout,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = Spacing.spacingMd)
        )
        
        Text(
            text = Strings.profileAboutValue,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SettingsContent(onLogout: () -> Unit, viewModel: ProfileViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Spacing.spacingXxl)
    ) {
        SettingItem(
            title = Strings.profileNotifications,
            subtitle = Strings.profileNotificationsSubtitle,
            enabled = true
        )
        
        Divider(modifier = Modifier.padding(vertical = Spacing.spacingLg))
        
        SettingItem(
            title = Strings.profilePrivacy,
            subtitle = Strings.profilePrivacySubtitle,
            enabled = true
        )
        
        Divider(modifier = Modifier.padding(vertical = Spacing.spacingLg))
        
        SettingItem(
            title = Strings.profileSyncFavorites,
            subtitle = Strings.profileSyncFavoritesSubtitle,
            enabled = true
        )
        
        Spacer(modifier = Modifier.height(Spacing.spacingXxxl))
        
        OutlinedButton(
            onClick = {
                viewModel.logout()
                onLogout()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(ComponentSize.buttonLarge)
        ) {
            Text(Strings.profileLogout)
        }
    }
}

@Composable
private fun StatCard(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(Spacing.spacingMd)
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
        
        Spacer(modifier = Modifier.height(Spacing.spacingXs))
        
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
            .padding(vertical = Spacing.spacingLg),
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
            contentDescription = Strings.cdSettings,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
