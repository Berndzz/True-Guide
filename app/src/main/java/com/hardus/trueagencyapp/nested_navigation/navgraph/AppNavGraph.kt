package com.hardus.trueagencyapp.nested_navigation.navgraph

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hardus.trueagencyapp.main_content.AppScreen
import com.hardus.trueagencyapp.nested_navigation.APP_GRAPH_ROUTE
import com.hardus.trueagencyapp.nested_navigation.Screen

fun NavGraphBuilder.appNavGraph(
    navController: NavHostController,
    windowSize: WindowWidthSizeClass,
    onBackPressed: () -> Unit,
) {
    navigation(
        startDestination = Screen.Home.route,
        route = APP_GRAPH_ROUTE
    ) {
        composable(route = Screen.Home.route) {
            AppScreen(navController = navController, windowSize, onBackPressed)
        }
    }
}