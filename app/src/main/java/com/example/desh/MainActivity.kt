package com.example.desh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.desh.screens.Home
import com.example.desh.screens.Profile
import com.example.desh.screens.Welcome
import com.example.desh.ui.theme.DeshTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeshTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    // nav controller instance
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoutes.Home.route) {
        composable(NavRoutes.Home.route) {
            Home(navController = navController)
        }

        composable(NavRoutes.Welcome.route + "/{userName}") { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName")
            Welcome(navController = navController, userName)
        }

        composable(NavRoutes.Profile.route) {
            Profile()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DeshTheme {
        MainScreen()
    }
}