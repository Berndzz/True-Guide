package com.hardus.trueagencyapp.auth.data.register

sealed class RegisterUIEvent {
    data class UsernameChanged(val username: String) : RegisterUIEvent()
    data class EmailChanged(val email: String) : RegisterUIEvent()
    data class PhoneNumberChanged(val phoneNumber: String) : RegisterUIEvent()
    data class PasswordChanged(val password: String) : RegisterUIEvent()

    data class PrivacyPolicyCheckBoxClicked(val status: Boolean) : RegisterUIEvent()
    object RegisterButtonClicked : RegisterUIEvent()
}