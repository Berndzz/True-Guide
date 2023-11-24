package com.hardus.trueagencyapp.auth.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.auth.component.AppbarAddOne
import com.hardus.trueagencyapp.auth.component.ButtonComponent
import com.hardus.trueagencyapp.auth.component.MyTextField
import com.hardus.trueagencyapp.navigations.Route

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgotPasswordScreen(navController: NavHostController) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val (focusEmail) = remember { FocusRequester.createRefs() }

    Surface(
        color = Color.White, modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {
            AppbarAddOne(
                stringResource(id = R.string.forgot_password),
                stringResource(id = R.string.forgot_password_description)
            )
            Spacer(modifier = Modifier.height(32.dp))
            MyTextField(
                labelValue = stringResource(id = R.string.email),
                imageVector = Icons.Outlined.Email,
                focusEmail,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
            )
            Spacer(modifier = Modifier.height(20.dp))
            ButtonComponent(
                value = stringResource(id = R.string.submit),
                onNavigate = {
                    navController.navigate(Route.screenOTPCode)
                }
            )


        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Hardus")
@Composable
fun CheckForgotPasswordScreenPhone() {
    val navController = rememberNavController()
    ForgotPasswordScreen(navController = navController)
}

@Preview(device = Devices.TABLET)
@Composable
fun CheckForgotPasswordScreenTablet() {
    val navController = rememberNavController()
    ForgotPasswordScreen(navController = navController)
}

