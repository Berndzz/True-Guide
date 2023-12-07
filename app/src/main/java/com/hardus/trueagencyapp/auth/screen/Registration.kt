package com.hardus.trueagencyapp.auth.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.component.AppbarAuthentication
import com.hardus.trueagencyapp.component.ButtonComponent
import com.hardus.trueagencyapp.component.CheckboxComponents
import com.hardus.trueagencyapp.component.MyTextField
import com.hardus.trueagencyapp.component.PasswordTextFieldComponent
import com.hardus.trueagencyapp.component.TextButtonComponent
import com.hardus.trueagencyapp.auth.data.register.RegisterUIEvent
import com.hardus.trueagencyapp.auth.viewmodel.AuthViewModel
import com.hardus.trueagencyapp.firebase.Resource
import com.hardus.trueagencyapp.nested_navigation.APP_GRAPH_ROUTE
import com.hardus.trueagencyapp.nested_navigation.Screen


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegistrationScreen(
    registerViewModel: AuthViewModel? = hiltViewModel(),
    navController: NavHostController
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val (focusEmail, focusPassword, focusPhoneNumber, focusUsername) = remember { FocusRequester.createRefs() }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val registerFlow = registerViewModel?.registerFlow?.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column {
                AppbarAuthentication(name = stringResource(id = R.string.register))
                Spacer(modifier = Modifier.height(20.dp))
                Column(modifier = Modifier.verticalScroll(scrollState)) {
                    registerViewModel?.registrationUIState?.value?.let {
                        MyTextField(
                            labelValue = stringResource(id = R.string.username),
                            imageVector = Icons.Outlined.Person,
                            onTextSelected = {
                                registerViewModel.onEventRegister(RegisterUIEvent.UsernameChanged(it))
                            },
                            errorStatus = it.usernameError,
                            focusUsername,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { focusEmail.requestFocus() })
                        )
                    }

                    registerViewModel?.registrationUIState?.value?.let {
                        MyTextField(
                            labelValue = stringResource(id = R.string.email),
                            imageVector = Icons.Outlined.Email,
                            onTextSelected = {
                                registerViewModel.onEventRegister(RegisterUIEvent.EmailChanged(it))
                            },
                            errorStatus = it.emailError,
                            focusEmail,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { focusPhoneNumber.requestFocus() })
                        )
                    }

                    registerViewModel?.registrationUIState?.value?.let {
                        MyTextField(
                            labelValue = stringResource(id = R.string.phone_number),
                            imageVector = Icons.Outlined.Phone,
                            onTextSelected = {
                                registerViewModel.onEventRegister(RegisterUIEvent.PhoneNumberChanged(it))
                            },
                            errorStatus = it.phoneNumberError,
                            focusPhoneNumber,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Next,
                            ),
                            keyboardActions = KeyboardActions(onNext = { focusPassword.requestFocus() })
                        )
                    }

                    registerViewModel?.registrationUIState?.value?.let {
                        PasswordTextFieldComponent(
                            labelValue = stringResource(id = R.string.password),
                            imageVector = Icons.Outlined.Lock,
                            onTextSelected = {
                                registerViewModel.onEventRegister(RegisterUIEvent.PasswordChanged(it))
                            },
                            errorStatus = it.passwordError,
                            focusPassword,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done,
                            ),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    CheckboxComponents(stringResource(R.string.term_and_condition),
                        onTextSelected = {
                            navController.navigate(Screen.TermAndCondition.route)
                        },
                        onCheckedChange = {
                            registerViewModel?.onEventRegister(
                                RegisterUIEvent.PrivacyPolicyCheckBoxClicked(
                                    it
                                )
                            )
                        })

                    Spacer(modifier = Modifier.height(20.dp))

                    registerViewModel?.allValidatePass?.let {
                        ButtonComponent(
                            value = stringResource(id = R.string.register), onNavigate = {
                                registerViewModel.onEventRegister(RegisterUIEvent.RegisterButtonClicked)
                            }, isEnabled = it.value
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    TextButtonComponent(value1 = stringResource(id = R.string.i_have_an_account),
                        value2 = stringResource(
                            id = R.string.login
                        ),
                        onNavigate = {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        })
                }
            }
        }
        registerFlow?.value?.let {
            when (it) {
                is Resource.Failure -> {
                    Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                }

                Resource.Loading -> {
                    CircularProgressIndicator()
                }

                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                        navController.navigate(route = APP_GRAPH_ROUTE) {
                            popUpTo(APP_GRAPH_ROUTE) { inclusive = true }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Hardus")
@Composable
fun CheckRegistrationScreenPhone() {
    val navController = rememberNavController()
    RegistrationScreen(null, navController = navController)
}

@Preview(device = Devices.TABLET)
@Composable
fun CheckRegistrationScreenTablet() {
    val navController = rememberNavController()
    RegistrationScreen(null, navController = navController)
}

