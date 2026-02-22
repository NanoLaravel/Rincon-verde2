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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.rincon_verde2.ui.theme.Strings
import com.example.rincon_verde2.ui.theme.Spacing
import com.example.rincon_verde2.ui.theme.CornerRadius

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    initialFilterTab: Int = 0,
    autoOpenFilters: Boolean = false,
    onPlaceClick: (String) -> Unit = {},
    onBottomBarVisibilityChange: (Boolean) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val recentSearches by viewModel.recentSearches.collectAsStateWithLifecycle()
    
    var searchQuery by remember { mutableStateOf("") }
    var showFilterSheet by remember { mutableStateOf(autoOpenFilters) }
    val filterSheetState = rememberModalBottomSheetState()
    val lazyListState = rememberLazyListState()
    
    // Detectar dirección del scroll para ocultar/mostrar barra
    var previousScrollValue by remember { mutableIntStateOf(0) }
    
    LaunchedEffect(lazyListState.firstVisibleItemScrollOffset) {
        val currentScroll = lazyListState.firstVisibleItemScrollOffset + 
                           lazyListState.firstVisibleItemIndex * 1000
        val scrollDiff = currentScroll - previousScrollValue
        
        if (kotlin.math.abs(scrollDiff) > 10) {
            if (scrollDiff > 0) {
                onBottomBarVisibilityChange(false)
            } else {
                onBottomBarVisibilityChange(true)
            }
        }
        
        if (lazyListState.firstVisibleItemIndex == 0 && lazyListState.firstVisibleItemScrollOffset < 50) {
            onBottomBarVisibilityChange(true)
        }
        
        previousScrollValue = currentScroll
    }
    
    // Auto-open filter sheet when navigated from PlaceList with autoOpenFilters=true
    LaunchedEffect(autoOpenFilters) {
        if (autoOpenFilters) {
            showFilterSheet = true
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacing.spacingLg)
    ) {
        // Search Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(CornerRadius.radiusLg)
                )
                .padding(Spacing.spacingXs),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = Strings.cdSearch,
                modifier = Modifier.padding(start = Spacing.spacingMd),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text(Strings.searchPlaceholder) },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Spacing.spacingMd),
                singleLine = true
            )
            
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { searchQuery = "" }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = Strings.cdClear
                    )
                }
            }
            
            IconButton(onClick = { 
                android.util.Log.d("SearchScreen", "🎯 FILTER ICON PRESSED - showFilterSheet changed from false to true")
                showFilterSheet = true 
            }) {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = Strings.cdFilter
                )
            }
        }
        
        Spacer(modifier = Modifier.height(Spacing.spacingLg))
        
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
                        Text(
                            when (category) {
                                null -> Strings.searchAllCategories
                                PlaceCategory.EAT -> Strings.categoryEat
                                PlaceCategory.STAY -> Strings.categoryStay
                                PlaceCategory.ACTIVITY -> Strings.categoryActivity
                                PlaceCategory.FAVORITES -> Strings.homeFavorites
                            }
                        )
                    },
                    modifier = Modifier.padding(end = Spacing.spacingMd, bottom = Spacing.spacingMd)
                )
            }
        }
        
        // Active filters indicator
        if (uiState.hasActiveFilters) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Spacing.spacingMd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    Strings.searchFiltersActive,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    Strings.searchClear,
                    modifier = Modifier
                        .clickable { viewModel.clearFilters() }
                        .padding(horizontal = Spacing.spacingMd),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        
        Spacer(modifier = Modifier.height(Spacing.spacingLg))
        
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
                            text = "${Strings.errorGeneric}: ${uiState.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
            searchQuery.isEmpty() && uiState.places.isEmpty() -> {
                Column {
                    Text(
                        text = Strings.searchRecent,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = Spacing.spacingMd)
                    )
                    
                    if (recentSearches.isEmpty()) {
                        Text(
                            text = Strings.searchNoRecent,
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
                            .padding(Spacing.spacingLg),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = Strings.searchNoResults,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = Spacing.spacingMd)
                        )
                        Text(
                            text = Strings.searchNoResultsMessage,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = Spacing.spacingLg, vertical = Spacing.spacingMd),
                            textAlign = TextAlign.Center
                        )
                        OutlinedButton(
                            onClick = { viewModel.clearFilters() },
                            modifier = Modifier.padding(top = Spacing.spacingLg)
                        ) {
                            Text(Strings.searchClearFilters)
                        }
                    }
                } else {
                    // Con resultados
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        state = lazyListState
                    ) {
                        item {
                            Text(
                                text = Strings.searchResultsFor(uiState.searchQuery),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = Spacing.spacingMd)
                            )
                            Text(
                                text = Strings.searchPlacesFound(uiState.places.size),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = Spacing.spacingLg)
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
            .padding(vertical = Spacing.spacingMd),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            modifier = Modifier.padding(end = Spacing.spacingMd),
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
                contentDescription = Strings.actionDelete,
                modifier = Modifier.padding(end = 0.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
