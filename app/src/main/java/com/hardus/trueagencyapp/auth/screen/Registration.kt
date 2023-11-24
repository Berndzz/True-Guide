package com.hardus.trueagencyapp.auth.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.auth.component.AppbarAuthentication
import com.hardus.trueagencyapp.auth.component.ButtonComponent
import com.hardus.trueagencyapp.auth.component.ButtonComponentWithIcon
import com.hardus.trueagencyapp.auth.component.CheckboxComponents
import com.hardus.trueagencyapp.auth.component.MyTextField
import com.hardus.trueagencyapp.auth.component.PasswordTextFieldComponent
import com.hardus.trueagencyapp.auth.component.TextButtonComponent
import com.hardus.trueagencyapp.navigations.Route

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegistrationScreen(navController: NavHostController) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val (focusEmail, focusPassword, focusPhoneNumber, focusUsername) = remember { FocusRequester.createRefs() }
    val scrollState = rememberScrollState()
    Surface(
        color = Color.White, modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column() {
            AppbarAuthentication(name = stringResource(id = R.string.register))
            Spacer(modifier = Modifier.height(35.dp))
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                MyTextField(
                    labelValue = stringResource(id = R.string.username),
                    imageVector = Icons.Outlined.Person,
                    focusUsername,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusEmail.requestFocus() })
                )

                MyTextField(
                    labelValue = stringResource(id = R.string.email),
                    imageVector = Icons.Outlined.Email,
                    focusEmail,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusPhoneNumber.requestFocus() })
                )

                MyTextField(
                    labelValue = stringResource(id = R.string.phone_number),
                    imageVector = Icons.Outlined.Phone,
                    focusPhoneNumber,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(onNext = { focusPassword.requestFocus() })
                )

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    imageVector = Icons.Outlined.Lock,
                    focusPassword,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
                )

                Spacer(modifier = Modifier.height(10.dp))
                CheckboxComponents(stringResource(R.string.term_and_condition), onTextSelected = {
                    navController.navigate(Route.screenTermAndCondition)
                })

                Spacer(modifier = Modifier.height(20.dp))

                ButtonComponent(value = stringResource(id = R.string.register), onNavigate = {})
                Spacer(modifier = Modifier.height(10.dp))

                TextButtonComponent(
                    value1 = stringResource(id = R.string.i_have_an_account),
                    value2 = stringResource(
                        id = R.string.login
                    ),
                    onNavigate = {
                        navController.navigate(Route.screenLogin) {
                            popUpTo(Route.screenLogin) { inclusive = true }
                        }
                    }
                )
            }

        }
    }
}

@Preview(showBackground = true, name = "Hardus")
@Composable
fun CheckRegistrationScreenPhone() {
    val navController = rememberNavController()
    RegistrationScreen(navController = navController)
}

@Preview(device = Devices.TABLET)
@Composable
fun CheckRegistrationScreenTablet() {
    val navController = rememberNavController()
    RegistrationScreen(navController = navController)
}

