package com.hardus.trueagencyapp.auth.data

sealed class UIEvent {
    data class UsernameChanged(val username: String) : UIEvent()
    data class EmailChanged(val email: String) : UIEvent()
    data class PhoneNumberChanged(val phoneNumber: String) : UIEvent()
    data class PasswordChanged(val password: String) : UIEvent()

    data class PrivacyPolicyCheckBoxClicked(val status: Boolean) : UIEvent()
    object RegisterButtonClicked : UIEvent()
}