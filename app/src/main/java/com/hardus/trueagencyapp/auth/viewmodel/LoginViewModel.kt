package com.hardus.trueagencyapp.auth.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hardus.trueagencyapp.auth.data.RegistrationUIState
import com.hardus.trueagencyapp.auth.data.UIEvent

class LoginViewModel : ViewModel() {
    var registrationUIState = mutableStateOf(RegistrationUIState())

    fun onEvent(event: UIEvent) {
        when (event) {
            is UIEvent.UsernameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    username = event.username
                )
            }

            is UIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
            }

            is UIEvent.PhoneNumberChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    phoneNumber = event.phoneNumber
                )
            }

            is UIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
            }
        }
    }

}