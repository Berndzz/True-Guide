package com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation.components

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DatePickerForm(
    label: String,
    date: LocalDate,
    onDateChanged: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val dateString = remember(date) { date.format(DateTimeFormatter.ofPattern("dd  MMMM  yyyy")) }
    Text(text = label, fontSize = 20.sp)
    Button(
        onClick = {
            val datePickerDialog = DatePickerDialog(
                context, { _, year, month, dayOfMonth ->
                    onDateChanged(LocalDate.of(year, month + 1, dayOfMonth))
                },
                date.year,
                date.monthValue - 1,
                date.dayOfMonth
            )
            datePickerDialog.show()
        },
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f))
    ) {
        Text(
            text = dateString,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.8f),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f),
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}


