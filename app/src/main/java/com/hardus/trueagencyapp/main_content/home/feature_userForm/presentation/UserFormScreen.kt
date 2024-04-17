package com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.hardus.auth.components.anim_component.EmptyAnim
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.main_content.home.feature_userForm.domain.model.FormViewModel
import com.hardus.trueagencyapp.ui.theme.TrueAgencyAppTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserFormScreen(viewModel: FormViewModel, onNavigate: () -> Unit, onFormPage: () -> Unit) {
    val personalData by viewModel.personalData
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    Text(
                        "Personal Data",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigate) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onFormPage) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },
        content = { paddingValue ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(paddingValue)
                    .padding(10.dp),
//                verticalArrangement = Arrangement.Top,
//                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (personalData != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(color = MaterialTheme.colorScheme.onSecondary)
                    ) {
                        LazyColumn(modifier = Modifier.padding(10.dp)) {
                            item {
                                Text("Nama Lengkap: ${personalData?.fullName ?: stringResource(R.string.tidak_tersedia)}  ")
                                Text("Alamat: ${personalData?.address ?: stringResource(R.string.tidak_tersedia)}")
                                Text("Nomor Telepon: ${personalData?.phoneNumber ?: stringResource(R.string.tidak_tersedia)}")
                                Text(
                                    "Tanggal Lahir: ${
                                        personalData?.dateOfBirth?.toDateA()
                                            ?.formatToStrinAg() ?: stringResource(R.string.tidak_tersedia)
                                    }"
                                )
                                Text(
                                    "Partner Business / Leader: ${
                                        personalData?.leaderStatus ?: stringResource(
                                            R.string.tidak_tersedia
                                        )
                                    }"
                                )
                                if (personalData?.leaderTitle!!.isNotEmpty()) {
                                    Text(
                                        "Status Leader: ${
                                            personalData?.leaderTitle ?: stringResource(
                                                R.string.tidak_tersedia
                                            )
                                        }"
                                    )
                                }
                                Text("Kode Agent: ${personalData?.agentCode ?: stringResource(R.string.tidak_tersedia)}")
                                Text(
                                    "Tanggal Ujian AAJI: ${
                                        personalData?.ajjExamDate?.toDateA()
                                            ?.formatToStrinAg() ?: stringResource(R.string.tidak_tersedia)
                                    }"
                                )
                                Text(
                                    "Tanggal Ujian AASI: ${
                                        personalData?.aasiExamDate?.toDateA()
                                            ?.formatToStrinAg() ?: stringResource(R.string.tidak_tersedia)
                                    }"
                                )
                                Text("Unit: ${personalData?.selectedUnit ?: stringResource(R.string.tidak_tersedia)}")
                                Text("Visi: ${personalData?.vision ?: stringResource(R.string.tidak_tersedia)}")
                                Text("Moto Hidup: ${personalData?.lifeMoto ?: stringResource(R.string.tidak_tersedia)}")
                            }
                        }
                    }
                } else {
                    EmptyAnim()
                }
            }
        }
    )
}

fun Timestamp?.toDateA(): Date? = this?.toDate()
fun Date?.formatToStrinAg(): String {
    // Format tanggal ke string sesuai kebutuhan Anda
    return SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(this)
}

@Preview
@Composable
fun CheckUserFormScreen() {
    TrueAgencyAppTheme {
        UserFormScreen(viewModel = FormViewModel(), onNavigate = {}, onFormPage = {})
    }
}