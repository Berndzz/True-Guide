package com.hardus.trueagencyapp.main_content.home.feature_qrcode.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
// NANTI DI HAPUS AJA SCREEN INI
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrScanningScreen(onNavigate: () -> Unit) {
    val viewModel: QrScanViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(title = { /*TODO*/ },
                navigationIcon = {
                    IconButton(onClick = onNavigate) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.startScanning() }) {
                Icon(imageVector = Icons.Filled.QrCode, contentDescription = "qr_icon")
            }
        },
        content = { paddingValue ->
            Surface(modifier = Modifier.padding(paddingValue)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                            .weight(0.5f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = state.value.detail)
                    }

                }
            }
        }
    )
}