package com.hardus.trueagencyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.hardus.trueagencyapp.auth.navigation.AuthNavigation
import com.hardus.trueagencyapp.auth.screen.LoginScreen
import com.hardus.trueagencyapp.auth.screen.RegisterScreen
import com.hardus.trueagencyapp.ui.theme.TrueAgencyAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrueAgencyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    //LoginScreen()
                    AuthNavigation()
                }
            }
        }
    }
}



