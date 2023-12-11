package com.hardus.trueagencyapp.nested_navigation.navgraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hardus.trueagencyapp.main_content.AppScreen
import com.hardus.trueagencyapp.main_content.absent.AbsentScreen
import com.hardus.trueagencyapp.main_content.program.ProgramScreen
import com.hardus.trueagencyapp.main_content.setting.SettingScreen
import com.hardus.trueagencyapp.nested_navigation.APP_GRAPH_ROUTE
import com.hardus.trueagencyapp.nested_navigation.Screen

fun NavGraphBuilder.appNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Home.route,
        route = APP_GRAPH_ROUTE
    ) {
        composable(route = Screen.Home.route) {
            AppScreen(navController = navController)
        }
        composable(route = Screen.Program.route) {
            ProgramScreen(navController = navController)
        }
        composable(route = Screen.Absent.route) {
            AbsentScreen(navController = navController)
        }
        composable(route = Screen.Setting.route) {
            SettingScreen(navController = navController)
        }
    }
}