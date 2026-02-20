package com.example.rincon_verde2.ui.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rincon_verde2.domain.model.PlaceCategory
import com.example.rincon_verde2.ui.components.PlaceCard
import com.example.rincon_verde2.ui.feature.search.components.FilterBottomSheet

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    initialFilterTab: Int = 0,
    autoOpenFilters: Boolean = false,
    onPlaceClick: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val recentSearches by viewModel.recentSearches.collectAsStateWithLifecycle()
    
    var searchQuery by remember { mutableStateOf("") }
    var showFilterSheet by remember { mutableStateOf(autoOpenFilters) }
    val filterSheetState = rememberModalBottomSheetState()
    
    // Auto-open filter sheet when navigated from PlaceList with autoOpenFilters=true
    LaunchedEffect(autoOpenFilters) {
        if (autoOpenFilters) {
            showFilterSheet = true
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                modifier = Modifier.padding(start = 8.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar lugares...") },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                singleLine = true
            )
            
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { searchQuery = "" }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear"
                    )
                }
            }
            
            IconButton(onClick = { 
                android.util.Log.d("SearchScreen", "🎯 FILTER ICON PRESSED - showFilterSheet changed from false to true")
                showFilterSheet = true 
            }) {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = "Filters"
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Category Chips
        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            val categories = listOf(null, PlaceCategory.EAT, PlaceCategory.STAY, PlaceCategory.ACTIVITY)
            categories.forEach { category ->
                FilterChip(
                    selected = uiState.selectedCategory == category,
                    onClick = { viewModel.filterByCategory(category) },
                    label = { 
                        Text(category?.toString()?.replace("_", " ") ?: "Todas")
                    },
                    modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
                )
            }
        }
        
        // Active filters indicator
        if (uiState.hasActiveFilters) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Filtros activos ✓",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "Limpiar",
                    modifier = Modifier
                        .clickable { viewModel.clearFilters() }
                        .padding(horizontal = 8.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Results, History, or Loading
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Error: ${uiState.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
            searchQuery.isEmpty() && uiState.places.isEmpty() -> {
                Column {
                    Text(
                        text = "Búsquedas recientes",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    if (recentSearches.isEmpty()) {
                        Text(
                            text = "No hay búsquedas recientes",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        LazyColumn {
                            items(recentSearches) { query ->
                                SearchHistoryItem(
                                    query = query,
                                    onSelect = { searchQuery = it },
                                    onRemove = { viewModel.removeRecentSearch(query) }
                                )
                            }
                        }
                    }
                }
            }
            else -> {
                if (uiState.places.isEmpty()) {
                    // Sin resultados
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Sin resultados",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "No se encontraron lugares que coincidan con tu búsqueda y filtros.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        OutlinedButton(
                            onClick = { viewModel.clearFilters() },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Limpiar filtros")
                        }
                    }
                } else {
                    // Con resultados
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        item {
                            Text(
                                text = "Resultados para \"${uiState.searchQuery}\"",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            Text(
                                text = "Se encontraron ${uiState.places.size} lugar${if (uiState.places.size != 1) "es" else ""}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }
                        items(uiState.places) { place ->
                            PlaceCard(
                                place = place,
                                onClick = { onPlaceClick(place.id) }
                            )
                        }
                    }
                }
            }
        }
    }
    
    // Filter Bottom Sheet
    FilterBottomSheet(
        isVisible = showFilterSheet,
        currentFilters = uiState.filters,
        initialFilterTab = initialFilterTab,
        sheetState = filterSheetState,
        onDismiss = { 
            android.util.Log.d("SearchScreen", "onDismiss called")
            showFilterSheet = false 
        },
        onApplyFilters = { newFilters ->
            android.util.Log.d("SearchScreen", "onApplyFilters CALLBACK EXECUTED: $newFilters")
            viewModel.applyFilters(newFilters)
        },
        onClearFilters = { 
            android.util.Log.d("SearchScreen", "onClearFilters CALLBACK EXECUTED")
            viewModel.clearFilters() 
        }
    )
}

@Composable
private fun SearchHistoryItem(
    query: String,
    onSelect: (String) -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect(query) }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            modifier = Modifier.padding(end = 12.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = query,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        
        IconButton(onClick = onRemove, modifier = Modifier.padding(0.dp)) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = "Remove",
                modifier = Modifier.padding(end = 0.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
