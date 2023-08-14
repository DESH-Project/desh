package com.demo.desh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.desh.model.User
import com.demo.desh.nav.BottomNavigationBar
import com.demo.desh.nav.MainBottomBarNav
import com.demo.desh.ui.screens.MainScreen
import com.demo.desh.ui.screens.ProfileScreen
import com.demo.desh.ui.screens.SettingsScreen
import com.demo.desh.ui.theme.DeshprojectfeTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getSerializableExtra("user") as User

        setContent {
            DeshprojectfeTheme {
                Surface {
                    App(user)
                }
            }
        }
    }
}

@Composable
fun App(user: User) {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Bottom Nav") }) },
        content = { it
            MainNavigationHost(navController = navController, user = user)
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    )
}

@Composable
fun MainNavigationHost(navController: NavHostController, user: User) {
    NavHost(navController = navController, startDestination = MainBottomBarNav.Home.route) {
        composable(route = MainBottomBarNav.Home.route) {
            MainScreen(user)
        }

        composable(route = MainBottomBarNav.Profile.route) {
            ProfileScreen()
        }

        composable(route = MainBottomBarNav.Settings.route) {
            SettingsScreen()
        }
    }
}