package com.hardus.trueagencyapp.auth.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.auth.component.AppbarAddOne
import com.hardus.trueagencyapp.auth.navigation.Route
import com.hardus.trueagencyapp.ui.theme.md_theme_light_surfaceVariant
import com.hardus.trueagencyapp.util.getEmailError
import com.hardus.trueagencyapp.util.getPasswordError
import com.hardus.trueagencyapp.util.validateEmail
import com.hardus.trueagencyapp.util.validatePassword

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPasswordScreen(navController: NavHostController) {
    Scaffold {
        Column {
            AppbarAddOne(
                name = stringResource(R.string.enter_new_password),
                description = "Please use a strong password minimum 8 characters combined with numbers"
            )
            NewPasswordFilledInput(navigateScreen = navController)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NewPasswordFilledInput(navigateScreen: NavHostController) {
    var password by remember { mutableStateOf("") }
    var verifyPassword by remember { mutableStateOf("") }
    val ( focusPassword,focusVerifyPassword) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isVerifyPasswordVisible by remember { mutableStateOf(false) }
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
                OutlinedTextField(value = password,
                    onValueChange = { password = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusPassword),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusVerifyPassword.requestFocus() }),
                    singleLine = true,
                    label = { Text(text = stringResource(R.string.enter_your_password)) },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = stringResource(R.string.password_toggle)
                            )
                        }
                    }
                )
                Text(
                    text = if (validateEmail(password)) "" else getEmailError(
                        password
                    ), style = TextStyle(color = Color.Red, fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusPassword),
                    value = verifyPassword,
                    onValueChange = { verifyPassword = it },
                    label = { Text(text = stringResource(R.string.repeat_your_password)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                    visualTransformation = if (isVerifyPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isVerifyPasswordVisible = !isVerifyPasswordVisible }) {
                            Icon(
                                imageVector = if (isVerifyPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
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
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                   navigateScreen.navigate(Route.screenLogin)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = if (password.isNotEmpty() && verifyPassword.isNotEmpty()) ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.primaryContainer
                ) else ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.outline
                ),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(text = stringResource(R.string.submit), fontSize = 18.sp)
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, name = "Hardus")
@Composable
fun CheckNewPasswordScreenPhone() {
    val navController = rememberNavController()
    NewPasswordScreen(navController = navController)
}

@Preview(device = Devices.TABLET)
@Composable
fun CheckNewPasswordScreenTablet() {

}