package com.hardus.trueagencyapp.main_content.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.auth.viewmodel.AuthViewModel
import com.hardus.trueagencyapp.component.ButtonComponent
import com.hardus.trueagencyapp.nested_navigation.AUTH_GRAPH_ROUTE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val navigationItemsContentList = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        BottomNavigationItem(
            title = "Program",
            selectedIcon = Icons.Filled.GridView,
            unselectedIcon = Icons.Outlined.GridView,
        ),
        BottomNavigationItem(
            title = "Absent",
            selectedIcon = Icons.Filled.EditNote,
            unselectedIcon = Icons.Outlined.EditNote,
        ),
        BottomNavigationItem(
            title = "Setting",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
        ),
    )
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ), title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.vector),
                        contentDescription = stringResource(R.string.logo_app)
                    )
                }
            }, scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        )
    }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CheckLogin(navController = navController)
        }
    }, bottomBar = {
        NavigationBar {
            navigationItemsContentList.forEachIndexed { index, bottomNavigationItem ->
                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    onClick = { selectedItemIndex = index },
                    icon = {
                        Icon(
                            imageVector = if (index == selectedItemIndex) {
                                bottomNavigationItem.selectedIcon
                            } else bottomNavigationItem.unselectedIcon,
                            contentDescription = null
                        )
                    },
                    alwaysShowLabel = false,
                    label = {
                        Text(
                            text = bottomNavigationItem.title,
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(MaterialTheme.colorScheme.primaryContainer)
                )
            }
        }
    }
    )
}

@Composable
fun CheckLogin(
    authViewModel: AuthViewModel? = hiltViewModel(), navController: NavController
) {
    Column(
        modifier = Modifier.padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home Screen", style = MaterialTheme.typography.displaySmall)
        Text(
            text = authViewModel?.currentUser?.displayName ?: "",
            style = MaterialTheme.typography.displaySmall
        )
        Text(
            text = authViewModel?.currentUser?.email ?: "",
            style = MaterialTheme.typography.displaySmall
        )
        Text(
            text = authViewModel?.currentUser?.phoneNumber ?: "",
            style = MaterialTheme.typography.displaySmall
        )
        ButtonComponent(
            value = stringResource(R.string.log_out), onNavigate = {
                authViewModel?.logout()
                navController.navigate(route = AUTH_GRAPH_ROUTE) {
                    popUpTo(AUTH_GRAPH_ROUTE) { inclusive = true }
                }
            }, isEnabled = true
        )
    }
}

private data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)


@Preview(showBackground = true, showSystemUi = true, name = "Hardus")
@Composable
//CheckNewPasswordScreenPhone
fun CheckHomeScreenPhone() {
    HomeScreen(rememberNavController())
}

@Preview(showBackground = true, showSystemUi = true, name = "Hardus", device = Devices.TABLET)
@Composable
//CheckNewPasswordScreenPhone
fun CheckHomeScreenPhoneTablet() {
    HomeScreen(rememberNavController())
}

