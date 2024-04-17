package com.hardus.trueagencyapp.util

sealed class QrScanState {
    object Idle : QrScanState()
    object Loading : QrScanState()
    object Success : QrScanState()
    data class Error(val message: String) : QrScanState()
}