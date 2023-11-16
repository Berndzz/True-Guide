package com.hardus.trueagencyapp.auth.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hardus.trueagencyapp.auth.screen.ForgotPasswordScreen
import com.hardus.trueagencyapp.auth.screen.LoginPhoneNumberScreen
import com.hardus.trueagencyapp.auth.screen.LoginScreen
import com.hardus.trueagencyapp.auth.screen.NewPasswordScreen
import com.hardus.trueagencyapp.auth.screen.OtpCodeScreen
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
        composable(route = Route.screenForgotPassword) {
            ForgotPasswordScreen(navController)
        }
        composable(
            route = Route.screenOTPCode + "/{email}",
            arguments = listOf(
                navArgument(name = "email"){
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            OtpCodeScreen(navController,backStackEntry.arguments?.getString("email"))
        }
        composable(route = Route.screenLoginViaPhone) {
            LoginPhoneNumberScreen(navController)
        }
        composable (route = Route.screenNewPassword) {
            NewPasswordScreen(navController)
        }
    }
}

object Route {
    val value = ""
    const val screenLogin = "ScreenLogin"
    const val screenRegister = "ScreenRegister"
    const val screenForgotPassword = "ScreenForgotPassword"
    const val screenLoginViaPhone = "ScreenLoginViaPhone"
    const val screenOTPCode = "ScreenOTPCode"
    const val screenNewPassword = "ScreenNewPassword"
}