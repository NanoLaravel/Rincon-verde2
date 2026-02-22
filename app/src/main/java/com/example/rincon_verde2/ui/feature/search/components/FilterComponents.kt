package com.example.rincon_verde2.ui.feature.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// =============================================================================
// COMPONENTE 1: FilterHeader - Barra superior con título y acciones
// =============================================================================

/**
 * Header del BottomSheet con botón atrás, título y botón limpiar.
 * Se usa una sola vez pero encapsula la lógica del header.
 */
@Composable
fun FilterHeader(
    title: String = "Filtros de búsqueda",
    onDismiss: () -> Unit,
    onClearFilters: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onDismiss) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver"
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )
        IconButton(onClick = { onClearFilters(); onDismiss() }) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = "Limpiar filtros"
            )
        }
    }
}

// =============================================================================
// COMPONENTE 2: SearchField - Campo de búsqueda reutilizable
// =============================================================================

/**
 * Campo de texto para búsqueda con icono de lupa.
 * Usado en las 3 tabs del filtro.
 */
@Composable
fun SearchField(
    placeholder: String,
    modifier: Modifier = Modifier,
    initialValue: String = "",
    onValueChange: (String) -> Unit = {}
) {
    var searchText by remember { mutableStateOf(initialValue) }
    
    OutlinedTextField(
        value = searchText,
        onValueChange = { 
            searchText = it
            onValueChange(it)
        },
        leadingIcon = { 
            Icon(imageVector = Icons.Filled.Search, contentDescription = null) 
        },
        placeholder = { Text(text = placeholder) },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
}

// =============================================================================
// COMPONENTE 3: RangeSliderSection - Slider de rango reutilizable
// =============================================================================

/**
 * Sección completa con título y RangeSlider.
 * Usado para Calificación, Precio y Distancia en las 3 tabs.
 */
@Composable
fun RangeSliderSection(
    label: String,
    value: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    steps: Int = 0,
    modifier: Modifier = Modifier,
    valueFormatter: (Float) -> String = { it.toString() }
) {
    val startFormatted = valueFormatter(value.start)
    val endFormatted = valueFormatter(value.endInclusive)
    
    Text(
        text = "$label: $startFormatted - $endFormatted",
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(top = 8.dp)
    )
    RangeSlider(
        value = value,
        onValueChange = onValueChange,
        valueRange = valueRange,
        steps = steps,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}

// =============================================================================
// COMPONENTE 4: FilterChipGrid - Grid de FilterChips seleccionables
// =============================================================================

/**
 * Grid de FilterChips para selección múltiple.
 * Usado para tipos de cocina, alojamiento, actividades, etc.
 */
@Composable
fun FilterChipGrid(
    items: List<String>,
    selectedItems: List<String>,
    onSelectionChange: (List<String>) -> Unit,
    columns: Int = 3,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        items.chunked(columns).forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { item ->
                    FilterChip(
                        selected = selectedItems.contains(item),
                        onClick = {
                            val newList = selectedItems.toMutableList()
                            if (newList.contains(item)) {
                                newList.remove(item)
                            } else {
                                newList.add(item)
                            }
                            onSelectionChange(newList)
                        },
                        label = { 
                            Text(item, style = MaterialTheme.typography.labelSmall) 
                        },
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .weight(1f)
                    )
                }
                // Fill remaining space if row is not complete
                repeat(columns - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

// =============================================================================
// COMPONENTE 5: CheckboxList - Lista de checkboxes en grid
// =============================================================================

/**
 * Grid de checkboxes para selección múltiple.
 * Usado para servicios, características, amenidades, etc.
 */
@Composable
fun CheckboxList(
    items: List<String>,
    selectedItems: List<String>,
    onSelectionChange: (List<String>) -> Unit,
    columns: Int = 2,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        items.chunked(columns).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { item ->
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectedItems.contains(item),
                            onCheckedChange = { checked ->
                                val newList = selectedItems.toMutableList()
                                if (checked) {
                                    newList.add(item)
                                } else {
                                    newList.remove(item)
                                }
                                onSelectionChange(newList)
                            },
                            modifier = Modifier.padding(0.dp)
                        )
                        Text(
                            text = item,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
                // Fill remaining space if row is not complete
                repeat(columns - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

// =============================================================================
// COMPONENTE 6: ActionButtonsRow - Botones de acción Cancelar/Aplicar
// =============================================================================

/**
 * Fila de botones de acción al final del formulario.
 * Usado en las 3 tabs del filtro.
 */
@Composable
fun ActionButtonsRow(
    onCancelClick: () -> Unit,
    onApplyClick: () -> Unit,
    cancelText: String = "Cancelar",
    applyText: String = "Aplicar filtros",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(
            onClick = onCancelClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(cancelText)
        }
        Button(
            onClick = onApplyClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(applyText)
        }
    }
}

// =============================================================================
// COMPONENTE 7: SectionTitle - Título de sección reutilizable
// =============================================================================

/**
 * Título de sección con estilo consistente.
 */
@Composable
fun SectionTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        modifier = modifier.padding(vertical = 8.dp)
    )
}

// =============================================================================
// COMPONENTE 8: FilterSlidersGroup - Grupo de 3 sliders comunes
// =============================================================================

/**
 * Grupo de los 3 sliders que se repiten en cada tab:
 * Calificación, Precio y Distancia.
 */
@Composable
fun FilterSlidersGroup(
    ratingRange: ClosedFloatingPointRange<Float>,
    onRatingChange: (ClosedFloatingPointRange<Float>) -> Unit,
    priceRange: IntRange,
    onPriceChange: (IntRange) -> Unit,
    distanceRange: ClosedFloatingPointRange<Float>,
    onDistanceChange: (ClosedFloatingPointRange<Float>) -> Unit,
    modifier: Modifier = Modifier
) {
    // Rating Slider
    RangeSliderSection(
        label = "Calificación",
        value = ratingRange,
        onValueChange = onRatingChange,
        valueRange = 0f..5f,
        steps = 4,
        valueFormatter = { String.format("%.1f", it) },
        modifier = modifier
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Price Slider
    RangeSliderSection(
        label = "Precio",
        value = priceRange.start.toFloat()..priceRange.endInclusive.toFloat(),
        onValueChange = { 
            onPriceChange(it.start.toInt()..it.endInclusive.toInt()) 
        },
        valueRange = 0f..1000f,
        steps = 99,
        valueFormatter = { "$${it.toInt()}" },
        modifier = modifier
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Distance Slider
    RangeSliderSection(
        label = "Distancia",
        value = distanceRange,
        onValueChange = onDistanceChange,
        valueRange = 0f..100f,
        steps = 99,
        valueFormatter = { String.format("%.0f km", it) },
        modifier = modifier
    )

    Spacer(modifier = Modifier.height(12.dp))
}
