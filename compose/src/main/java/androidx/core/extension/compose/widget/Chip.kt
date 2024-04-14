package androidx.core.extension.compose.widget

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.extension.compose.horizontalPadding

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> SimpleFlowRowChip(
    chip: List<T>,
    onText: (T) -> String,
    onClick: (T) -> Unit
) {
    FlowRow {
        chip.forEach { item ->
            SimpleChip(item, onText = onText) { onClick(item) }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> SimpleFlowRowHorizontalScrollChip(
    chip: List<T>,
    onText: (T) -> String,
    onClick: (T) -> Unit
) {
    FlowRow(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        chip.forEach { item ->
            SimpleChip(item, onText = onText) { onClick(item) }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> SimpleFlowRowVerticalScrollChip(
    chip: List<T>,
    onText: (T) -> String,
    onClick: (T) -> Unit
) {
    FlowRow(modifier = Modifier.verticalScroll(rememberScrollState())) {
        chip.forEach { item ->
            SimpleChip(item, onText = onText) { onClick(item) }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SimpleChip(
    item: T,
    onText: (T) -> String,
    onClick: () -> Unit,
) {
    Chip(
        modifier = Modifier.horizontalPadding(3.dp),
        onClick = onClick,
    ) { Text(onText(item)) }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SimpleFilterChip(
    item: T,
    selected: Boolean = false,
    onText: (T) -> String,
    onClick: () -> Unit,
) {
    FilterChip(
        selected = selected,
        modifier = Modifier.horizontalPadding(3.dp),
        onClick = onClick,
    ) { Text(onText(item)) }
}