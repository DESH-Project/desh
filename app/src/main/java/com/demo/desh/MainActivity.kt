package com.demo.desh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.desh.model.User
import com.demo.desh.util.BottomNavigationBar
import com.demo.desh.util.MainNavigation
import com.demo.desh.ui.screens.MainScreen
import com.demo.desh.ui.screens.MapScreen
import com.demo.desh.ui.screens.ProfileScreen
import com.demo.desh.ui.screens.SettingsScreen
import com.demo.desh.ui.theme.DeshprojectfeTheme
import com.demo.desh.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val user = intent.getSerializableExtra("user") as User

            DeshprojectfeTheme {
                Surface {
                    App(
                        user = user,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun App(
    user: User,
    viewModel: MainViewModel
) {
    val navController = rememberNavController()

    Scaffold(
        content = { it
            MainNavigationHost(
                navController = navController,
                user = user,
                viewModel = viewModel
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    )
}

@Composable
fun MainNavigationHost(
    navController: NavHostController,
    user: User,
    viewModel: MainViewModel,
) {
    NavHost(navController = navController, startDestination = MainNavigation.Home.route) {
        composable(route = MainNavigation.Home.route) {
            MainScreen(navController)
        }

        composable(route = MainNavigation.Profile.route) {
            ProfileScreen()
        }

        composable(route = MainNavigation.Settings.route) {
            SettingsScreen()
        }

        composable(route = MainNavigation.Map.route) {
            val onBackButtonClick = { navController.navigate(MainNavigation.Home.route) }
            MapScreen(viewModel, onBackButtonClick)
        }
    }
}

