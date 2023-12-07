package com.hardus.trueagencyapp.nested_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hardus.trueagencyapp.nested_navigation.navgraph.appNavGraph
import com.hardus.trueagencyapp.nested_navigation.navgraph.authNavGraph
import com.hardus.trueagencyapp.nested_navigation.navgraph.onboardingNavGraph

@Composable
fun NavigationAllScreen(
    navController: NavHostController, startDestination: String
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        route = ROOT_GRAPH_ROUTE
    ) {
        onboardingNavGraph(navController = navController)
        authNavGraph(navController = navController)
        appNavGraph(navController = navController)
    }
}