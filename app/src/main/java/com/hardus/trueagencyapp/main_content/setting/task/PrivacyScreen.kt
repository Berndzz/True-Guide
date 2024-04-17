package com.hardus.auth.screen.view.setting.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hardus.trueagencyapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyScreen(onNavigate: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Kebijakan Privasi") },
                navigationIcon = {
                    IconButton(onClick = onNavigate) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                })
        },
        content = { paddingValue ->
            Surface(modifier = Modifier
                .padding(paddingValue)
                .padding(start = 20.dp, end = 20.dp)) {
                LazyColumn(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item{
                        Text(text = stringResource(id = R.string.privacy_setting))
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(text = stringResource(id = R.string.privacy_setting2))
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(text = stringResource(id = R.string.privacy_setting3))
                    }
                }
            }
        }
    )
}