package com.hardus.auth.screen.view.home.feature_qrcode.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardus.trueagencyapp.main_content.home.feature_qrcode.domain.repo.QrcodeRepo
import com.hardus.trueagencyapp.util.FirestoreAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrScanViewModel @Inject constructor(
    private val repo: QrcodeRepo
) : ViewModel() {
    private val _state = MutableStateFlow(QrScreenState())
    val state = _state.asStateFlow()

    fun startScanning() {
        viewModelScope.launch {
            repo.startScanning().collect { detail ->
                if (!detail.isNullOrBlank()) {
                    _state.value = state.value.copy(
                        detail = detail
                    )
                }
                detail?.let {
                    // Dapatkan ID pengguna dari FirebaseAuth
                    val userId = FirestoreAuth.db.currentUser?.uid
                    userId?.let { uid ->
                        // Simpan ke Firestore
                        repo.saveToFirestore(detail, uid)
                    }
                }
            }
        }
    }
}