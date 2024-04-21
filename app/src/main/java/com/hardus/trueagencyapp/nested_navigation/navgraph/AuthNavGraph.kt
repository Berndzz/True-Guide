package com.hardus.trueagencyapp.nested_navigation.navgraph

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hardus.trueagencyapp.auth.screen.ForgotPasswordScreen
import com.hardus.trueagencyapp.auth.screen.LoginScreen
import com.hardus.trueagencyapp.auth.screen.RegistrationScreen
import com.hardus.trueagencyapp.auth.screen.TermAndConditionScreen
import com.hardus.trueagencyapp.nested_navigation.APP_GRAPH_ROUTE
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
            LoginScreen(
                onLogin = {
                    Log.d("Navigation LoginScreen", "APP_GRAPH_ROUTE")
                    navController.popBackStack() // popbackStack berarti ketika klik back button di handphone akan langsung keluar aplikasi
                    navController.navigate(route = APP_GRAPH_ROUTE) {
                        popUpTo(AUTH_GRAPH_ROUTE) { inclusive = true }
                    }
                },
                onRegister = {
                    Log.d("Navigation LoginScreen", "onRegister")
                    navController.popBackStack()
                    navController.navigate(route = Screen.Register.route)
                },
                onForgotPassword = {
                    navController.popBackStack()
                    navController.navigate(route = Screen.ForgotPassword.route)
                },
            )
        }
        composable(route = Screen.Register.route) {
            RegistrationScreen(
                onLogin = {
                    Log.d("Navigation RegisterScreen", "APP_GRAPH_ROUTE")
                    navController.popBackStack()
                    navController.navigate(route = APP_GRAPH_ROUTE) {
                        popUpTo(APP_GRAPH_ROUTE) { inclusive = true }
                    }
                },
                onTermAndCondition = {
                    //  Log.d("Navigation RegisterScreen", "APP_GRAPH_ROUTE")
                    navController.popBackStack()
                    navController.navigate(Screen.TermAndCondition.route)
                },
                onBackToLoginScreen = {
                    // Log.d("Navigation RegisterScreen", "APP_GRAPH_ROUTE")
                    navController.popBackStack()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onBackToLoginScreen = {
                    // Log.d("Navigation RegisterScreen", "APP_GRAPH_ROUTE")
                    navController.popBackStack()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.TermAndCondition.route) {
            TermAndConditionScreen(onNavigate = {
                navController.navigate(Screen.Register.route)
            })
        }
    }
}