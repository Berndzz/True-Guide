package com.hardus.trueagencyapp.main_content.absent.data

data class AbsentBreed(
    val idAbsent: String? = null, // Jika idAbsent adalah opsional
    val name: String = "",
    val description: String? = null,
    val subAbsentList: List<SubAbsent> = listOf()
)

data class SubAbsent(
    val idUser: String? = null, // Jika idUser adalah opsional
    val idSubAbsent: String = "",
    val headerTitle: String = "",
    val tabItem: String = "",
    val bodyTitle: String = "",
    val date: String = "",
    val location: String = "",
    val present: Boolean? = null // Jika present adalah opsional
)