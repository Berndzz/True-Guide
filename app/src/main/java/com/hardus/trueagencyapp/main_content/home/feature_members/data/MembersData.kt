package com.hardus.trueagencyapp.main_content.home.feature_members.data

data class AbsentBreedMember(
    val idAbsent: String? = null, // Jika idAbsent adalah opsional
    val name: String = "",
    val description: String? = null,
    val subAbsentList: List<SubAbsentMember> = listOf()
)
data class SubAbsentMember(
    val idUser: String? = null,
    var idSubAbsent: String = "",
    val headerTitle: String = "",
    val tabItem: String = "",
    val bodyTitle: String = "",
    val date: String = "",
    val location: String = "",
    val present: Boolean? = null // Jika present adalah opsional
)