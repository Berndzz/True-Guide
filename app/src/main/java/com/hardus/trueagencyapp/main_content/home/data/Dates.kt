package com.hardus.trueagencyapp.main_content.home.data

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Program(
    val id_program: Long = 0L, // Atau String, tergantung pada tipe data Anda
    val judul_program: String = "",
    val deskripsi_program: String = "",
    val deskripsi_program2: String = "",
    val photo_program: String = "" // Asumsi Anda menyimpan URL gambar
    //Add body_program
)

data class Aktivitas(
    val id_aktivitas: Long = 0L, // Atau String
    val judul_aktivitas: String = "",
    val deskripsi_aktivitas: String = "",
    val gambar_aktivitas: String = "",
    @ServerTimestamp
    val hari_aktivitas: Date = Date() ,// Firebase menggunakan Timestamp untuk data waktu
    val body_aktivitas: String = ""
)

data class ProgramWithActivities(
    val program: Program,
    val activities: List<Aktivitas>
)