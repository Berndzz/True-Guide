package com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hardus.auth.components.anim_component.EmptyAnim
import com.hardus.trueagencyapp.ui.theme.TrueAgencyAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserFormScreen(onNavigate: () -> Unit, onFormPage: () -> Unit) {
    val teks = ""
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
            Column(modifier = Modifier.padding(paddingValue)) {
                if (teks.isNotEmpty()) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = teks)
                    }
                } else {
                    EmptyAnim()
                }
            }
        }
    )
}


@Preview
@Composable
fun CheckUserFormScreen() {
    TrueAgencyAppTheme {
        UserFormScreen(onNavigate = {}, onFormPage = {})
    }
}