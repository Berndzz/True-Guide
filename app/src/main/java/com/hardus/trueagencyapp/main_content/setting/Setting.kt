package com.hardus.trueagencyapp.main_content.setting

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.auth.viewmodel.AuthViewModel
import com.hardus.trueagencyapp.component.field_component.ButtonComponent
import com.hardus.trueagencyapp.component.setting_component.SettingComponents
import com.hardus.trueagencyapp.nested_navigation.AUTH_GRAPH_ROUTE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavController,
    onReport: () -> Unit,
    onTermApps: () -> Unit,
    onPrivacy: () -> Unit,
    onAboutApp: () -> Unit,
) {
    val viewModel = hiltViewModel<AuthViewModel>()

    Scaffold(topBar = {}, content = { paddingValue ->
        Surface(
            color = Color.Transparent,
            modifier = Modifier.padding(paddingValue)
        ) {
            LazyColumn() {
                item {
                    ProfileCard(viewModel = viewModel)
                }

                item {

                    SettingComponents(
                        iconStart = Icons.Default.Message,
                        iconEnd = Icons.Default.ArrowForwardIos,
                        text = "Laporan Keluhan",
                        onNavigate = onReport

                    )

                    SettingComponents(
                        iconStart = Icons.Default.ListAlt,
                        iconEnd = Icons.Default.ArrowForwardIos,
                        text = "Syarat Ketentuan",
                        onNavigate = onTermApps
                    )

                    SettingComponents(
                        iconStart = Icons.Default.PrivacyTip,
                        iconEnd = Icons.Default.ArrowForwardIos,
                        text = "Kebijakan Privasi",
                        onNavigate = onPrivacy
                    )

                    SettingComponents(
                        iconStart = Icons.Default.TouchApp,
                        iconEnd = Icons.Default.ArrowForwardIos,
                        text = "Tentang Aplikasi",
                        onNavigate = onAboutApp
                    )
                }

                item {
                    Logout(viewModel = viewModel, navController = navController)
                }
            }
        }
    })
}

@Composable
fun ProfileCard(viewModel: AuthViewModel) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(4.dp))
            .padding(16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondary)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.padding(5.dp))
            CardImage()
            TextCard(viewModel = viewModel)
        }
    }
    Spacer(modifier = Modifier.padding(10.dp))
}

@Composable
fun CardImage() {
    val painter = painterResource(R.drawable.icon_true)
    Card(
        shape = CircleShape,
        border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary),
        modifier = Modifier.size(50.dp),
    ) {
        Image(
            painter = painter,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(48.dp),
            contentDescription = "Profile picture holder"
        )
    }
}

@Composable
fun TextCard(viewModel: AuthViewModel) {
    Column(
        modifier = Modifier.padding(
            vertical = 8.dp,
            horizontal = 16.dp,
        )
    ) {
        Text(
            text = "${viewModel.currentUser?.displayName}", style = TextStyle(
                fontFamily = FontFamily.SansSerif, fontSize = 25.sp, fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = "${viewModel.currentUser?.email}", style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color.LightGray
            )
        )
    }
}

@Composable
fun Logout(viewModel: AuthViewModel, navController: NavController) {

    Spacer(modifier = Modifier.padding(10.dp))

    ButtonComponent(
        value = "Logout", onNavigate = {
            viewModel.logout()
            navController.navigate(route = AUTH_GRAPH_ROUTE) {
                Log.d("Navigation", "back to login screen")
                popUpTo(AUTH_GRAPH_ROUTE) { inclusive = true }
            }
        },
        isEnabled = true
    )
}


@Preview(showBackground = true, showSystemUi = true, name = "Hardus")
@Composable
fun CheckHomeScreenPhone() {
    SettingScreen(
        navController = rememberNavController(),
        onReport = { /*TODO*/ },
        onTermApps = { /*TODO*/ },
        onPrivacy = { /*TODO*/ },
        onAboutApp = {/*TODO*/ }
    )
}

