package com.hardus.trueagencyapp.main_content.home.feature_userForm.data

import com.google.firebase.Timestamp

enum class FormQuestion {
    PAGE_ONE, PAGE_TWO, PAGE_THREE
}

data class DirectionPage(
    val formIndex: Int,
    val formCount: Int,
    val shouldShowPreviousButton: Boolean,
    val shouldShowDoneButton: Boolean,
    val formQuestion: FormQuestion,
)


data class PersonalData(
    val userId: String? = "", // ID pengguna, diperoleh dari Firebase Authentication
    val fullName: String = "",
    val address: String = "",
    val dateOfBirth: Timestamp? = null, // Gunakan `null` sebagai default untuk `@ServerTimestamp` agar Firestore menetapkannya
    val leaderStatus: String = "", // "Leader" atau "Business Partner"
    val leaderTitle: String = "-", // Judul jika pengguna adalah leader
    val isBusinessPartner: Boolean = false, // Atau bisa dihapus jika leaderStatus sudah mencakup ini
    val agentCode: String = "",
    val ajjExamDate: Timestamp? = null, // Gunakan Timestamp untuk manfaatkan query berdasarkan tanggal
    val aasiExamDate: Timestamp? = null,
    val selectedUnit: String = "", // ID unit atau nama yang dipilih pengguna
    val vision: String = "",
    val lifeMoto: String = ""
)

data class UserOption(
    val userId: String,
    val userD: String,
    val displayName: String
)

