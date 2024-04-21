package com.hardus.trueagencyapp.main_content.home.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.RequestPermission
import com.hardus.trueagencyapp.auth.viewmodel.AuthViewModel
import com.hardus.trueagencyapp.main_content.home.domain.AktivitasApi
import com.hardus.trueagencyapp.main_content.home.domain.model.HomeViewModel
import com.hardus.trueagencyapp.main_content.home.feature_userForm.domain.model.FormViewModel


@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    formViewModel: FormViewModel,
    onUserForm: () -> Unit,
    onNote: () -> Unit,
    onScan: () -> Unit,
    onMember: () -> Unit,
) {
    val authViewModel = hiltViewModel<AuthViewModel>()
    val programs = viewModel.activityProgram
    val personalData by formViewModel.personalData
    val isLoading = viewModel.isLoading.value

    Scaffold(topBar = {
        TopAppBarHome()
    }, content = { paddingValue ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Surface {
                        LeaderStatus(
                            name = authViewModel.currentUser?.displayName ?: "",
                            status = personalData?.leaderStatus ?: " ",
                            hierarchy = personalData?.leaderTitle ?: " ",
                            onUserForm = onUserForm
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Surface {
                        Menu(onNote = onNote, onScan = onScan, onMember = onMember)
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(
                        "Kegiatan di Minggu Ini", style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 20.sp,
                        )
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                }
                listOf(
                    "Training SM7" to "SM7",
                    "Product Knowledge" to "PRODUCT_&_KNOWLEDGE",
                    "Pru Sales Academy" to "PRU_SALES_ACADEMY",
                    "Sales SKill" to "SALES_SKILL",
                    "Personal Excellent Mentality Attitude" to "PERSONAL_EXCELLENT_MENTALITY_ATTITUDE"
                ).forEach { (title, category) ->
                    if (isLoading) {
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        Text(title, modifier = Modifier.padding(start = 16.dp))
                        Row(
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (programs.isNotEmpty()) {
                                programs.filter { it.category == category }
                                    .forEach { aktivitasApi ->
                                        TrainingScreen(aktivitas = aktivitasApi)
                                    }
                            } else {
                                Text("Kegiatan tersebut tidak ada hari ini", color = Color.Gray)
                            }
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                }
            }
        }
    })
}

@Composable
fun PostText(aktivitas: AktivitasApi) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        aktivitas.judul_aktivitas?.let { title ->
            Text(
                text = title, color = Color.Black, maxLines = 1, overflow = TextOverflow.Ellipsis
            )
        }
        aktivitas.hari_aktivitas?.let { day ->
            Text(text = "($day)", maxLines = 1)
        }
        Spacer(Modifier.padding(3.dp))
        Divider(
            color = Color.White, thickness = 2.dp, modifier = Modifier.background(
                shape = MaterialTheme.shapes.small, color = Color.Black
            )
        )
        Spacer(Modifier.padding(1.dp))
    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun PostImage(aktivitas: AktivitasApi, modifier: Modifier = Modifier) {
    val urlImage = aktivitas.gambar_aktivitas
    val painter = if (!urlImage.isNullOrEmpty()) {
        rememberAsyncImagePainter(model = urlImage)
    } else {
        painterResource(id = R.drawable.placeholder)
    }
    Image(
        painter = painter, contentDescription = aktivitas.judul_aktivitas, // decorative
        modifier = modifier.fillMaxSize(), contentScale = ContentScale.FillWidth
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCard(
    aktivitas: AktivitasApi
) {
    var openDialog by remember { mutableStateOf(false) }
    Card(modifier = Modifier
        .width(155.dp)
        .height(144.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
        onClick = { openDialog = true }) {
        Column {
            PostText(aktivitas = aktivitas)
            PostImage(aktivitas = aktivitas)
        }
    }
    if (openDialog) {
        AlertDialog(modifier = Modifier.height(350.dp),
            onDismissRequest = { openDialog = false },
            title = {
                aktivitas.judul_aktivitas.let { title ->
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            text = {
                aktivitas.deskripsi_aktivitas?.let { description ->
                    Text(
                        text = description, style = MaterialTheme.typography.bodyLarge
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    },
                    modifier = Modifier.padding(15.dp),
                ) {
                    Text("Close")
                }
            })
    }
}

@Composable
fun TrainingScreen(aktivitas: AktivitasApi) {
    // Menampilkan setiap aktivitas dalam baris horizontal
    PostCard(aktivitas = aktivitas)
}


@Composable
fun Menu(onNote: () -> Unit, onScan: () -> Unit, onMember: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(
                color = MaterialTheme.colorScheme.inverseSurface,
                shape = MaterialTheme.shapes.medium
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Surface(
            modifier = Modifier.size(30.dp),
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.small,
        ) {
            Icon(
                imageVector = Icons.Outlined.NoteAlt,
                contentDescription = "note",
                modifier = Modifier
                    .size(30.dp)
                    .background(Color.Transparent)
                    .clickable {
                        onNote()
                    },
                tint = MaterialTheme.colorScheme.onSecondary,
            )
        }
        Divider(
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier
                .width(3.dp)
                .fillMaxHeight()
        )
        Surface(
            modifier = Modifier.size(30.dp),
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.small,
        ) {
            RequestPermission()
            Icon(
                imageVector = Icons.Outlined.QrCodeScanner,
                contentDescription = "qr code",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        onScan()
                    },
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
        Divider(
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier
                .width(3.dp)
                .fillMaxHeight()
        )
        Surface(
            modifier = Modifier.size(30.dp),
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.small,
        ) {
            Icon(
                imageVector = Icons.Outlined.PeopleAlt,
                contentDescription = "members",
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onMember() },
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarHome() {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onSecondary
        ), title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    painter = painterResource(R.drawable.vector),
                    contentDescription = stringResource(R.string.logo_app)
                )
            }
        }, scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun LeaderStatus(name: String, status: String, hierarchy: String, onUserForm: () -> Unit) {
    val painter = painterResource(R.drawable.icon_true)
    Row {
        Card(
            modifier = Modifier.size(45.dp),
            shape = CircleShape,
            border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary),
            onClick = onUserForm
        ) {
            Image(
                painter = painter, contentDescription = "Leader Image"
            )
        }
        Column(modifier = Modifier.padding(start = 20.dp)) {
            Text(text = name)
            Row {
                Text(text = status)
                if (hierarchy.isEmpty()) {
                    Text(text = " ")
                } else {
                    Text(text = " | $hierarchy")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckImageLoader() {
    LeaderStatus(name = "Anna", status = "Leader", hierarchy = "AAD", onUserForm = {})
}

@Preview(showBackground = true)
@Composable
fun CheckMenu() {
    Menu(onNote = {}, onScan = {}, onMember = {})
}


