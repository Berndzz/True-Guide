package com.hardus.trueagencyapp.auth.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hardus.trueagencyapp.auth.screen.LoginScreen
import com.hardus.trueagencyapp.auth.screen.RegisterScreen

@Composable
fun AuthNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.screenLogin) {
        composable(route = Route.screenLogin) {
            LoginScreen(navController)
        }
        composable(route = Route.screenRegister) {
            RegisterScreen(navController)
        }
    }
}

object Route {
    const val screenLogin = "ScreenLogin"
    const val screenRegister = "ScreenRegister"
    const val screenForgotPassword = "ScreenForgotPassword"
}