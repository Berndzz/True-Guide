package com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.component.field_component.MyTextField
import com.hardus.trueagencyapp.main_content.home.feature_userForm.domain.model.FormViewModel
import com.hardus.trueagencyapp.ui.theme.TrueAgencyAppTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PersonalScreenTwo(viewModel: FormViewModel, modifier: Modifier = Modifier) {

    val (focusAgentCode) = remember { FocusRequester.createRefs() }

    LaunchedEffect(key1 = true) {
        viewModel.fetchUnitOptions()
    }

    LazyColumn(
        modifier = modifier.padding(20.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        item {
            MyTextField(
                text = viewModel.agentCodeResponse,
                labelValue = stringResource(id = R.string.agent_code),
                imageVector = Icons.Outlined.Person,
                onTextSelected = viewModel::onAgentCodeChanged,
                errorStatus = true,
                focusAgentCode,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions.Default,
                modifier = Modifier
                    .fillMaxWidth()

            )

            Spacer(modifier = Modifier.height(8.dp))

            DatePickerForm(
                label = "AAJI Exam Date",
                date = viewModel.ajjExamDateResponse,
                onDateChanged = viewModel::onAjjExamDateChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .height(54.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            DatePickerForm(
                label = "AASI Exam Date",
                date = viewModel.aasiExamDateResponse,
                onDateChanged = viewModel::onAasiExamDateChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .height(54.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            UnitDropdown(
                textLabel = "Unit",
                selectedUnit = viewModel.selectedUnit,
                onUnitSelected = viewModel::onUnitSelected,
                unitOptions = viewModel.unitOptions,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CheckPersonaScreenTwo() {
    TrueAgencyAppTheme {
        PersonalScreenTwo(viewModel = FormViewModel())
    }
}