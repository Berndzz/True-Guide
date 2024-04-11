package com.hardus.trueagencyapp.main_content.home.feature_qrcode.presentation

import androidx.lifecycle.ViewModel
import com.hardus.auth.screen.view.home.feature_qrcode.presentation.QrScreenState
import com.hardus.trueagencyapp.main_content.home.feature_qrcode.domain.repo.QrcodeRepo
import com.hardus.trueagencyapp.util.FirestoreAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class QrScanViewModel @Inject constructor(
    private val repo: QrcodeRepo
) : ViewModel() {
    val userId = FirestoreAuth.db.currentUser?.uid
    private val _state = MutableStateFlow(QrScreenState())
    val state = _state.asStateFlow()


    // Fungsi untuk memulai pemindaian kode QR dan menyimpan hasilnya ke Firestore
    fun startScanningAndSaveToFirestore(scannedData: String) {
        // Di sini Anda akan memanggil fungsi pemindaian kode QR dari dependensi Anda.
        // Setelah mendapatkan hasil pemindaian, Anda akan memanggil fungsi saveToFirestore() dari repository.
        // Contoh menggunakan library zxing-android-embedded:
        // val integrator = IntentIntegrator.forSupportFragment(this)
        // integrator.setPrompt("Scan a QR code")
        // integrator.setOrientationLocked(false)
        // integrator.initiateScan()

        // Di sini kita hanya melakukan pemanggilan langsung ke saveToFirestore dengan data yang diduga dipindai
        if (userId != null) {
            repo.saveToFirestore(scannedData, userId)
        }
    }
}