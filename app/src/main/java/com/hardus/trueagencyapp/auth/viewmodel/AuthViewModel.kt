package com.hardus.trueagencyapp.auth.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.hardus.trueagencyapp.component.rules.Validator
import com.hardus.trueagencyapp.auth.data.login.LoginUIEvent
import com.hardus.trueagencyapp.auth.data.login.LoginUIState
import com.hardus.trueagencyapp.auth.data.register.RegisterUIEvent
import com.hardus.trueagencyapp.auth.data.register.RegistrationUIState
import com.hardus.trueagencyapp.firebase.AuthFirebase
import com.hardus.trueagencyapp.firebase.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthFirebase
) : ViewModel() {
    private val TAG = AuthViewModel::class.simpleName

    // Untuk pendaftaran
    var registrationUIState = mutableStateOf(RegistrationUIState())

    // Untuk login
    var loginUIState = mutableStateOf(LoginUIState())

    var allValidatePass = mutableStateOf(false)

    private val _registerFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val registerFlow: StateFlow<Resource<FirebaseUser>?> = _registerFlow

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow


    val currentUser: FirebaseUser?
        get() = repository.currentUser

    init {
        if (repository.currentUser != null) {
            _registerFlow.value = Resource.Success(repository.currentUser!!)
            _loginFlow.value = Resource.Success(repository.currentUser!!)
        }
    }

    fun onEventRegister(event: RegisterUIEvent) {
        when (event) {
            is RegisterUIEvent.UsernameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    username = event.username
                )

                printState()
            }

            is RegisterUIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
                printState()
            }

            is RegisterUIEvent.PhoneNumberChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    phoneNumber = event.phoneNumber
                )
                printState()
            }

            is RegisterUIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
                printState()
            }

            is RegisterUIEvent.RegisterButtonClicked -> {
                register()
            }

            is RegisterUIEvent.PrivacyPolicyCheckBoxClicked -> {
                registrationUIState.value = registrationUIState.value.copy(
                    privacyPolicyAccepted = event.status
                )
            }
        }
        validateDataRegisterWithRules()
    }

    fun onEventLogin(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )

                printState()
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
                printState()
            }

            is LoginUIEvent.LoginButtonClicked -> {
                login()
            }
        }
        validateLoginUIDataWithRules()
    }

    private fun login() {
        Log.d(TAG, "Inside_register()")
        printState()
        signIn(
            email = loginUIState.value.email,
            password = loginUIState.value.password
        )
    }

    private fun register() {
        Log.d(TAG, "Inside_register()")
        printState()
        createUser(
            name = registrationUIState.value.username,
            email = registrationUIState.value.email,
            phoneNumber = registrationUIState.value.phoneNumber,
            password = registrationUIState.value.password
        )
    }

    private fun validateDataRegisterWithRules() {
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
        Log.d(TAG, "emailResult = ${registrationUIState.value.email}")
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

    private fun validateLoginUIDataWithRules() {
        val emailResult = Validator.validateUsername(
            username = loginUIState.value.email
        )
        val passwordResult = Validator.validateEmail(
            email = loginUIState.value.password
        )

        Log.d(TAG, "Inside_validateDataWithRules")
        Log.d(TAG, "usernameResult = $emailResult")
        Log.d(TAG, "emailResult = $passwordResult")

        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        allValidatePass.value =
            emailResult.status && passwordResult.status
    }

    private fun createUser(name: String, email: String, phoneNumber: String, password: String) =
        viewModelScope.launch {
            _registerFlow.value = Resource.Loading
            val result = repository.register(name, email, phoneNumber, password)
            //currentUser?.sendEmailVerification()
            _registerFlow.value = result
        }

    private fun signIn(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = repository.login(email, password)
        _loginFlow.value = result
    }

    private fun printState() {
        Log.d(TAG, "Inside PrintState")
        Log.d(TAG, registrationUIState.value.toString())
    }

    fun logout() {
        repository.logout()
        _registerFlow.value = null
        _loginFlow.value = null
    }

}