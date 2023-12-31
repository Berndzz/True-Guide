package com.hardus.trueagencyapp.main_content.home.feature_qrcode.data.repo

import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.hardus.trueagencyapp.main_content.home.feature_qrcode.domain.repo.QrcodeRepo
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
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

    private fun getDetail(barcode: Barcode): String {
        return when (barcode.valueType) {
            Barcode.TYPE_WIFI -> {
                val ssid = barcode.wifi!!.ssid
                val password = barcode.wifi!!.password
                val type = barcode.wifi!!.encryptionType
                "ssid: $ssid,password: $password, type: $type"
            }

            Barcode.TYPE_URL -> {
                "url:${barcode.url!!.url}"
            }

            Barcode.TYPE_PRODUCT -> {
                "productType : ${barcode.displayValue}"
            }

            Barcode.TYPE_EMAIL -> {
                "email : ${barcode.email!!.address}\n" +
                        "subject: ${barcode.email!!.subject}\n" +
                        "body: ${barcode.email!!.body}"
            }

            Barcode.TYPE_CONTACT_INFO -> {
                "contact : ${barcode.contactInfo}"
            }

            Barcode.TYPE_PHONE -> {
                "phone : ${barcode.phone}"
            }

            Barcode.TYPE_CALENDAR_EVENT -> {
                "calender event : ${barcode.calendarEvent}"
            }

            Barcode.TYPE_GEO -> {
                "geo point : ${barcode.geoPoint}"
            }

            Barcode.TYPE_ISBN -> {
                "isbn : ${barcode.displayValue}"
            }

            Barcode.TYPE_DRIVER_LICENSE -> {
                "driving license : ${barcode.driverLicense}"
            }

            Barcode.TYPE_SMS -> {
                "sms : ${barcode.sms}"
            }

            Barcode.TYPE_TEXT -> {
                "text : ${barcode.rawValue}"
            }

            Barcode.TYPE_UNKNOWN -> {
                "unknown : ${barcode.rawValue}"
            }

            else -> {
                "Couldn't determine"
            }
        }
    }
}