package com.hardus.trueagencyapp.main_content.absent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun AbsentScreen(navController: NavController) {
    Column(
        modifier = Modifier.padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Absent Screen", style = MaterialTheme.typography.displaySmall)
    }
}

@Preview
@Composable
fun CheckAbsentScreen() {
    AbsentScreen(rememberNavController())
}