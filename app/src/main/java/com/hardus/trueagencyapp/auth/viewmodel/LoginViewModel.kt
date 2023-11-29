package com.hardus.trueagencyapp.auth.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.hardus.trueagencyapp.auth.component.rules.Validator
import com.hardus.trueagencyapp.auth.data.RegistrationUIState
import com.hardus.trueagencyapp.auth.data.UIEvent

class LoginViewModel : ViewModel() {
    private val TAG = LoginViewModel::class.simpleName
    var registrationUIState = mutableStateOf(RegistrationUIState())
    var allValidatePass = mutableStateOf(false)

    fun onEvent(event: UIEvent) {

        when (event) {
            is UIEvent.UsernameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    username = event.username
                )

                printState()
            }

            is UIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
                printState()
            }

            is UIEvent.PhoneNumberChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    phoneNumber = event.phoneNumber
                )
                printState()
            }

            is UIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
                printState()
            }

            is UIEvent.RegisterButtonClicked -> {
                register()
            }

            is UIEvent.PrivacyPolicyCheckBoxClicked -> {
                registrationUIState.value = registrationUIState.value.copy(
                    privacyPolicyAccepted = event.status
                )
            }
        }
        validateDataWithRules()
    }

    private fun register() {
        Log.d(TAG, "Inside_register()")
        printState()
        createUserFirebase(
            email = registrationUIState.value.email, password = registrationUIState.value.password
        )
    }

    private fun validateDataWithRules() {
        val usernameResult = Validator.validateUsername(
            username = registrationUIState.value.username
        )
        val emailResult = Validator.validateEmail(
            email = registrationUIState.value.email
        )
        val phoneNResult = Validator.validatePhoneNumber(
            phoneN = registrationUIState.value.phoneNumber
        )
        val passwordResult = Validator.validatePassword(
            password = registrationUIState.value.password
        )
        val privacyPolicyResult = Validator.validatePrivacyPolicyAcceptance(
            statusValue = registrationUIState.value.privacyPolicyAccepted
        )

        Log.d(TAG, "Inside_validateDataWithRules")
        Log.d(TAG, "usernameResult = $usernameResult")
        Log.d(TAG, "emailResult = $emailResult")
        Log.d(TAG, "phoneNResult = $phoneNResult")
        Log.d(TAG, "passwordResult = $passwordResult")
        Log.d(TAG, "privacyPolicyResult = $privacyPolicyResult")

        registrationUIState.value = registrationUIState.value.copy(
            usernameError = usernameResult.status,
            emailError = emailResult.status,
            phoneNumberError = phoneNResult.status,
            passwordError = passwordResult.status,
            privacyPolicyError = privacyPolicyResult.status
        )

        allValidatePass.value =
            usernameResult.status && emailResult.status && phoneNResult.status && passwordResult.status && privacyPolicyResult.status
    }

    private fun printState() {
        Log.d(TAG, "Inside PrintState")
        Log.d(TAG, registrationUIState.value.toString())
    }

    private fun createUserFirebase(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG, "Insider_onCompleteListener")
                Log.d(TAG, "${it.isSuccessful}")
            }.addOnFailureListener {
                Log.d(TAG, "Inside_onFailureListener")
                Log.d(TAG, "Exception ${it.message}")
                Log.d(TAG, "Exception ${it.localizedMessage}")
            }
    }
}