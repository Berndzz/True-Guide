package com.hardus.trueagencyapp.nested_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

const val DETAIL_ARGUMENT_KEY = "id"
const val DETAIL_ARGUMENT_KEY2 = "name"

const val ROOT_GRAPH_ROUTE = "root"
const val AUTH_GRAPH_ROUTE = "auth"
const val APP_GRAPH_ROUTE = "home"
const val ONBOARDING_GRAPH_ROUTE = "onboarding"

sealed class Screen(
    val route: String,
    val title: String? = null,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null
) {

    // app root
    object Home :
        Screen(
            route = "home_screen",
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        )
    object Program :
        Screen(
            route = "program_screen",
            title = "Program",
            selectedIcon = Icons.Filled.GridView,
            unselectedIcon = Icons.Outlined.GridView
        )
    object Absent :
        Screen(
            route = "absent_screen",
            title = "Absent",
            selectedIcon = Icons.Default.EditNote,
            unselectedIcon = Icons.Outlined.EditNote,
        )
    object Setting :
        Screen(
            route = "setting_screen",
            title = "Setting",
            selectedIcon = Icons.Default.Settings,
            unselectedIcon = Icons.Outlined.Settings,
        )


    //App - Home
    object FormUsers : Screen(route = "formUsers_screen")
    object NoteSc : Screen(route = "note_screen")
    object AddEditNoteScreen: Screen(route = "add_edit_note_screen")
    object QrCode : Screen(route = "qrcode_screen")
    object Members : Screen(route = "members_screen")
    object EachMemberAbsentScreen : Screen(route = "each_member_absent_screen")
    object FormFragment: Screen(route = "formFragment_screen")

    // App - Setting
    object ChangeLanguage : Screen(route = "changeLanguage_screen")
    object Notification : Screen(route = "notification_screen")
    object Report : Screen(route = "report_screen")
    object Terms : Screen(route = "terms_screen")
    object Privacy : Screen(route = "privacy_screen")
    object About : Screen(route = "about_screen")


    // auth root
    object Login : Screen("login_screen")
    object Register : Screen("register_screen")
    object ForgotPassword : Screen("forgotPass_screen")
    object TermAndCondition : Screen("termAndCondition_screen")

    // onboarding root
    object Onboarding : Screen("onboarding_screen")
}
