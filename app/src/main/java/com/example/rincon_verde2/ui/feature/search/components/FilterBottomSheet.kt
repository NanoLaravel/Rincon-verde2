package com.example.rincon_verde2.ui.feature.search.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.rincon_verde2.domain.model.Filter

/**
 * Datos de configuración para cada tab de filtro.
 */
data class FilterTabConfig(
    val icon: ImageVector,
    val label: String,
    val searchPlaceholder: String,
    val typeOptions: List<String>,
    val serviceOptions: List<String>,
    val typeTitle: String,
    val serviceTitle: String
)

/**
 * Configuraciones por defecto para las 3 categorías de filtros.
 */
private val EAT_CONFIG = FilterTabConfig(
    icon = Icons.Filled.Restaurant,
    label = "Comer",
    searchPlaceholder = "Buscar restaurantes",
    typeOptions = listOf("Restaurante", "Café", "Bar", "Gastrobar", "Comida rápida", "Heladería"),
    serviceOptions = listOf("Terraza", "Cocina local", "Vegetariano", "Para familias", "WiFi", "Parqueadero", "Entrega a domicilio", "Acepta reservas"),
    typeTitle = "Tipos de cocina",
    serviceTitle = "Servicios y ambiente"
)

private val STAY_CONFIG = FilterTabConfig(
    icon = Icons.Filled.Hotel,
    label = "Alojarse",
    searchPlaceholder = "Buscar alojamiento",
    typeOptions = listOf("Hotel", "Cabaña", "Casa rural", "Apartamento", "Hostal", "Resort"),
    serviceOptions = listOf("Piscina", "Piscina para niños", "Spa", "WiFi gratis", "Parqueo", "Desayuno incluido", "Vista al bosque"),
    typeTitle = "Tipo de alojamiento",
    serviceTitle = "Servicios"
)

private val DO_CONFIG = FilterTabConfig(
    icon = Icons.Filled.DirectionsRun,
    label = "Hacer",
    searchPlaceholder = "Buscar actividades",
    typeOptions = listOf("Senderismo", "Observación de aves", "Tour guiado", "Deportes extremos", "Fotografía"),
    serviceOptions = listOf("Para familias", "Guía incluido", "Fotografía", "Educativo", "Aventura", "Naturaleza", "Reflexión", "Nivel fácil"),
    typeTitle = "Tipo de actividad",
    serviceTitle = "Características"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    isVisible: Boolean,
    currentFilters: Filter,
    initialFilterTab: Int = 0,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onApplyFilters: (Filter) -> Unit,
    onClearFilters: () -> Unit
) {
    Log.d("FilterBottomSheet", "🎨 FilterBottomSheet COMPOSABLE CALLED - isVisible=$isVisible")
    if (!isVisible) {
        Log.d("FilterBottomSheet", "⚠️ isVisible is FALSE, returning early without showing sheet")
        return
    }
    
    Log.d("FilterBottomSheet", "✅ FilterBottomSheet WILL RENDER - isVisible=$isVisible")

    // Estados de los sliders
    var ratingRange by remember(currentFilters) {
        mutableStateOf(currentFilters.minRating..currentFilters.maxRating)
    }
    var priceRange by remember(currentFilters) {
        mutableStateOf(currentFilters.minPrice..currentFilters.maxPrice)
    }
    var distanceRange by remember(currentFilters) {
        mutableStateOf(currentFilters.minDistance..currentFilters.maxDistance)
    }
    
    // Estados de selección
    var selectedTypes by remember(currentFilters) { 
        mutableStateOf(currentFilters.categories) 
    }
    var selectedServices by remember(currentFilters) { 
        mutableStateOf(currentFilters.amenities) 
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Header con título y acciones
            FilterHeader(
                onDismiss = onDismiss,
                onClearFilters = onClearFilters
            )

            // Tabs de categoría
            var selectedTabIndex by remember(initialFilterTab) { mutableIntStateOf(initialFilterTab) }
            val tabConfigs = listOf(EAT_CONFIG, STAY_CONFIG, DO_CONFIG)

            CategoryTabRow(
                tabs = tabConfigs,
                selectedIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it }
            )

            Divider()

            // Contenido del tab seleccionado
            val currentConfig = tabConfigs[selectedTabIndex]
            
            FilterTabContent(
                config = currentConfig,
                ratingRange = ratingRange,
                onRatingChange = { ratingRange = it },
                priceRange = priceRange,
                onPriceChange = { priceRange = it },
                distanceRange = distanceRange,
                onDistanceChange = { distanceRange = it },
                selectedTypes = selectedTypes,
                onTypesChange = { selectedTypes = it },
                selectedServices = selectedServices,
                onServicesChange = { selectedServices = it },
                onApplyFilters = {
                    val newFilter = Filter(
                        minRating = ratingRange.start,
                        maxRating = ratingRange.endInclusive,
                        minPrice = priceRange.start,
                        maxPrice = priceRange.endInclusive,
                        minDistance = distanceRange.start,
                        maxDistance = distanceRange.endInclusive,
                        amenities = selectedServices,
                        categories = selectedTypes
                    )
                    onApplyFilters(newFilter)
                    onDismiss()
                },
                onClearFilters = {
                    onClearFilters()
                    onDismiss()
                }
            )
        }
    }
}

/**
 * Fila de tabs de categoría (Comer / Alojarse / Hacer).
 */
@Composable
private fun CategoryTabRow(
    tabs: List<FilterTabConfig>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tabs.forEachIndexed { index, config ->
            FilterChip(
                selected = selectedIndex == index,
                onClick = { onTabSelected(index) },
                label = { Text(config.label) },
                leadingIcon = {
                    Icon(imageVector = config.icon, contentDescription = null)
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Contenido de un tab de filtro individual.
 */
@Composable
private fun FilterTabContent(
    config: FilterTabConfig,
    ratingRange: ClosedFloatingPointRange<Float>,
    onRatingChange: (ClosedFloatingPointRange<Float>) -> Unit,
    priceRange: IntRange,
    onPriceChange: (IntRange) -> Unit,
    distanceRange: ClosedFloatingPointRange<Float>,
    onDistanceChange: (ClosedFloatingPointRange<Float>) -> Unit,
    selectedTypes: List<String>,
    onTypesChange: (List<String>) -> Unit,
    selectedServices: List<String>,
    onServicesChange: (List<String>) -> Unit,
    onApplyFilters: () -> Unit,
    onClearFilters: () -> Unit
) {
    // Campo de búsqueda
    Text(
        text = "Buscar",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
    )

    SearchField(placeholder = config.searchPlaceholder)

    // Sliders comunes
    FilterSlidersGroup(
        ratingRange = ratingRange,
        onRatingChange = onRatingChange,
        priceRange = priceRange,
        onPriceChange = onPriceChange,
        distanceRange = distanceRange,
        onDistanceChange = onDistanceChange
    )

    // Tipos (ChipGrid)
    SectionTitle(text = config.typeTitle)
    
    FilterChipGrid(
        items = config.typeOptions,
        selectedItems = selectedTypes,
        onSelectionChange = onTypesChange,
        columns = 3
    )

    Divider(modifier = Modifier.padding(vertical = 12.dp))

    // Servicios (CheckboxList)
    SectionTitle(text = config.serviceTitle)
    
    CheckboxList(
        items = config.serviceOptions,
        selectedItems = selectedServices,
        onSelectionChange = onServicesChange,
        columns = 2
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Botones de acción
    ActionButtonsRow(
        onCancelClick = onClearFilters,
        onApplyClick = onApplyFilters
    )
}
