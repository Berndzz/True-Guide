package com.hardus.trueagencyapp.main_content.home.feature_userForm.data

enum class FormQuestion {
    PAGE_ONE, PAGE_TWO, PAGE_THREE
}

data class PersonalData(
    val formIndex: Int,
    val formCount: Int,
    val shouldShowPreviousButton: Boolean,
    val shouldShowDoneButton: Boolean,
    val formQuestion: FormQuestion,
)

data class FormUIState(
    var fullName: String = "",
    var address: String = "",
)
