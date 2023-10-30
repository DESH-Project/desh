package com.demo.desh

import com.demo.desh.ui.screens.RealtyDetailScreen
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.desh.ui.screens.LoginScreen
import com.demo.desh.ui.screens.MapScreen
import com.demo.desh.ui.screens.ProfileScreen
import com.demo.desh.ui.theme.DeshprojectfeTheme
import com.demo.desh.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userViewModel: UserViewModel by viewModels()

        setContent {
            DeshprojectfeTheme { Root(userViewModel) }
        }
    }
}

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Profile : Screen("profile")
    object Map : Screen("map")
    object RealtyDetail : Screen("realtyDetail")
    object RealtyAdd : Screen("realtyAdd")
}

@Composable
fun Root(userViewModel: UserViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        val realtyId = "realtyId"

        composable(route = Screen.Login.route) {
            val goToMapScreen = { navController.navigate(Screen.Map.route) {
                popUpTo(navController.graph.id) {
                    inclusive = false
                }
            } }

            LoginScreen(userViewModel, goToMapScreen)
        }

        composable(route = Screen.Map.route) {
            val goToRealtyDetail = { realtyId: Long -> navController.navigate(Screen.RealtyDetail.route + "/$realtyId") }
            MapScreen(userViewModel, goToRealtyDetail)
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen()
        }

        composable(route = "${Screen.RealtyDetail.route}/{${realtyId}}") { backStackEntry ->
            backStackEntry.arguments?.getString(realtyId)?.let { RealtyDetailScreen(it.toLong(), userViewModel) }
        }
    }
}
