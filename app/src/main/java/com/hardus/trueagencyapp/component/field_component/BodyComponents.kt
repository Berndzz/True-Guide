package com.hardus.trueagencyapp.component.field_component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.component.OTP_VIEW_TYPE_BORDER
import com.hardus.trueagencyapp.component.OTP_VIEW_TYPE_UNDERLINE

@Composable
fun NormalTextComponent(value: String) {
    Text(
        text = value, modifier = Modifier.fillMaxWidth(), style = TextStyle(
            fontSize = 12.sp, fontWeight = FontWeight.Normal, fontStyle = FontStyle.Normal
        ), color = colorResource(id = R.color.black)
    )
}

@Composable
fun HeadingTextComponent(value: String) {
    Text(
        text = value, modifier = Modifier.fillMaxWidth(), style = TextStyle(
            fontSize = 30.sp, fontWeight = FontWeight.Normal, fontStyle = FontStyle.Normal
        ), color = colorResource(id = R.color.black)
    )
}

@Composable
fun MyTextField(
    text: String,
    labelValue: String,
    imageVector: ImageVector,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false,
    focusRequester: FocusRequester,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier,
    placeholder: String? = null
) {
    OutlinedTextField(modifier = modifier.focusRequester(focusRequester),
        label = { Text(text = labelValue) },
        value = text,
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = colorResource(id = R.color.black),
            focusedBorderColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
        ),

        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        onValueChange = {
            onTextSelected(it)
        },
        placeholder = {
            if (placeholder != null) {
                Text(text = placeholder)
            }
        },
        leadingIcon = {
            Icon(imageVector = imageVector, contentDescription = "")
        },
        isError = !errorStatus,
        supportingText = {
            if (!errorStatus) {
                Text("Required")
            }
        }

    )
    Spacer(modifier = Modifier.height(5.dp))
}

@Composable
fun TextError(labelError: String) {
    Text(
        text = labelError,
        style = TextStyle(color = Color.Red, fontSize = 12.sp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 35.dp),
    )
}

@Composable
fun MottoTextArea(
    motto: String,
    onMottoChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = motto,
        onValueChange = onMottoChanged,
        label = { Text("Motto Hidup") },
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp), // Atur tinggi sesuai kebutuhan
        textStyle = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Start),
        maxLines = 10, // Atur jumlah maksimal baris atau biarkan tidak terbatas
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = colorResource(id = R.color.black),
            focusedBorderColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
        ),
    )
}


@Composable
fun PasswordTextFieldComponent(
    text: String,
    labelValue: String,
    imageVector: ImageVector,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false,
    focusRequester: FocusRequester,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(modifier = modifier.focusRequester(focusRequester),
        label = { Text(text = labelValue) },
        value = text,
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = colorResource(id = R.color.black),
            focusedBorderColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        onValueChange = {
            onTextSelected(it)
        },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = {
            Icon(imageVector = imageVector, contentDescription = "")
        },
        trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = stringResource(R.string.password_toggle)
                )
            }
        },
        isError = !errorStatus,
        supportingText = {
            if (!errorStatus) {
                Text("Required")
            }
        })
}

@Composable
fun CheckboxComponents(
    value: String,
    onTextSelected: (String) -> Unit,
    checkedState: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkedState,
            onCheckedChange = { isChecked ->
                onCheckedChange(isChecked)
                Log.d("Checkbox Clicked", "$isChecked")
            }
        )
        ClickableTextComponents(value = value, onTextSelected = onTextSelected)
    }
}

@Composable
fun ClickableTextComponents(value: String, onTextSelected: (String) -> Unit) {
    val initialText = "By continuing you accept our "
    val privacyPolicyText = "Privacy Policy"
    val andText = " and "
    val termsAndConditionsText = "Term of Use"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
        }
        append(andText)
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            pushStringAnnotation(tag = termsAndConditionsText, annotation = termsAndConditionsText)
            append(termsAndConditionsText)
        }
    }

    ClickableText(text = annotatedString, onClick = { offset ->
        annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.also { span ->
            Log.d("ClickableTextComponents", "$span")
            if ((span.item == termsAndConditionsText) || (span.item == privacyPolicyText)) {
                onTextSelected(span.item)
            }
        }
    })
}

