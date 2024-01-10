package com.hardus.trueagencyapp.main_content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hardus.auth.screen.view.home.feature_qrcode.presentation.QrScanViewModel
import com.hardus.auth.screen.view.setting.task.AboutScreen
import com.hardus.auth.screen.view.setting.task.ChangeLanguageScreen
import com.hardus.auth.screen.view.setting.task.NotificationScreen
import com.hardus.auth.screen.view.setting.task.PrivacyScreen
import com.hardus.auth.screen.view.setting.task.ReportScreen
import com.hardus.auth.screen.view.setting.task.TermsScreen
import com.hardus.trueagencyapp.main_content.absent.presentation.AbsentScreen
import com.hardus.trueagencyapp.main_content.home.domain.model.HomeViewModel
import com.hardus.trueagencyapp.main_content.home.feature_userForm.domain.model.FormViewModel
import com.hardus.trueagencyapp.main_content.home.presentation.HomeScreen
import com.hardus.trueagencyapp.main_content.program.ProgramScreen
import com.hardus.trueagencyapp.main_content.program.ProgramViewModel
import com.hardus.trueagencyapp.main_content.setting.SettingScreen
import com.hardus.trueagencyapp.nested_navigation.Screen


@Composable
fun AppScreen(
    navController: NavHostController,
    windowSize: WindowWidthSizeClass,
    onBackPressed: () -> Unit,
) {
    val programViewModel: ProgramViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()
    val formViewModel: FormViewModel = viewModel()
    val qrViewModel: QrScanViewModel = hiltViewModel()
    val bottomNavController = rememberNavController()
    Scaffold(bottomBar = {
        BottomNavigationBar(bottomNavController = bottomNavController)
    }, content = { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            NavHost(
                navController = bottomNavController, startDestination = Screen.Home.route
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(viewModel = homeViewModel,
                        formViewModel = formViewModel,
                        onUserForm = { navController.navigate(Screen.FormUsers.route) },
                        onNote = { navController.navigate(Screen.NoteSc.route) },
                        onScan = { qrViewModel.startScanning() },
                        onMember = { navController.navigate(Screen.Members.route) })
                }
                composable(Screen.Program.route) {
                    ProgramScreen(
                        windowSize = windowSize,
                        onBackPressed = onBackPressed,
                        viewModel = programViewModel
                    )
                }
                composable(Screen.Absent.route) {
                    AbsentScreen()
                }
                composable(Screen.Setting.route) {
                    SettingScreen(
                        navController = navController,
                        windowSize = windowSize,
                        onBackPressed = onBackPressed,
                        onChangeLanguage = { bottomNavController.navigate(Screen.ChangeLanguage.route) },
                        onNotification = { bottomNavController.navigate(Screen.Notification.route) },
                        onReport = { bottomNavController.navigate(Screen.Report.route) },
                        onTermApps = { bottomNavController.navigate(Screen.Terms.route) },
                        onPrivacy = { bottomNavController.navigate(Screen.Privacy.route) },
                        onAboutApp = { bottomNavController.navigate(Screen.About.route) },
                    )
                }
                composable(Screen.ChangeLanguage.route) {
                    // Content untuk Ganti Bahasa screen
                    ChangeLanguageScreen(onNavigate = {
                        bottomNavController.popBackStack()
                        bottomNavController.navigate(Screen.Setting.route)
                    })
                }
                composable(Screen.Notification.route) {
                    // Content untuk Ganti Bahasa screen
                    NotificationScreen(onNavigate = {
                        bottomNavController.popBackStack()
                        bottomNavController.navigate(Screen.Setting.route)
                    })
                }
                composable(Screen.Report.route) {
                    // Content untuk Report screen
                    ReportScreen(onNavigate = {
                        bottomNavController.popBackStack()
                        bottomNavController.navigate(Screen.Setting.route)
                    })
                }
                composable(Screen.Terms.route) {
                    // Content untuk Terms screen
                    TermsScreen(onNavigate = {
                        bottomNavController.popBackStack()
                        bottomNavController.navigate(Screen.Setting.route)
                    })
                }
                composable(Screen.Privacy.route) {
                    // Content untuk Privacy screen
                    PrivacyScreen(onNavigate = {
                        bottomNavController.popBackStack()
                        bottomNavController.navigate(Screen.Setting.route)
                    })
                }
                composable(Screen.About.route) {
                    // Content untuk About screen
                    AboutScreen(onNavigate = {
                        bottomNavController.popBackStack()
                        bottomNavController.navigate(Screen.Setting.route)
                    })
                }
            }
        }
    })
}

@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController,
) {
    val items = listOf(
        Screen.Home, Screen.Program, Screen.Absent, Screen.Setting
    )

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Row(modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(selected = selected.intValue == index,
                    onClick = {
                        selected.intValue = index
                        bottomNavController.popBackStack()
                        bottomNavController.navigate(route = bottomItem.route)
                    },
                    icon = {
                        Icon(
                            imageVector = if (index == selected.intValue) {
                                bottomItem.selectedIcon ?: Icons.Default.Check
                            } else bottomItem.unselectedIcon ?: Icons.Default.Check,
                            contentDescription = bottomItem.title.orEmpty()
                        )
                    },
                    alwaysShowLabel = false,
                    label = {
                        Text(
                            text = bottomItem.title ?: "",
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(MaterialTheme.colorScheme.primaryContainer)
                )
            }
        }
    }
}