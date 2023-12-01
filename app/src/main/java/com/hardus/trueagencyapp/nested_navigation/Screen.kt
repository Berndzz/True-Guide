package com.hardus.trueagencyapp.nested_navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

const val DETAIL_ARGUMENT_KEY = "id"
const val DETAIL_ARGUMENT_KEY2 = "name"

const val ROOT_GRAPH_ROUTE = "root"
const val AUTH_GRAPH_ROUTE = "auth"
const val HOME_GRAPH_ROUTE = "home"
const val ONBOARDING_GRAPH_ROUTE = "onboarding"

sealed class Screen(val route: String) {

    // app root
    object Home : Screen(route = "home_screen")
    object Detail : Screen(route = "detail_screen?id={id}&name={name}") {
        fun passNameAndId(
            id: Int = 0,
            name: String = "Hardus Tukan"
        ): String {
            return "detail_screen?id=$id&name=$name"
        }
    }

    // auth root
    object Login : Screen("login_screen")
    object Register : Screen("register_screen")
    object ForgotPassword : Screen("forgotPass_screen")
    object TermAndCondition : Screen("termAndCondition_screen")
    object OtpCode : Screen("otp_screen")
    object NewPassword : Screen("newPass_screen")

    // onboarding root
    object Onboarding : Screen("onboarding_screen")
}
