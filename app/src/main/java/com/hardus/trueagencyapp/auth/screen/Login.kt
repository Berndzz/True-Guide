package com.hardus.trueagencyapp.auth.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.auth.component.AppbarAuthentication
import com.hardus.trueagencyapp.auth.component.ButtonComponent
import com.hardus.trueagencyapp.auth.component.ButtonComponentWithIcon
import com.hardus.trueagencyapp.auth.component.DividerTextComponent
import com.hardus.trueagencyapp.auth.component.MyTextField
import com.hardus.trueagencyapp.auth.component.PasswordTextFieldComponent
import com.hardus.trueagencyapp.auth.component.TextButtonComponent
import com.hardus.trueagencyapp.auth.component.TextButtonComponent2
import com.hardus.trueagencyapp.auth.data.UIEvent
import com.hardus.trueagencyapp.auth.viewmodel.LoginViewModel
import com.hardus.trueagencyapp.navigations.Route

@OptIn(
    ExperimentalComposeUiApi::class
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val (focusEmail, focusPassword) = remember { FocusRequester.createRefs() }
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var  isValid = false
    Surface(
        color = Color.White, modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {
            AppbarAuthentication(stringResource(R.string.login))

            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.verticalScroll(scrollState)) {

                MyTextField(
                    labelValue = stringResource(id = R.string.email),
                    imageVector = Icons.Outlined.Email,
                    onTextSelected = {
                        loginViewModel.onEvent(UIEvent.UsernameChanged(it))
                    },
                    errorStatus = loginViewModel.registrationUIState.value.usernameError,
                    focusEmail,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusPassword.requestFocus() })
                )

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    imageVector = Icons.Outlined.Lock,
                    onTextSelected = {
                        loginViewModel.onEvent(UIEvent.PasswordChanged(it))
                    },
                    errorStatus = loginViewModel.registrationUIState.value.passwordError,
                    focusPassword,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
                )
                Spacer(modifier = Modifier.height(20.dp))

                TextButtonComponent2(
                    value = stringResource(id = R.string.forgot_password),
                    onNavigate = {
                        navController.navigate(Route.screenForgotPassword)
                    })
                Spacer(modifier = Modifier.height(20.dp))

                ButtonComponent(
                    value = stringResource(id = R.string.login),
                    onNavigate = {
                        //isValid = loginViewModel.registrationUIState.value.usernameError && loginViewModel.registrationUIState.value.passwordError
                        //if(isValid){
                            navController.navigate(Route.screenHome)
                            Toast.makeText(context, "Berhasil Login", Toast.LENGTH_LONG).show()
                       // }else{
                        //    Toast.makeText(context, "Gagal Login", Toast.LENGTH_LONG).show()
                        //}
                    },
                    isEnabled = loginViewModel.registrationUIState.value.usernameError && loginViewModel.registrationUIState.value.passwordError ,
                )

                Spacer(modifier = Modifier.height(30.dp))

                DividerTextComponent()

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceEvenly,

                    ) {
                    ButtonComponentWithIcon(
                        value = stringResource(id = R.string.google),
                        painterResource = painterResource(
                            id = R.drawable.logo_google
                        ),
                        onNavigate = {}
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    ButtonComponentWithIcon(
                        value = stringResource(id = R.string.facebook),
                        painterResource = painterResource(
                            id = R.drawable.logo_facebook
                        ),
                        onNavigate = {}
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
                TextButtonComponent(
                    value1 = stringResource(id = R.string.don_t_have_an_account),
                    value2 = stringResource(
                        id = R.string.register
                    ),
                    onNavigate = {
                        navController.navigate(Route.screenRegister)
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, name = "Hardus")
@Composable
fun CheckLoginScreenPhone() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}

@Preview(device = Devices.TABLET)
@Composable
fun CheckLoginScreenTablet() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}