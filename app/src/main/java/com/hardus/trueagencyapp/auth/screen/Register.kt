package com.hardus.trueagencyapp.auth.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.auth.component.AppbarAuthentication
import com.hardus.trueagencyapp.navigations.Route
import com.hardus.trueagencyapp.util.getEmailError
import com.hardus.trueagencyapp.util.getPasswordError
import com.hardus.trueagencyapp.util.getPhoneNumberError
import com.hardus.trueagencyapp.util.getUsernameError
import com.hardus.trueagencyapp.util.validateEmail
import com.hardus.trueagencyapp.util.validatePassword
import com.hardus.trueagencyapp.util.validatePhoneNumber
import com.hardus.trueagencyapp.util.validateUsername

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController) {
    Scaffold {
        Column {
            AppbarAuthentication(name = stringResource(id = R.string.register))
            RegisterFilledInput(navigateToLogin = navController)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun RegisterFilledInput(navigateToLogin: NavHostController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val (focusEmail, focusPassword, focusPhoneNumber, focusUsername) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isPasswordVisible by remember { mutableStateOf(false) }

// State untuk menyimpan pesan error
    val textError = remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 35.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(32.dp))

            Column(Modifier.fillMaxWidth()) {
                // Username
                OutlinedTextField(value = username,
                    onValueChange = { username = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusUsername),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusEmail.requestFocus() }),
                    singleLine = true,
                    label = { Text(text = stringResource(id = R.string.enter_your_username)) })
                Text(
                    text = if (validateUsername(username)) "" else getUsernameError(
                        username
                    ), style = TextStyle(color = Color.Red, fontSize = 12.sp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Email
                OutlinedTextField(value = email,
                    onValueChange = { email = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusEmail),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusPhoneNumber.requestFocus() }),
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.enter_your_email)) })
                Text(
                    text = if (validateEmail(email)) "" else getEmailError(
                        email
                    ), style = TextStyle(color = Color.Red, fontSize = 12.sp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Phone Number
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusPhoneNumber),
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text(text = stringResource(R.string.enter_your_phone_number)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = { focusPassword.requestFocus() }),
                )
                Text(
                    text = if (validatePhoneNumber(phoneNumber)) "" else getPhoneNumberError(
                        phoneNumber
                    ), style = TextStyle(color = Color.Red, fontSize = 12.sp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Password
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusPassword),
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = stringResource(R.string.enter_your_password)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = stringResource(R.string.password_toggle)
                            )
                        }
                    })
                Text(
                    text = if (validatePassword(password)) "" else getPasswordError(
                        password
                    ), style = TextStyle(color = Color.Red, fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(25.dp))
                Button(
                    onClick = {
                        if (validateEmail(email) && validatePassword(password)) {
                            /* Do something */
                        } else {
                            // Tampilkan pesan error
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = if (username.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty() && password.isNotEmpty()) ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.primaryContainer
                    ) else ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.outline
                    ),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(text = stringResource(R.string.register), fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(R.string.i_have_an_account), fontSize = 12.sp)
                    TextButton(onClick = {
                        navigateToLogin.navigate(Route.screenLogin) {
                            popUpTo(Route.screenLogin) { inclusive = true }
                        }
                    }) {
                        Text(
                            text = stringResource(R.string.login),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.inter_medium)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFFB22222),
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Hardus")
@Composable
fun CheckRegisterScreenPhone() {
    val navController = rememberNavController()
    RegisterScreen(navController = navController)
}