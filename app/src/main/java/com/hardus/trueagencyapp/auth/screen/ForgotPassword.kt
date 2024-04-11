package com.hardus.trueagencyapp.auth.screen

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.auth.viewmodel.AuthViewModel
import com.hardus.trueagencyapp.component.AppbarAddOne
import com.hardus.trueagencyapp.component.field_component.ButtonComponent
import com.hardus.trueagencyapp.component.field_component.ButtonComponentWithIcon
import com.hardus.trueagencyapp.component.field_component.MyTextField
import com.hardus.trueagencyapp.firebase.Resource

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgotPasswordScreen(
    onBackToLoginScreen: () -> Unit
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val (focusPhoneNumber) = remember { FocusRequester.createRefs() }
    val forgotPasswordViewModel: AuthViewModel = hiltViewModel()
    var showSuccessToast by remember { mutableStateOf(false) }

    // Mengamati status reset password
    val resetPasswordStatus by forgotPasswordViewModel.resetPasswordStatus.collectAsState()


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
                text = email,
                labelValue = stringResource(id = R.string.email),
                imageVector = Icons.Outlined.Phone,
                onTextSelected = {
                    email = it
                },
                errorStatus = true,
                focusPhoneNumber,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(onNext = { keyboardController?.hide() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp),
            )
            Spacer(modifier = Modifier.height(20.dp))
            ButtonComponent(
                value = stringResource(id = R.string.submit),
                isEnabled = email.isNotEmpty(),
                onNavigate = {
                    forgotPasswordViewModel.sendPasswordResetEmail(email)
                },
            )
            when (resetPasswordStatus) {
                is Resource.Success -> {
                    showSuccessToast = true
                    onBackToLoginScreen()
                }

                is Resource.Failure -> {
                    // Tampilkan pesan kesalahan
                    val exception = (resetPasswordStatus as Resource.Failure).exception
                    Text("Error: ${exception.message}")
                }

                else -> {}
            }
            if (showSuccessToast) {
                Toast.makeText(context, "Email untuk ganti password telah dikirim.", Toast.LENGTH_SHORT).show()
                forgotPasswordViewModel.resetPasswordStatus // Reset status setelah menampilkan toast
                showSuccessToast = false // Reset state toast
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Hardus")
@Composable
fun CheckForgotPasswordScreenPhone() {
    //ForgotPasswordScreen(onOTPCode = {}, activity = {})
}

@Preview(device = Devices.TABLET)
@Composable
fun CheckForgotPasswordScreenTablet() {
    //ForgotPasswordScreen(onOTPCode = {})
}

