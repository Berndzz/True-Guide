package com.hardus.trueagencyapp.main_content.home.feature_qrcode.data.repo

import android.util.Log
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.hardus.trueagencyapp.main_content.home.feature_qrcode.domain.repo.QrcodeRepo
import com.hardus.trueagencyapp.main_content.home.presentation.util.isWithinAbsenceWindow
import com.hardus.trueagencyapp.main_content.home.presentation.util.toJsonString
import com.hardus.trueagencyapp.util.FirestoreService
import com.hardus.trueagencyapp.util.await
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class QrcodeRepoImpl @Inject constructor(
    private val scanner: GmsBarcodeScanner
) : QrcodeRepo {
    override fun startScanning(): Flow<String?> {
        return callbackFlow {
            scanner.startScan()
                .addOnSuccessListener {
                    launch {
                        send(getDetail(it))
                    }
                }.addOnFailureListener {
                    it.printStackTrace()
                }
            awaitClose { }
        }
    }

    override fun saveToFirestore(detail: String, userId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val detailObj = JSONObject(detail)
                if (detailObj.has("error")) {
                    Log.e(TAG, "Error in scanned data: ${detailObj.getString("error")}")
                    return@launch
                }
                val namaAcara = detailObj.optString("namaAcara", "Nama acara tidak dikenal")
                val program = detailObj.optString("program", "Program tidak dikenal")
                val lokasi = detailObj.optString("lokasi", "Lokasi tidak dikenal")
                val tabName = detailObj.optString("tabName", "Unknown")

                // Contoh: Tanggal dan jam mulai acara
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                val eventStartDateStr =
                    detailObj.getString("tanggalAcara") + " " + detailObj.getString("jamMulai")
                val eventEndDateStr =
                    detailObj.getString("tanggalAcara") + " " + detailObj.getString("jamSelesai")
                val userUnit = getUserUnit(userId) ?: "Unit tidak dikenal"

                val eventStartTime = dateFormat.parse(eventStartDateStr)
                val eventEndTime = dateFormat.parse(eventEndDateStr)
                val currentDate = Date()

                val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentDate)
                // Menentukan kehadiran berdasarkan waktu pemindaian
                val kehadiran = if (eventStartTime != null && eventEndTime != null &&
                    isWithinAbsenceWindow(currentDate, eventStartTime, eventEndTime)
                )
                    "Hadir"
                else
                    "Tidak Hadir"

                val scanData = hashMapOf(
                    "namaAcara" to namaAcara,
                    "program" to program,
                    "tabName" to tabName,
                    "tanggalAcara" to formattedDate,
                    "lokasi" to lokasi,
                    "kehadiran" to kehadiran,
                    "unit" to userUnit
                )

                val scansCollection = FirestoreService.db.collection("scans").document(userId).collection("idScan")

                val existingScanQuery = scansCollection
                    .whereEqualTo("namaAcara", namaAcara)
                    .whereEqualTo("tanggalAcara", formattedDate)

                existingScanQuery.get().addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.isEmpty) {
                        // Tidak ada scan yang sama, tambahkan entri baru di 'scans'
                        scansCollection.add(scanData)
                            .addOnSuccessListener { documentReference ->
                                Log.d(TAG, "Scan data successfully saved with new Document ID: ${documentReference.id}")

                                // Juga tambahkan entri baru di 'organization'
                                val organizationDocRef = FirestoreService.db.collection("organization").document(userUnit)
                                val memberDocRef = organizationDocRef.collection("members").document(userId)
                                val scansSubCollectionRef = memberDocRef.collection("PersonalData").document("Scans").collection("idScans")

                                // Tambahkan data scan ke 'organization'
                                scansSubCollectionRef.add(scanData)
                                    .addOnSuccessListener { orgDocRef ->
                                        Log.d(TAG, "Scanned data also successfully saved in the organization collection with autogenerated ID: ${orgDocRef.id}")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(TAG, "Error saving scanned data in the organization collection", e)
                                    }
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding new scan data", e)
                            }
                    } else {
                        // Entri duplikat ditemukan, tidak menambahkan
                        Log.d(TAG, "Duplicate scan entry found. Not adding new scan data.")
                    }
                }.addOnFailureListener { e ->
                    Log.w(TAG, "Error querying for existing scans", e)
                }

            } catch (e: JSONException) {
                Log.e(TAG, "Error parsing scan detail JSON", e)
            } catch (e: Exception) {
                Log.e(TAG, "An error occurred while saving scan details", e)
            }
        }
    }

    private fun getDetail(barcode: Barcode): String {
        return when (barcode.valueType) {
            Barcode.TYPE_TEXT -> {
                "${barcode.rawValue}"
            }

            else -> {
                "Couldn't determine"
            }
        }
    }

    suspend fun getUserUnit(userId: String): String? {
        return try {
            // Gunakan Coroutine untuk menjalankan operasi Firestore secara asinkron
            val documentSnapshot = withContext(Dispatchers.IO) {
                FirestoreService.db.collection("usersData")
                    .document(userId)
                    .get()
                    .await() // Menggunakan 'await' dari kotlinx.coroutines untuk menunggu hasil
            }

            // Cek apakah dokumen berhasil didapatkan dan ekstrak unit
            if (documentSnapshot.exists()) {
                documentSnapshot.getString("selectedUnit")
            } else {
                null
            }
        } catch (e: Exception) {
            // Log error atau handle exception
            null
        }
    }

    companion object {
        private const val TAG = "MyClassName"
    }
}