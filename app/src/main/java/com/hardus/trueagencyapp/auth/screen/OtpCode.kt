package com.hardus.trueagencyapp.auth.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.component.AppbarAddOne
import com.hardus.trueagencyapp.util.GlobalVariable.TEST_VERIFY_CODE
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OtpCodeScreen(onNewPassword: () -> Unit) {
    Scaffold {
        Column {
            AppbarAddOne(
                name = stringResource(R.string.enter_otp_code), description = stringResource(
                    R.string.an_4_digit_code_has_sent_to
                )
            )
            ContentView(
                textList = textList,
                requesterList = requestList,
                onNewPassword = onNewPassword
            )
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ContentView(
    textList: List<MutableState<TextFieldValue>>, requesterList: List<FocusRequester>,
    onNewPassword: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(), color = Color.White
    ) {
        Box(Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .align(Alignment.TopCenter)
            ) {
                for (i in textList.indices) {
                    OtpCodeFilledInput(
                        value = textList[i].value, onValueChange = { newValue ->
                            // if old value is not empty, just return
                            if (textList[i].value.text != "") {
                                if (newValue.text == "") {
                                    textList[i].value = TextFieldValue(
                                        text = "", selection = TextRange(0)
                                    )
                                }
                                return@OtpCodeFilledInput
                            }
                            textList[i].value = TextFieldValue(
                                text = newValue.text,
                                selection = TextRange(newValue.text.length)
                            )
                            connectInputCode(textList) {
                                focusManager.clearFocus()
                                keyboardController?.hide()

                                val message = if (it) {
                                    "Success"
                                } else {
                                    "Error, input again"
                                }

                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                                for (text in textList) {
                                    text.value = TextFieldValue(text = "", selection = TextRange(0))
                                }

                                if (it) {
                                    onNewPassword()
                                }

                            }
                            nextFocus(textList, requesterList)
                        }, focusRequester = requesterList[i]
                    )
                }
            }
        }
    }
    LaunchedEffect(key1 = null, block = {
        delay(300)
        requesterList[0].requestFocus()
    })
}

private fun connectInputCode(
    textList: List<MutableState<TextFieldValue>>,
    onVerifyCode: ((success: Boolean) -> Unit)? = null,
) {
    var code = ""
    for (text in textList) {
        code += text.value.text
    }
    if (code.length == 4) {
        verifyCode(code, onSuccess = {
            onVerifyCode?.let {
                it(true)
            }

        }, onError = {
            onVerifyCode?.let {
                it(false)
            }
        })
    }
}

private fun verifyCode(code: String, onSuccess: () -> Unit, onError: () -> Unit) {
    if (code == TEST_VERIFY_CODE) {
        onSuccess()
    } else {
        onError()
    }
}

@Composable
fun OtpCodeFilledInput(
    value: TextFieldValue,
    onValueChange: (value: TextFieldValue) -> Unit,
    focusRequester: FocusRequester
) {
    Spacer(modifier = Modifier.height(32.dp))
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        readOnly = false,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(size = 6.dp))
            .wrapContentSize()
            .border(
                width = 1.dp, color = Color(0xFF000000), shape = RoundedCornerShape(size = 6.dp)
            )
            .focusRequester(focusRequester),
        maxLines = 1,
        cursorBrush = SolidColor(Color.Black),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .width(56.dp)
                    .height(56.dp), contentAlignment = Alignment.Center
            ) {
                innerTextField()
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = null
        )
    )
}

private fun nextFocus(
    textList: List<MutableState<TextFieldValue>>, requesterList: List<FocusRequester>
) {
    for (index in textList.indices) {
        if (textList[index].value.text == "") {
            if (index < textList.size) {
                requesterList[index].requestFocus()
                break
            }
        }
    }
}

private val textList = listOf(
    mutableStateOf(
        TextFieldValue(
            text = "", selection = TextRange(0)
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "", selection = TextRange(0)
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "", selection = TextRange(0)
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "", selection = TextRange(0)
        )
    ),
)

private val requestList = listOf(
    FocusRequester(), FocusRequester(), FocusRequester(), FocusRequester()
)


@Preview(showBackground = true, showSystemUi = true, name = "Hardus")
@Composable
fun CheckOtpCodeScreenPhone() {
    OtpCodeScreen(onNewPassword = {})
}