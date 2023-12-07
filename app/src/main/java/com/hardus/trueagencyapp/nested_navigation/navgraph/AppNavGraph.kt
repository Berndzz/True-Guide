package com.hardus.trueagencyapp.nested_navigation.navgraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.hardus.trueagencyapp.main_content.home.HomeScreen
import com.hardus.trueagencyapp.main_content.setting.ProfilScreen
import com.hardus.trueagencyapp.nested_navigation.DETAIL_ARGUMENT_KEY
import com.hardus.trueagencyapp.nested_navigation.DETAIL_ARGUMENT_KEY2
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
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument(DETAIL_ARGUMENT_KEY) {
                    type = NavType.IntType
                    defaultValue = 0
                },
                navArgument(DETAIL_ARGUMENT_KEY2) {
                    type = NavType.StringType
                    defaultValue = "Hardus Tukan"
                }
            )
        ) {
            ProfilScreen()
        }
    }
}