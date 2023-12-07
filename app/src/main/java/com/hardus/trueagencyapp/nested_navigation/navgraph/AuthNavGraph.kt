package com.hardus.trueagencyapp.nested_navigation.navgraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hardus.trueagencyapp.auth.screen.ForgotPasswordScreen
import com.hardus.trueagencyapp.auth.screen.LoginScreen
import com.hardus.trueagencyapp.auth.screen.NewPasswordScreen
import com.hardus.trueagencyapp.auth.screen.OtpCodeScreen
import com.hardus.trueagencyapp.auth.screen.RegistrationScreen
import com.hardus.trueagencyapp.auth.screen.TermAndConditionScreen
import com.hardus.trueagencyapp.nested_navigation.AUTH_GRAPH_ROUTE
import com.hardus.trueagencyapp.nested_navigation.Screen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Login.route,
        route = AUTH_GRAPH_ROUTE
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.Register.route) {
            RegistrationScreen(navController = navController)
        }
        composable(route = Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navController = navController)
        }
        composable(route = Screen.OtpCode.route) {
            OtpCodeScreen(navController = navController)
        }
        composable(route = Screen.NewPassword.route) {
            NewPasswordScreen(navController = navController)
        }
        composable(route = Screen.TermAndCondition.route) {
            TermAndConditionScreen()
        }
    }
}