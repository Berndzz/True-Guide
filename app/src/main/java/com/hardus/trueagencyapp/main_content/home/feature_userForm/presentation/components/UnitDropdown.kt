package com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal val DropdownMenuVerticalPadding = 8.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitDropdown(
    textLabel: String,
    selectedUnit: String?,
    onUnitSelected: (String) -> Unit,
    unitOptions: List<String>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var isExpanded by remember { mutableStateOf(false) }
    val displayText = selectedUnit ?: "Pilih Unit"
    val itemHeights = remember { mutableStateMapOf<Int, Int>() }
    val baseHeight = 330.dp
    val density = LocalDensity.current
    val maxHeight = remember(itemHeights.toMap()) {
        if (itemHeights.keys.toSet() != displayText.indices.toSet()) {
            // if we don't have all heights calculated yet, return default value
            return@remember baseHeight
        }
        val baseHeightInt = with(density) { baseHeight.toPx().toInt() }

        // top+bottom system padding
        var sum = with(density) { DropdownMenuVerticalPadding.toPx().toInt() } * 2
        for ((i, itemSize) in itemHeights.toSortedMap()) {
            sum += itemSize
            if (sum >= baseHeightInt) {
                return@remember with(density) { (sum - itemSize / 2).toDp() }
            }
        }
        // all items fit into base height
        baseHeight
    }

    LaunchedEffect(isExpanded) {
        if (isExpanded) {
            // scroll to the last item
            scrollState.scrollTo(scrollState.maxValue)
        }
    }

    Text(text = textLabel, fontSize = 20.sp, modifier = Modifier.padding(5.dp))

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it },
        modifier = Modifier.requiredSizeIn(maxHeight = maxHeight)
    ) {
        TextField(
            value = displayText,
            onValueChange = {

            },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(focusedTextColor = MaterialTheme.colorScheme.onBackground),
            modifier = modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            unitOptions.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(text = unit, color = MaterialTheme.colorScheme.onBackground) },
                    onClick = {
                        onUnitSelected(unit)
                        isExpanded = false
                    }
                )
            }
        }
    }
}
