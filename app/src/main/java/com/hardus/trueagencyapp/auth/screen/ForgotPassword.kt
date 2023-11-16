package com.hardus.trueagencyapp.auth.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.auth.component.AppbarAddOne
import com.hardus.trueagencyapp.auth.navigation.Route
import com.hardus.trueagencyapp.util.getEmailError
import com.hardus.trueagencyapp.util.validateEmail

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(navController: NavHostController) {
    Scaffold {
        Column {
            AppbarAddOne(
                stringResource(id = R.string.forgot_password),
                stringResource(id = R.string.forgot_password_description)
            )
            ForgotPasswordFilledInput(navigateData = navController)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordFilledInput(navigateData: NavHostController) {
    var email by remember { mutableStateOf("") }
    val (focusEmail) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 35.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusEmail),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                singleLine = true,
                label = { Text(text = stringResource(R.string.enter_your_email)) })
            Text(
                text = if (validateEmail(email)) "" else getEmailError(
                    email
                ), style = TextStyle(color = Color.Red, fontSize = 12.sp)
            )
        }
        Spacer(modifier = Modifier.height(37.dp))
        Button(
            onClick = {
                if (email.isNotEmpty() && validateEmail(email)) {
                    navigateData.navigate(Route.screenOTPCode + "/${email}")
                } else {
                    Toast.makeText(context, getEmailError(email), Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = if (email.isNotEmpty()) ButtonDefaults.buttonColors(
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

@Preview(showBackground = true, showSystemUi = true, name = "Hardus")
@Composable
fun CheckForgotPasswordScreenPhone() {
    val navController = rememberNavController()
    ForgotPasswordScreen(navController = navController)
}

@Preview(device = Devices.TABLET)
@Composable
fun CheckForgotPasswordScreenTablet() {

}

