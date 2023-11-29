package com.hardus.trueagencyapp.auth.data

data class RegistrationUIState(
    var username: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var password: String = "",
    var privacyPolicyAccepted: Boolean = false,

    var usernameError: Boolean = false,
    var emailError: Boolean = false,
    var phoneNumberError: Boolean = false,
    var passwordError: Boolean = false,

    var privacyPolicyError: Boolean = false
)