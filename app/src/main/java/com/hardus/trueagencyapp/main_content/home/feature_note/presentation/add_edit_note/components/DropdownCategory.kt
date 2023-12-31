package com.hardus.trueagencyapp.main_content.home.feature_note.presentation.add_edit_note.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownCategory(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedCategoryState by rememberSaveable { mutableStateOf(selectedCategory) }
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = if (selectedCategoryState.isNotEmpty()) selectedCategoryState else "Select a category",
            modifier = Modifier
                .clickable { expanded = true }
                .padding(16.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            TextField(
                value = selectedCategoryState,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = {
                            Text(text = category)
                        },
                        onClick = {
                            Log.d("DropdownCategory", "Selected category: $category")
                            selectedCategoryState = category
                            onCategorySelected(category)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}