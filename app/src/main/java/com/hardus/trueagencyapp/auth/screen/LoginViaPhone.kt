package com.hardus.trueagencyapp.auth.screen

import android.annotation.SuppressLint
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.auth.component.AppbarAddOne
import com.hardus.trueagencyapp.util.getPhoneNumberError
import com.hardus.trueagencyapp.util.validatePhoneNumber

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginPhoneNumberScreen(navController: NavHostController) {
    Scaffold {
        Column {
            AppbarAddOne(
                stringResource(R.string.enter_your_number),
                stringResource(R.string.enter_your_number_description)
            )
            PhoneNumberFilledInput(navigateData = navController)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberFilledInput(navigateData: NavHostController) {
    var phoneNumber by remember { mutableStateOf("") }
    val (focusPhoneNumber) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 35.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusPhoneNumber),
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text(text = stringResource(R.string.enter_your_phone_number)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            )
            Text(
                text = if (validatePhoneNumber(phoneNumber)) "" else getPhoneNumberError(
                    phoneNumber
                ), style = TextStyle(color = Color.Red, fontSize = 12.sp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    if (validatePhoneNumber(phoneNumber)) {
                        /* Do something */
                    } else {
                        // Tampilkan pesan error
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = if (phoneNumber.isNotEmpty()) ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.primaryContainer
                ) else ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.outline
                ),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(text = stringResource(R.string.log_in), fontSize = 18.sp)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Hardus")
@Composable
fun CheckLoginPhoneNumberScreenPhone() {
    val navController = rememberNavController()
    LoginPhoneNumberScreen(navController = navController)
}

