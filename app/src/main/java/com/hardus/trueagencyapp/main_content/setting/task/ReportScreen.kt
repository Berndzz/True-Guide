package com.hardus.auth.screen.view.setting.task

import android.text.util.Linkify
import android.widget.TextView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(onNavigate: () -> Unit) {
    val context = LocalContext.current
    val link_wa = "https://api.whatsapp.com/send?phone=+6282198578051"
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = "Laporan Keluhan",
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
                navigationIcon = {
                    IconButton(onClick = onNavigate) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                })
        },
        content = { paddingValue ->
            Surface(
                modifier = Modifier
                    .padding(paddingValue)
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Bila mengalami kendala atau kesulitan lain terkait aplikasi, silahkan hubungi nomor ini. Terimakasih",
                        textAlign = TextAlign.Center
                    )
                    AndroidView(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp), factory = {
                        val textView = TextView(context)
                        textView.text = link_wa
                        textView.textSize = 17.sp.value
                        Linkify.addLinks(textView, Linkify.ALL)
                        textView
                    })
                }
            }
        }
    )
}