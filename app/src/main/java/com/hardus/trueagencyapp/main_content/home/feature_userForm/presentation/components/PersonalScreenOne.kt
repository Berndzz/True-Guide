package com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.TextFields
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.component.field_component.MyTextField
import com.hardus.trueagencyapp.main_content.home.feature_userForm.domain.model.FormViewModel
import com.hardus.trueagencyapp.ui.theme.TrueAgencyAppTheme


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PersonalScreenOne(viewModel: FormViewModel, modifier: Modifier = Modifier) {

    val (focusFullName, focusAddress,focusTitleLeader) = remember { FocusRequester.createRefs() }

    val radioButtonColors = RadioButtonDefaults.colors(
        selectedColor = MaterialTheme.colorScheme.primaryContainer,
        unselectedColor = MaterialTheme.colorScheme.onBackground
    )

    LazyColumn(
        modifier = modifier
            .padding(20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
    ) {
        item {
            // Nama Lengkap
            MyTextField(
                text = viewModel.fullNameResponse,
                labelValue = stringResource(id = R.string.fullname),
                imageVector = Icons.Outlined.Person,
                onTextSelected = viewModel::onFullNameChange,
                errorStatus = true,
                focusFullName,
                keyboardOptions = KeyboardOptions.Default,
                keyboardActions = KeyboardActions.Default,
                modifier = Modifier
                    .fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(8.dp))

            // Alamat
            MyTextField(
                text = viewModel.addressResponse,
                labelValue = stringResource(id = R.string.address),
                imageVector = Icons.Outlined.LocationOn,
                onTextSelected = viewModel::onAddressChange,
                errorStatus = true,
                focusAddress,
                keyboardOptions = KeyboardOptions.Default,
                keyboardActions = KeyboardActions.Default,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            DatePickerForm(
                label = "Tanggal Lahir",
                date = viewModel.dateOfBirth,
                onDateChanged = viewModel::onDateOfBirthChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .height(54.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Status", fontSize = 20.sp)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(vertical = 10.dp)
            ) {

                RadioButton(
                    selected = viewModel.isBusinessPartner == true,
                    onClick = { viewModel.onRoleChanged(true) },
                    colors = radioButtonColors
                )
                Text("Bisnis Partner")

                RadioButton(
                    selected = viewModel.isBusinessPartner == false,
                    onClick = { viewModel.onRoleChanged(false) },
                    colors = radioButtonColors
                )
                Text("Leader")

                Spacer(modifier = Modifier.width(8.dp))
            }

            if (viewModel.isLeader) {
                MyTextField(
                    text = viewModel.leaderTitle,
                    labelValue = stringResource(id = R.string.title_leader),
                    imageVector = Icons.Outlined.TextFields,
                    onTextSelected = viewModel::onLeaderTitleChanged,
                    errorStatus = true,
                    focusTitleLeader,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
                    keyboardActions = KeyboardActions.Default,
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = "AAD/AD"
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CheckPersonaScreenOne() {
    TrueAgencyAppTheme {
        PersonalScreenOne(viewModel = FormViewModel())
    }
}