package com.hardus.trueagencyapp.main_content.home.feature_qrcode.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardus.trueagencyapp.main_content.home.feature_qrcode.domain.repo.QrcodeRepo
import com.hardus.trueagencyapp.util.FirestoreAuth
import com.hardus.trueagencyapp.util.QrScanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrScanViewModel @Inject constructor(
    private val repo: QrcodeRepo
) : ViewModel() {
    val userId = FirestoreAuth.db.currentUser?.uid
    private val _state = MutableStateFlow<QrScanState>(QrScanState.Idle)
    val state: StateFlow<QrScanState> = _state

    fun startScanningAndSaveToFirestore(scannedData: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                // Update status menjadi Loading sebelum melakukan operasi pemindaian dan penyimpanan ke Firestore
                _state.value = QrScanState.Loading

                // Lakukan pemindaian dan simpan ke Firestore
                if (userId != null) {
                    repo.saveToFirestore(scannedData, userId) { success ->
                        if (success) {
                            // Jika operasi berhasil, update status menjadi Success
                            _state.value = QrScanState.Success
                            callback(true)
                        } else {
                            // Jika terjadi kesalahan, update status menjadi Error
                            _state.value = QrScanState.Error("Error saving scan details")
                            callback(false)
                        }
                    }
                }
            } catch (e: Exception) {
                // Jika terjadi exception, update status menjadi Error
                _state.value = QrScanState.Error("Error processing QR code")
                callback(false)
            }
        }
    }
}
