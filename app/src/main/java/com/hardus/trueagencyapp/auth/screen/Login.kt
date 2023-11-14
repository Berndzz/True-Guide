package com.hardus.trueagencyapp.auth.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.ui.res.painterResource
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
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.auth.component.AppbarAuthentication
import com.hardus.trueagencyapp.auth.navigation.Route
import com.hardus.trueagencyapp.util.getEmailError
import com.hardus.trueagencyapp.util.getPasswordError
import com.hardus.trueagencyapp.util.validateEmail
import com.hardus.trueagencyapp.util.validatePassword


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavHostController) {
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AppbarAuthentication(stringResource(R.string.login))
            LoginFilledInput(navigateToRegister = navController)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LoginFilledInput(navigateToRegister: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val (focusEmail, focusPassword) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isPasswordVisible by remember { mutableStateOf(false) }
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
                OutlinedTextField(value = email,
                    onValueChange = { email = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusEmail),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusPassword.requestFocus() }),
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.enter_your_email)) })
                Text(
                    text = if (validateEmail(email)) "" else getEmailError(
                        email
                    ), style = TextStyle(color = Color.Red, fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))
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
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.End) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = stringResource(R.string.forgot_password), style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.poppins)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFFB22222),
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
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
                colors = if (email.isNotEmpty() && password.isNotEmpty()) ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.primaryContainer
                ) else ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.outline
                ),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(text = stringResource(R.string.log_in), fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(49.dp))
            Image(
                painter = painterResource(id = R.drawable.login_option),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(23.dp)
            )
            Spacer(modifier = Modifier.height(37.dp))
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(171.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(Color.Gray),
                shape = RoundedCornerShape(6.dp),
            ) {
                Icon(
                    modifier = Modifier.padding(end = 5.dp),
                    imageVector = Icons.Default.Phone,
                    contentDescription = stringResource(R.string.phone_icon)
                )
                Text(text = stringResource(R.string.phone_number), fontSize = 13.sp)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                Modifier.fillMaxWidth(),
                Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.don_t_have_an_account), fontSize = 12.sp)
                TextButton(onClick = { navigateToRegister.navigate(Route.screenRegister) }) {
                    Text(
                        text = stringResource(R.string.register),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.inter_medium)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFFB22222),
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Hardus")
@Composable
fun CheckLoginScreenPhone() {
//    LoginScreen(navController =)
}

//@Preview(device = Devices.TABLET)
//@Composable
//fun CheckLoginScreenTablet() {
//    LoginScreen()
//}