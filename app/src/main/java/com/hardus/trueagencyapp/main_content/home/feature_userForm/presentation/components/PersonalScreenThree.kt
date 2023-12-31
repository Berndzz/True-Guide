package com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.TextSnippet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.component.field_component.MottoTextArea
import com.hardus.trueagencyapp.component.field_component.MyTextField
import com.hardus.trueagencyapp.main_content.home.feature_userForm.domain.model.FormViewModel
import com.hardus.trueagencyapp.ui.theme.TrueAgencyAppTheme


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PersonalScreenThree(viewModel: FormViewModel, modifier: Modifier = Modifier) {

    val (focusVision) = remember { FocusRequester.createRefs() }

    Column(
        modifier = modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MyTextField(
            text = viewModel.visiResponse,
            labelValue = stringResource(id = R.string.vision),
            imageVector = Icons.Outlined.TextSnippet,
            onTextSelected = viewModel::onVisiChanged,
            errorStatus = true,
            focusVision,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Life Motto
        MottoTextArea(
            motto = viewModel.lifeMottoResponse,
            onMottoChanged = viewModel::onLifeMottoChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CheckPersonalScreenThree() {
    TrueAgencyAppTheme {
        PersonalScreenThree(viewModel = FormViewModel())
    }
}