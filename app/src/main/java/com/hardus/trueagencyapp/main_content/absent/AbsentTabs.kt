package com.hardus.trueagencyapp.main_content.absent

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AbsentTabs(
    absentBreed: List<AbsentBreed>,
    selectedBreed: AbsentBreed,
    onBreedSelected: (AbsentBreed) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedIndex = absentBreed.indexOfFirst { it == selectedBreed }
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        edgePadding = 10.dp,
        backgroundColor = MaterialTheme.colorScheme.background,
        divider = {},
        indicator = {},
        modifier = modifier
    ) {
        absentBreed.forEachIndexed { index, absentBreed ->
            Tab(selected = index == selectedIndex, onClick = {
                onBreedSelected(absentBreed)
            }) {
                AbsentBreedChip(
                    breedName = absentBreed.name,
                    selected = index == selectedIndex,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 16.dp)
                )
            }
        }
    }
}

@Composable
fun AbsentBreedChip(
    breedName: String, selected: Boolean, modifier: Modifier = Modifier
) {
    val textColor = if (selected) Color.White else Color.Black
    Surface(
        color = when {
            selected -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f)
        }, contentColor = when {
            selected -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.primary
        }, shape = RoundedCornerShape(30), modifier = modifier
    ) {
        Text(
            text = breedName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = textColor
        )
    }
}
