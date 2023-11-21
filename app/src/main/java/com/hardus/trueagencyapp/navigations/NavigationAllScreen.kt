package com.hardus.trueagencyapp.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
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
import com.hardus.trueagencyapp.main_content.home.HomeScreen
import com.hardus.trueagencyapp.onboarding.screen.OnboardingScreenOne

@Composable
fun NavigationAllScreen(
    navController: NavHostController, startDestination: String
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {

        composable(route = Route.screenOnBoarding) {
            OnboardingScreenOne(navController = navController)
        }
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
                navArgument(name = "email") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            OtpCodeScreen(navController, backStackEntry.arguments?.getString("email"))
        }
        composable(route = Route.screenLoginViaPhone) {
            LoginPhoneNumberScreen(navController)
        }
        composable(route = Route.screenNewPassword) {
            NewPasswordScreen(navController)
        }
        composable(route = Route.screenHome) {
            HomeScreen()
        }
    }

}

object Route {
    const val screenLogin = "ScreenLogin"
    const val screenRegister = "ScreenRegister"
    const val screenForgotPassword = "ScreenForgotPassword"
    const val screenLoginViaPhone = "ScreenLoginViaPhone"
    const val screenOTPCode = "ScreenOTPCode"
    const val screenNewPassword = "ScreenNewPassword"
    const val screenOnBoarding = "onBoardingScreen"
    const val screenHome = "homeScreen"
}