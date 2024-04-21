package com.hardus.trueagencyapp.auth.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.auth.data.register.RegisterUIEvent
import com.hardus.trueagencyapp.auth.viewmodel.AuthViewModel
import com.hardus.trueagencyapp.component.AppbarAuthentication
import com.hardus.trueagencyapp.component.field_component.ButtonComponent
import com.hardus.trueagencyapp.component.field_component.CheckboxComponents
import com.hardus.trueagencyapp.component.field_component.MyTextField
import com.hardus.trueagencyapp.component.field_component.PasswordTextFieldComponent
import com.hardus.trueagencyapp.component.field_component.TextButtonComponent
import com.hardus.trueagencyapp.firebase.Resource


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegistrationScreen(
    onLogin: () -> Unit,
    onTermAndCondition: () -> Unit,
    onBackToLoginScreen: () -> Unit
) {
    val registerViewModel = hiltViewModel<AuthViewModel>()
    val keyboardController = LocalSoftwareKeyboardController.current
    val (focusEmail, focusPassword, focusPhoneNumber, focusUsername) = remember { FocusRequester.createRefs() }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val registerFlow = registerViewModel.registerFlow.collectAsState()

    LaunchedEffect(Unit) {
        registerViewModel.setRegistrationMode(true)
    }

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column {
                AppbarAuthentication(name = stringResource(id = R.string.register))
                Spacer(modifier = Modifier.height(20.dp))
                Column(modifier = Modifier.verticalScroll(scrollState)) {
                    registerViewModel.registrationUIState.value.let {
                        MyTextField(
                            text = registerViewModel.usernameUserResponse,
                            labelValue = stringResource(id = R.string.username),
                            imageVector = Icons.Outlined.Person,
                            onTextSelected = {
                                registerViewModel.onUsernameUserChange(it)
                            },
                            errorStatus = it.usernameError,
                            focusUsername,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { focusEmail.requestFocus() }),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 35.dp),
                        )
                    }

                    registerViewModel.registrationUIState.value.let {
                        MyTextField(
                            text = registerViewModel.emailUserResponse,
                            labelValue = stringResource(id = R.string.email),
                            imageVector = Icons.Outlined.Email,
                            onTextSelected = {
                                registerViewModel.onEmailUserChange(it)
                            },
                            errorStatus = it.emailError,
                            focusEmail,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { focusPhoneNumber.requestFocus() }),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 35.dp),
                        )
                    }

                    registerViewModel.registrationUIState.value.let {
                        MyTextField(
                            text = registerViewModel.phoneNumberUserResponse,
                            labelValue = stringResource(id = R.string.phone_number),
                            imageVector = Icons.Outlined.Phone,
                            onTextSelected = {
                                registerViewModel.onPhoneNumberChange(it)
                            },
                            errorStatus = it.phoneNumberError,
                            focusPhoneNumber,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Next,
                            ),
                            keyboardActions = KeyboardActions(onNext = { focusPassword.requestFocus() }),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 35.dp),
                        )
                    }

                    registerViewModel.registrationUIState.value.let {
                        PasswordTextFieldComponent(
                            text = registerViewModel.passwordUsersResponse,
                            labelValue = stringResource(id = R.string.password),
                            imageVector = Icons.Outlined.Lock,
                            onTextSelected = {
                                registerViewModel.onPasswordUserChange(it)
                            },
                            errorStatus = it.passwordError,
                            focusPassword,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done,
                            ),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 35.dp),
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    CheckboxComponents(stringResource(R.string.term_and_condition),
                        onTextSelected = {
                            onTermAndCondition()
                        },
                        checkedState = registerViewModel.privacyPolicyResponse,
                        onCheckedChange = {
                            registerViewModel.onPrivacyPolicyChange(it)
                        })

                    Spacer(modifier = Modifier.height(20.dp))

                    ButtonComponent(
                        value = stringResource(
                            id = R.string.register
                        ),
                        onNavigate = {
                            registerViewModel.onEventRegister(RegisterUIEvent.RegisterButtonClicked)
                        },
                        isEnabled = registerViewModel.allValidatePass.value
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    TextButtonComponent(value1 = stringResource(id = R.string.i_have_an_account),
                        value2 = stringResource(
                            id = R.string.login
                        ),
                        onNavigate = {
                            onBackToLoginScreen()
                        })
                }
            }
        }
        registerFlow.value?.let {
            when (it) {
                is Resource.Failure -> {
                    Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                }

                Resource.Loading -> {
                    CircularProgressIndicator()
                }

                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                        onLogin()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Hardus")
@Composable
fun CheckRegistrationScreenPhone() {
    RegistrationScreen(onLogin = {}, onTermAndCondition = {}, onBackToLoginScreen = {})
}

@Preview(device = Devices.TABLET)
@Composable
fun CheckRegistrationScreenTablet() {
    RegistrationScreen(onLogin = {}, onTermAndCondition = {}, onBackToLoginScreen = {})
}

