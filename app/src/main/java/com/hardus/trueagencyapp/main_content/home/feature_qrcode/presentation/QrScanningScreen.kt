package com.hardus.trueagencyapp.main_content.home.feature_qrcode.presentation

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.BeepManager
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.databinding.BarcodeLayoutBinding
import com.hardus.trueagencyapp.nested_navigation.Screen
import com.hardus.trueagencyapp.util.findActivity
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.camera.CameraSettings


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrScanningScreen(
    onNavigate: () -> Unit,
    navController: NavController,
    viewModel: QrScanViewModel
) {

    val context = LocalContext.current
    var scannedData by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope() // Buat scope coroutine

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.scan_code)) },
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
            FloatingActionButton(onClick = {}) {
                Icon(imageVector = Icons.Filled.FlashOn, contentDescription = "qr_icon")
            }
        },
        content = { paddingValue ->
            Column(modifier = Modifier.padding(paddingValue)) {
                AndroidView(factory = {
                    View.inflate(it, R.layout.barcode_layout, null)
                },
                    update = { view ->
                        val beepManager = BeepManager(context.findActivity())
                        beepManager.isBeepEnabled = true
                        beepManager.isVibrateEnabled = true
                        val binding = BarcodeLayoutBinding.bind(view)
                        binding.barcodeView.resume()
                        val s = CameraSettings()
                        s.requestedCameraId = 0 // front/back/etc
                        binding.barcodeView.cameraSettings = s
                        binding.barcodeView.decodeSingle(object : BarcodeCallback {
                            override fun barcodeResult(result: BarcodeResult?) {
                                beepManager.playBeepSound()
                                result?.result?.text?.let { scannedData = it }
                                try {
                                    // Cek apakah hasil pemindaian sesuai dengan format JSON yang diharapkan
                                    if (isValidJson(scannedData)) {
                                        // Panggil fungsi ViewModel untuk menyimpan data hasil pemindaian ke Firestore
                                        viewModel.startScanningAndSaveToFirestore(scannedData)
                                        navController.navigate(Screen.Home.route)
                                        binding.barcodeView.pause()
                                    } else {
                                        // Format QR code tidak sesuai, tampilkan pesan kesalahan
                                        Toast.makeText(
                                            context,
                                            "Invalid QR code format",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        context,
                                        "Error processing QR code",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
                                super.possibleResultPoints(resultPoints)
                            }
                        })
                    })
            }
        }
    )
}

// Fungsi untuk memvalidasi hasil pemindaian QR code
private fun isValidJson(jsonString: String): Boolean {
    return true
}







