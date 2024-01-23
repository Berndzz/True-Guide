package com.hardus.trueagencyapp.auth.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.hardus.trueagencyapp.auth.data.login.LoginUIEvent
import com.hardus.trueagencyapp.auth.data.login.LoginUIState
import com.hardus.trueagencyapp.auth.data.register.RegisterUIEvent
import com.hardus.trueagencyapp.auth.data.register.RegistrationUIState
import com.hardus.trueagencyapp.component.rules.Validator
import com.hardus.trueagencyapp.firebase.AuthFirebase
import com.hardus.trueagencyapp.firebase.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _isInRegistrationMode = mutableStateOf(false)
    val isInRegistrationMode: State<Boolean> = _isInRegistrationMode

    private val _resetPasswordStatus = MutableStateFlow<Resource<String>>(Resource.Loading)
    val resetPasswordStatus: StateFlow<Resource<String>> = _resetPasswordStatus.asStateFlow()

    // Fungsi untuk mengatur konteks
    fun setRegistrationMode(isInRegistrationMode: Boolean) {
        _isInRegistrationMode.value = isInRegistrationMode
    }

    private val _emailUser = mutableStateOf("")
    private val _usernameUser = mutableStateOf("")
    private val _phoneNumberUser = mutableStateOf("")
    private val _passwordUser = mutableStateOf("")
    private val _privacyPolicyUser = mutableStateOf(false)
    val usernameUserResponse: String
        get() = _usernameUser.value

    val emailUserResponse: String
        get() = _emailUser.value

    val phoneNumberUserResponse: String
        get() = _phoneNumberUser.value

    val passwordUsersResponse: String
        get() = _passwordUser.value

    val privacyPolicyResponse: Boolean
        get() = _privacyPolicyUser.value


    fun onUsernameUserChange(username: String) {
        _usernameUser.value = username
        validateDataRegisterWithRules()
    }

    fun onEmailUserChange(email: String) {
        _emailUser.value = email
        if (isInRegistrationMode.value) {
            validateDataRegisterWithRules()
        } else {
            validateLoginUIDataWithRules()
        }
    }

    fun onPhoneNumberChange(phone: String) {
        _phoneNumberUser.value = phone
        validateDataRegisterWithRules()
    }

    fun onPasswordUserChange(password: String) {
        _passwordUser.value = password
        if (isInRegistrationMode.value) {
            validateDataRegisterWithRules()
        } else {
            validateLoginUIDataWithRules()
        }
    }

    fun onPrivacyPolicyChange(status: Boolean) {
        _privacyPolicyUser.value = status
        validateDataRegisterWithRules()
    }

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

            is RegisterUIEvent.RegisterButtonClicked -> {
                register()
            }

        }
        validateDataRegisterWithRules()
    }

    fun onEventLogin(event: LoginUIEvent) {
        when (event) {
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
            email = _emailUser.value,
            password = _passwordUser.value
        )
    }

    private fun register() {
        Log.d(TAG, "Inside_register()")
        printState()
        createUser(
            name = _usernameUser.value,
            email = _emailUser.value,
            phoneNumber = _phoneNumberUser.value,
            password = _passwordUser.value
        )
        _privacyPolicyUser.value
    }

    private fun validateDataRegisterWithRules() {
        val usernameResult = Validator.validateUsername(
            username = _usernameUser.value
        )
        val emailResult = Validator.validateEmail(
            email = _emailUser.value
        )
        val phoneNResult = Validator.validatePhoneNumber(
            phoneN = _phoneNumberUser.value
        )
        val passwordResult = Validator.validatePassword(
            password = _passwordUser.value
        )
        val privacyPolicyResult = Validator.validatePrivacyPolicyAcceptance(
            statusValue = _privacyPolicyUser.value
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

    private fun validateLoginUIDataWithRules() {
        val emailResult = Validator.validateEmail(
            email = _emailUser.value
        )
        val passwordResult = Validator.validatePassword(
            password = _passwordUser.value
        )

        Log.d(TAG, "Inside_validateDataWithRules")
        Log.d(TAG, "emailResult = $emailResult")
        Log.d(TAG, "passwordResult = $passwordResult")

        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        allValidatePass.value = emailResult.status && passwordResult.status
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


    fun sendPasswordResetEmail(email: String) {
        viewModelScope.launch {
            _resetPasswordStatus.value = Resource.Loading
            try {
                val result = repository.sendPasswordResetEmail(email)
                _resetPasswordStatus.value =
                    result // Langsung menetapkan hasil ke _resetPasswordStatus
            } catch (e: Exception) {
                _resetPasswordStatus.value = Resource.Failure(e)
            }
        }
    }


    fun logout() {
        repository.logout()
        _registerFlow.value = null
        _loginFlow.value = null
    }

}