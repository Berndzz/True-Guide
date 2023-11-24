package com.hardus.trueagencyapp.auth.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hardus.trueagencyapp.R

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(
    labelValue: String,
    imageVector: ImageVector,
    focusRequester: FocusRequester,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions
) {
    var textValue by remember { mutableStateOf("") }
    OutlinedTextField(modifier = Modifier
        .fillMaxWidth()
        .focusRequester(focusRequester)
        .padding(horizontal = 35.dp),
        label = { Text(text = labelValue) },
        value = textValue,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
            cursorColor = colorResource(id = R.color.black),
        ),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        onValueChange = { textValue = it },
        leadingIcon = {
            Icon(imageVector = imageVector, contentDescription = "")
        })
    Spacer(modifier = Modifier.height(25.dp))
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextFieldComponent(
    labelValue: String,
    imageVector: ImageVector,
    focusRequester: FocusRequester,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions
) {
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(modifier = Modifier
        .fillMaxWidth()
        .focusRequester(focusRequester)
        .padding(horizontal = 35.dp),
        label = { Text(text = labelValue) },
        value = password,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
            cursorColor = colorResource(id = R.color.black),
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        onValueChange = { password = it },
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
        })
}

@Composable
fun CheckboxComponents(value: String, onTextSelected: (String) -> Unit) {
    var checkedState by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = checkedState, onCheckedChange = {
            checkedState = !checkedState
            Log.d("Checkbox Clicked", "$checkedState")
        })
        ClickabelTextComponents(value = value, onTextSelected = onTextSelected)
    }
}

@Composable
fun ClickabelTextComponents(value: String, onTextSelected: (String) -> Unit) {
    val initialText = "By continuing you accept our "
    val privacyPolicyText = "Privacy Policy"
    val andText = "and"
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
fun ButtonComponent(value: String, onNavigate: () -> Unit) {
    Button(
        onClick = onNavigate,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 35.dp),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(6.dp)
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