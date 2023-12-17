package com.hardus.trueagencyapp.main_content

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hardus.trueagencyapp.main_content.absent.AbsentScreen
import com.hardus.trueagencyapp.main_content.home.HomeScreen
import com.hardus.trueagencyapp.main_content.program.ProgramScreen
import com.hardus.trueagencyapp.main_content.setting.SettingScreen
import com.hardus.trueagencyapp.nested_navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(
    navController: NavHostController,
    windowSize: WindowWidthSizeClass,
    onBackPressed: () -> Unit,
) {
    val bottomNavController = rememberNavController()
    val context = LocalContext.current
    Scaffold(
        bottomBar = {
            BottomNavigationBar(bottomNavController = bottomNavController)
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues),
                contentAlignment = Alignment.Center,
            ) {
                NavHost(
                    navController = bottomNavController,
                    startDestination = Screen.Home.route
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen(navController = navController)
                    }
                    composable(Screen.Program.route) {
                        ProgramScreen(
                            navController = navController,
                            windowSize = windowSize,
                            onBackPressed = onBackPressed
                        )
                    }
                    composable(Screen.Absent.route) {
                        AbsentScreen(navController = navController)
                    }
                    composable(Screen.Setting.route) {
                        SettingScreen(
                            navController = navController,
                            windowSize = windowSize,
                            onBackPressed = onBackPressed,
                            onChangeLanguage = {
                                Toast.makeText(
                                    context,
                                    "Ini Ganti Bahasa",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onNotification = {
                                Toast.makeText(
                                    context,
                                    "Ini Pengaturan Notifikasi",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onReport = {
                                Toast.makeText(
                                    context,
                                    "Ini Laporan Keluhan",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onTermApps = {
                                Toast.makeText(
                                    context,
                                    "Ini Syarat Ketentuan",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onPrivacy = {
                                Toast.makeText(
                                    context,
                                    "Ini Kebijakan Privasi",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onAboutApp = {
                                Toast.makeText(
                                    context,
                                    "Ini Tentang Aplikasi",
                                    Toast.LENGTH_SHORT
                                ).show()
                            })
                    }
                }
            }
        }
    )
}

@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController,
) {
    val items = listOf(
        Screen.Home,
        Screen.Program,
        Screen.Absent,
        Screen.Setting
    )

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Row(modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(
                    selected = selected.intValue == index,
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