package com.hardus.trueagencyapp.main_content.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.auth.component.ButtonComponent
import com.hardus.trueagencyapp.auth.viewmodel.LoginViewModel

@Composable
fun HomeScreen(loginViewModel: LoginViewModel = viewModel()) {
    Column(
        modifier = Modifier.padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home Screen", style = MaterialTheme.typography.displaySmall)
        ButtonComponent(
            value = stringResource(R.string.log_out),
            onNavigate = {},
            isEnabled = true
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Hardus")
@Composable
//CheckNewPasswordScreenPhone
fun CheckHomeScreenPhone() {
    HomeScreen()
}

