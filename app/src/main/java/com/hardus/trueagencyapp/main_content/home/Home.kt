package com.hardus.trueagencyapp.main_content.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.auth.viewmodel.AuthViewModel
import com.hardus.trueagencyapp.component.ButtonComponent
import com.hardus.trueagencyapp.nested_navigation.AUTH_GRAPH_ROUTE

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    val viewModel = hiltViewModel<AuthViewModel>()
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface {
            Column(
                modifier = Modifier.padding(25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Home Screen", style = MaterialTheme.typography.displaySmall)
                Text(
                    text = viewModel.currentUser?.displayName ?: "",
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = viewModel.currentUser?.email ?: "",
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = viewModel.currentUser?.phoneNumber ?: "",
                    style = MaterialTheme.typography.displaySmall
                )
                ButtonComponent(
                    value = stringResource(R.string.log_out), onNavigate = {
                        viewModel.logout()
                        navController.navigate(route = AUTH_GRAPH_ROUTE) {
                            popUpTo(AUTH_GRAPH_ROUTE) { inclusive = true }
                        }
                    }, isEnabled = true
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Hardus")
@Composable
//CheckNewPasswordScreenPhone
fun CheckHomeScreenPhone() {
    HomeScreen(rememberNavController())
}

@Preview(showBackground = true, showSystemUi = true, name = "Hardus", device = Devices.TABLET)
@Composable
//CheckNewPasswordScreenPhone
fun CheckHomeScreenPhoneTablet() {
    HomeScreen(rememberNavController())
}

