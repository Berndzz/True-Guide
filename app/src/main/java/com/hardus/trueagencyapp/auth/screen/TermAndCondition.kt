package com.hardus.trueagencyapp.auth.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.component.field_component.HeadingTextComponent

@Composable
fun TermAndConditionScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        HeadingTextComponent(value = stringResource(id = R.string.term_and_condition))
    }
}

@Preview(showBackground = true, name = "Hardus")
@Composable
fun CheckTermAndConditionScreenPhone() {
    TermAndConditionScreen()
}