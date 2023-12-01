package com.hardus.trueagencyapp.nested_navigation.navgraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hardus.trueagencyapp.nested_navigation.ONBOARDING_GRAPH_ROUTE
import com.hardus.trueagencyapp.nested_navigation.Screen
import com.hardus.trueagencyapp.onboarding.screen.OnboardingScreenOne

fun NavGraphBuilder.onboardingNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Onboarding.route,
        route = ONBOARDING_GRAPH_ROUTE
    ) {
        composable(route = Screen.Onboarding.route){
            OnboardingScreenOne(navController = navController)
        }
    }
}