package com.demo.desh

import RealtyDetailScreen
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.desh.ui.screens.loginScreen.LoginScreen
import com.demo.desh.ui.screens.mapScreen.MapScreen
import com.demo.desh.ui.screens.profileScreen.ProfileScreen
import com.demo.desh.ui.screens.startScreen.StartScreen
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
    object Start : Screen("start")
    object Login : Screen("login")
    object Profile : Screen("profile")
    object Map : Screen("map")
    object RealtyDetail : Screen("realtyDetail")
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
            val goToStartScreen = { navController.navigate(Screen.Start.route) {
                popUpTo(navController.graph.id) {
                    inclusive = false
                }
            } }

            LoginScreen(userViewModel, goToStartScreen)
        }

        composable(route = Screen.Start.route) {
            val goToMapScreen = { navController.navigate(Screen.Map.route) }
            StartScreen(userViewModel, goToMapScreen)
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
