package com.hardus.trueagencyapp.auth.data

data class RegistrationUIState(
    var username: String = "",
    var email: String = "",
    var phoneNumber: Int = 0,
    var password: String = ""
)