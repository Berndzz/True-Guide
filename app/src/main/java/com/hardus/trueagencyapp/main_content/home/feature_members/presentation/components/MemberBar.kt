package com.hardus.trueagencyapp.main_content.home.feature_members.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hardus.trueagencyapp.main_content.home.feature_members.data.AbsentBreedMember
import com.hardus.trueagencyapp.main_content.home.feature_members.data.SubAbsentMember

@Composable
fun AbsentContentMember(
    modifier: Modifier = Modifier,
    onBreedSelected: (AbsentBreedMember) -> Unit,
    selectedBreed: AbsentBreedMember?,
    absentBreeds: List<AbsentBreedMember>,
) {

    if (absentBreeds.isNotEmpty() && selectedBreed != null) {
        Column(modifier = modifier) {
            AbsentTabsMember(
                absentBreed = absentBreeds,
                selectedBreed = selectedBreed,
                onBreedSelected = onBreedSelected,
                modifier = Modifier.fillMaxWidth()
            )
            if (selectedBreed.subAbsentList.isNotEmpty()) {
                LazyColumn(modifier = Modifier.padding(10.dp)) {
                    items(selectedBreed.subAbsentList) { subAbsent ->
                        CardAbsentMember(subAbsent = subAbsent)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            } else {
                Text(
                    text = "Absent Masih Kosong untuk\n ${selectedBreed.name}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )
            }
        }
    } else {
        Text(text = "Not Found")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardAbsentMember(
    subAbsent: SubAbsentMember, modifier: Modifier = Modifier
) {
    var openDialog by remember { mutableStateOf(false) }
    val buttonColor = if (subAbsent.present == true) Color.Green else Color.Red
    val buttonText = if (subAbsent.present == true) "Hadir" else "Tidak Hadir"

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondary),
        shape = MaterialTheme.shapes.small,
        onClick = { openDialog = true }
    ) {
        HeaderCardMember(subAbsent = subAbsent)
        BodyAbsentMember(subAbsent = subAbsent)
        Spacer(modifier = Modifier.padding(10.dp))
        Divider(
            thickness = 4.dp,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
        )
        Spacer(modifier = Modifier.padding(3.dp))
        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(
                    color = buttonColor,
                    shape = RoundedCornerShape(16.dp) // Ubah nilai radius sesuai kebutuhan
                )
        ) {
            Text(
                text = buttonText,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            )

        }
    }
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            title = {
                Column {
                    Text(
                        text = subAbsent.headerTitle,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.padding(3.dp))
                    Text(
                        text = subAbsent.bodyTitle,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            },
            text = {
                Column {
                    Text(
                        text = subAbsent.date,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.padding(3.dp))
                    Text(
                        text = subAbsent.location,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.padding(3.dp))
                    Text(
                        text = buttonText,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    },
                    modifier = Modifier
                        .padding(15.dp),
                ) {
                    Text("Close")
                }
            },
        )
    }
}

@Composable
fun HeaderCardMember(subAbsent: SubAbsentMember) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 15.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Text(
            text = subAbsent.headerTitle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 10.dp),
        )
        Divider(
            color = Color.Black,
            modifier = Modifier
                .heightIn(min = 24.dp) // Sesuaikan tinggi divider sesuai kebutuhan
                .width(2.dp),
        )
        Text(
            text = subAbsent.bodyTitle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 10.dp),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif
            ),
        )
    }
}

@Composable
fun BodyAbsentMember(subAbsent: SubAbsentMember) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Text(
            text = subAbsent.date,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.LightGray
            ),
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Text(
            text = subAbsent.location,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.LightGray
            )
        )
    }
}