package com.hardus.trueagencyapp.auth.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.auth.viewmodel.AuthViewModel
import com.hardus.trueagencyapp.component.AppbarAddOne
import com.hardus.trueagencyapp.component.field_component.ButtonComponent
import com.hardus.trueagencyapp.component.field_component.MyTextField

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgotPasswordScreen(
    onOTPCode: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val (focusEmail) = remember { FocusRequester.createRefs() }
    val forgotPasswordViewModel: AuthViewModel = hiltViewModel()

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
                text = forgotPasswordViewModel.emailUserResponse,
                labelValue = stringResource(id = R.string.email),
                imageVector = Icons.Outlined.Email,
                onTextSelected = {},
                errorStatus = true,
                focusEmail,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp),
            )
            Spacer(modifier = Modifier.height(20.dp))
            ButtonComponent(
                value = stringResource(id = R.string.submit), onNavigate = {
                    onOTPCode()
                },
                isEnabled = true
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Hardus")
@Composable
fun CheckForgotPasswordScreenPhone() {
    ForgotPasswordScreen(onOTPCode = {})
}

@Preview(device = Devices.TABLET)
@Composable
fun CheckForgotPasswordScreenTablet() {
    ForgotPasswordScreen(onOTPCode = {})
}

