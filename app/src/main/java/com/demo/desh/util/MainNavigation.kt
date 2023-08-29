package com.demo.desh.util

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.demo.desh.MainActivity
import com.demo.desh.ui.screens.MainScreen
import com.demo.desh.ui.screens.MapScreen
import com.demo.desh.ui.screens.ProfileScreen
import com.demo.desh.ui.screens.SettingsScreen
import com.demo.desh.viewModel.MainViewModel

sealed class MainNavigation(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : MainNavigation("home", "Home", Icons.Outlined.Home)
    object Map : MainNavigation("map", "Map", Icons.Outlined.Phone)
    object Profile : MainNavigation("profile", "Profile", Icons.Outlined.AccountCircle)
    object Settings : MainNavigation("settings", "Settings", Icons.Outlined.Settings)

    companion object {
        val items = listOf(Home, Profile, Settings)
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(
        contentColor = Color.LightGray,
        backgroundColor = Color.White
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        MainNavigation.items.forEach { navItem ->
            BottomNavigationItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },

                icon = { Icon(imageVector = navItem.icon, contentDescription = navItem.title) },
                label = { Text(text = navItem.title) }
            )
        }
    }
}

@Composable
fun MainNavigationHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    markerEventListener: MainActivity.MarkerEventListener
) {
    NavHost(navController = navController, startDestination = MainNavigation.Home.route) {
        composable(route = MainNavigation.Home.route) {
            MainScreen(viewModel, navController)
        }

        composable(route = MainNavigation.Profile.route) {
            ProfileScreen()
        }

        composable(route = MainNavigation.Settings.route) {
            SettingsScreen()
        }

        composable(route = MainNavigation.Map.route) {
            val onBackButtonClick = { navController.navigate(MainNavigation.Home.route) }
            MapScreen(viewModel, onBackButtonClick, markerEventListener)
        }
    }
}