@Composable
fun ButtonComponent(
    value: String, onNavigate: () -> Unit, isEnabled: Boolean = false
) {
    Button(
        onClick = {
            onNavigate.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 35.dp),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(6.dp),
        enabled = isEnabled
    ) {
        Text(text = value, fontSize = 18.sp)
    }
}

@Composable
fun ButtonComponentWithIcon(value: String, painterResource: Painter, onNavigate: () -> Unit) {
    Button(
        onClick = onNavigate,
        elevation = ButtonDefaults.buttonElevation(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray, // Set background color to gray
        ),
        shape = RoundedCornerShape(6.dp),
    ) {
        Icon(
            modifier = Modifier.padding(end = 5.dp),
            painter = painterResource,
            contentDescription = "",
            tint = Color.Black, // Set icon color to black
        )
        Text(text = value, fontSize = 13.sp)
    }
}


@Composable
fun DividerTextComponent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 35.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = colorResource(id = R.color.black),
            thickness = 1.dp,
        )
        Text(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            text = "Or Login With",
            fontSize = 12.sp
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = colorResource(id = R.color.black),
            thickness = 1.dp,
        )
    }
}

@Composable
fun TextButtonComponent(value1: String, value2: String, onNavigate: () -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Text(text = value1, fontSize = 12.sp)
        TextButton(onClick = onNavigate) {
            Text(
                text = value2, style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.inter_medium)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFFB22222),
                )
            )
        }
    }
}

@Composable
fun TextButtonComponent2(value: String, onNavigate: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 35.dp), Arrangement.End
    ) {
        TextButton(onClick = onNavigate) {
            Text(
                text = value, style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFFB22222),
                )
            )
        }
    }
}

@Composable
fun OtpView(
    modifier: Modifier = Modifier,
    otpText: String = "",
    charColor: Color = Color(0XFFE8E8E8),
    charBackground: Color = Color.Transparent,
    charSize: TextUnit = 20.sp,
    containerSize: Dp = charSize.value.dp * 2,
    otpCount: Int = 6,
    type: Int = OTP_VIEW_TYPE_BORDER,
    enabled: Boolean = true,
    password: Boolean = false,
    passwordChar: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
    onOtpTextChange: (String) -> Unit
) {
    BasicTextField(
        modifier = modifier,
        value = otpText,
        onValueChange = {
            if (it.length <= otpCount) {
                onOtpTextChange.invoke(it)
            }
        },
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        decorationBox = {
            Row(horizontalArrangement = Arrangement.SpaceAround) {
                repeat(otpCount) { index ->
                    Spacer(modifier = Modifier.width(2.dp))
                    CharView(
                        index = index,
                        text = otpText,
                        charColor = charColor,
                        charSize = charSize,
                        containerSize = containerSize,
                        type = type,
                        charBackground = charBackground,
                        password = password,
                        passwordChar = passwordChar,
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                }
            }
        })
}

@Composable
private fun CharView(
    index: Int,
    text: String,
    charColor: Color,
    charSize: TextUnit,
    containerSize: Dp,
    type: Int = OTP_VIEW_TYPE_UNDERLINE,
    charBackground: Color = Color.Transparent,
    password: Boolean = false,
    passwordChar: String = ""
) {
    val modifier = if (type == OTP_VIEW_TYPE_BORDER) {
        Modifier
            .size(containerSize)
            .border(
                width = 3.dp,
                color = charColor,
                shape = androidx.compose.material.MaterialTheme.shapes.medium
            )
            .padding(bottom = 4.dp)
            .background(charBackground)
    } else Modifier
        .width(containerSize)
        .background(charBackground)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val char = when {
            index >= text.length -> ""
            password -> passwordChar
            else -> text[index].toString()
        }
        androidx.compose.material.Text(
            text = char,
            color = Color.Black,
            modifier = modifier.wrapContentHeight(),
            style = androidx.compose.material.MaterialTheme.typography.body1,
            fontSize = charSize,
            textAlign = TextAlign.Center,
        )
        if (type == OTP_VIEW_TYPE_UNDERLINE) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .background(charColor)
                    .height(1.dp)
                    .width(containerSize)
            )
        }
    }
}


@Composable
fun ButtonSubmit(
    labelButton: String,
    click: () -> Unit
) {
    Button(
        onClick = {
            click.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 35.dp),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(6.dp),
    ) {
        Text(text = labelButton, fontSize = 18.sp)
    }
}