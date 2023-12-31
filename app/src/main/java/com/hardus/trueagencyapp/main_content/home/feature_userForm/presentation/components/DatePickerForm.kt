package com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation.components

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
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

@Composable
fun CustomDatePicker(
    selectedDate: LocalDate,
    onDateChanged: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val months =
        listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    val years = (1900..LocalDate.now().year).toList().map(Int::toString)

    var expanded by remember { mutableStateOf(false) }
    val (selectedMonth, setSelectedMonth) = remember { mutableStateOf(months[selectedDate.monthValue - 1]) }
    val (selectedDay, setSelectedDay) = remember { mutableStateOf(selectedDate.dayOfMonth.toString()) }
    val (selectedYear, setSelectedYear) = remember { mutableStateOf(selectedDate.year.toString()) }

    Row(modifier = modifier) {
        Box() {
            //Text(text = "Date of Birth")
            Spacer(modifier = Modifier.height(10.dp))
            // Month Dropdown
            OutlinedTextField(
                value = selectedMonth,
                onValueChange = { },
                label = { Text("Month") },
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        "dropdown",
                        Modifier.clickable { expanded = !expanded })
                },
                modifier = Modifier.width(100.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                months.forEach { month ->
                    DropdownMenuItem(onClick = {
                        setSelectedMonth(month)
                        expanded = false
                        val monthIndex = months.indexOf(month) + 1
                        onDateChanged(selectedDate.withMonth(monthIndex))
                    }) {
                        Text(text = month)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Day Input
        OutlinedTextField(
            value = selectedDay,
            onValueChange = {
                setSelectedDay(it)
                onDateChanged(
                    selectedDate.withDayOfMonth(
                        it.toIntOrNull() ?: selectedDate.dayOfMonth
                    )
                )
            },
            label = { Text("Day") },
            modifier = Modifier.width(64.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Year Input
        OutlinedTextField(
            value = selectedYear,
            onValueChange = {
                setSelectedYear(it)
                onDateChanged(selectedDate.withYear(it.toIntOrNull() ?: selectedDate.year))
            },
            label = { Text("Year") },
            modifier = Modifier.width(80.dp)
        )
    }
}

