package com.hardus.trueagencyapp.nested_navigation.navgraph

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.hardus.trueagencyapp.main_content.AppScreen
import com.hardus.trueagencyapp.main_content.home.feature_members.presentation.MembersScreen
import com.hardus.trueagencyapp.main_content.home.feature_members.presentation.MembersViewModel
import com.hardus.trueagencyapp.main_content.home.feature_note.domain.model.NoteCategory
import com.hardus.trueagencyapp.main_content.home.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.hardus.trueagencyapp.main_content.home.feature_note.presentation.notes.NoteScreen
import com.hardus.trueagencyapp.main_content.home.feature_qrcode.presentation.QrScanningScreen
import com.hardus.trueagencyapp.main_content.home.feature_userForm.domain.model.FormViewModel
import com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation.FormRoute
import com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation.UserFormScreen
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
        composable(route = Screen.FormUsers.route) {
            val formViewModel: FormViewModel = viewModel()
            UserFormScreen(
                viewModel = formViewModel,
                onNavigate = {
                    navController.popBackStack()
                    navController.navigate(route = Screen.Home.route)
                },
                onFormPage = {
                    navController.popBackStack()
                    navController.navigate(route = Screen.FormFragment.route)
                }
            )
        }
        composable(route = Screen.NoteSc.route) {
            NoteScreen(
                onNavigate = {
                    navController.popBackStack()
                    navController.navigate(route = Screen.Home.route)
                },
                navController = navController
            )
        }
        composable(route = Screen.QrCode.route) {
            QrScanningScreen(onNavigate = {
                navController.popBackStack()
                navController.navigate(route = Screen.Home.route)
            })
        }
        composable(route = Screen.Members.route) {
            val membersViewModel: MembersViewModel = viewModel()
            MembersScreen(
                onNavigate = {
                    navController.popBackStack()
                    navController.navigate(route = Screen.Home.route)
                },
                windowSize = windowSize,
                onBackPressed = onBackPressed,
                viewModel = membersViewModel
            )
        }
        composable(
            route = Screen.AddEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}&noteCategory={noteCategory}",
            arguments = listOf(
                navArgument(
                    name = "noteId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = "noteColor"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument("noteCategory") {
                    type = NavType.StringType
                    defaultValue = NoteCategory.Category.name // Atau default value lain yang sesuai
                }
            )
        ) {
            val color = it.arguments?.getInt("noteColor") ?: -1
            val category = it.arguments?.getString("noteCategory") ?: NoteCategory.PROSPECT.name
            AddEditNoteScreen(
                navController = navController,
                noteColor = color,
                noteCategory = category,
                onNavigate = {
                    navController.popBackStack()
                    navController.navigate(route = Screen.NoteSc.route)
                }
            )
        }
        composable(route = Screen.FormFragment.route) {
            FormRoute(
                onFormComplete = {
                    navController.navigate(route = Screen.FormUsers.route)
                },
                onNavUp = navController::navigateUp
            )
        }
    }
}