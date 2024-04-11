package com.hardus.trueagencyapp.main_content.home.domain

import com.hardus.trueagencyapp.main_content.home.data.ProgramWithActivities
import retrofit2.Response
import retrofit2.http.GET
data class AktivitasApi(
    val category: String,
    val body_aktivitas: String = "",
    val deskripsi_aktivitas: String = "",
    val gambar_aktivitas: String = "",
    val hari_aktivitas: String = "",
    val judul_aktivitas: String = ""
) {
    // Konstruktor tanpa argumen
    constructor() : this("", "", "", "", "", "")
}

