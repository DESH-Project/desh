package com.demo.desh.nav

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomBarItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

object NavBarItems {
    val BarItems = listOf(
        BottomBarItem(
            title = "Home",
            icon = Icons.Filled.Home,
            route = Screen.Home.route
        ),

        BottomBarItem(
            title = "Add",
            icon = Icons.Filled.Add,
            route = Screen.RealtyAdd.route
        ),

        BottomBarItem(
            title = "Profile",
            icon = Icons.Filled.AccountCircle,
            route = Screen.Profile.route
        )
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        NavBarItems.BarItems.forEach { navItem ->
            BottomNavigationItem(
                selected = currentRoute == navItem.route,

                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

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