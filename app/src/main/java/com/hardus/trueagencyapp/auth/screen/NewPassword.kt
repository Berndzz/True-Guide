package com.hardus.trueagencyapp.auth.screen

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.Lock
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.auth.viewmodel.AuthViewModel
import com.hardus.trueagencyapp.component.AppbarAddOne
import com.hardus.trueagencyapp.component.field_component.ButtonComponent
import com.hardus.trueagencyapp.component.field_component.PasswordTextFieldComponent
import com.hardus.trueagencyapp.nested_navigation.Screen


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NewPasswordScreen(navController: NavHostController) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val (focusPassword1, focusPassword2) = remember { FocusRequester.createRefs() }
    val scrollState = rememberScrollState()
    val newPasswordViewModel: AuthViewModel = hiltViewModel()

    Surface(
        color = Color.White, modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {
            AppbarAddOne(
                name = stringResource(R.string.enter_new_password),
                description = "Please use a strong password minimum 8 characters combined with numbers"
            )
            Spacer(modifier = Modifier.height(32.dp))
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                PasswordTextFieldComponent(
                    text = newPasswordViewModel.passwordUsersResponse,
                    labelValue = stringResource(id = R.string.password),
                    imageVector = Icons.Outlined.Lock,
                    onTextSelected = {},
                    errorStatus = false,
                    focusPassword1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(onDone = { focusPassword2.requestFocus() }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp),
                )
                Spacer(modifier = Modifier.height(20.dp))

                PasswordTextFieldComponent(
                    text = newPasswordViewModel.repeatPasswordUsersResponse,
                    labelValue = stringResource(id = R.string.repeat_your_password),
                    imageVector = Icons.Outlined.Lock,
                    onTextSelected = {},
                    errorStatus = false,
                    focusPassword2,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp),
                )

                Spacer(modifier = Modifier.height(35.dp))

                ButtonComponent(
                    value = stringResource(id = R.string.submit),
                    onNavigate = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
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