package com.hardus.trueagencyapp.main_content.home.feature_qrcode.domain.repo

import kotlinx.coroutines.flow.Flow

interface QrcodeRepo {
    fun startScanning(): Flow<String?>
    fun saveToFirestore(detail: String, userId: String)
}