package com.hardus.trueagencyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.hardus.trueagencyapp.nested_navigation.NavigationAllScreen
import com.hardus.trueagencyapp.onboarding.viewmodel.SplashViewModel
import com.hardus.trueagencyapp.ui.theme.TrueAgencyAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalPagerApi
@AndroidEntryPoint


class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    @Inject
    lateinit var splashViewModel: SplashViewModel

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            !splashViewModel.isLoading.value
        }
        setContent {
            TrueAgencyAppTheme {
                val widthSizeClass = calculateWindowSizeClass(this)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val screen by splashViewModel.startDestination
                    navController = rememberNavController()
                    NavigationAllScreen(
                        navController = navController,
                        startDestination = screen,
                        windowSize = widthSizeClass.widthSizeClass,
                        onBackPressed = {
                            finish()
                        }
                    )
                }
            }
        }
    }
}



