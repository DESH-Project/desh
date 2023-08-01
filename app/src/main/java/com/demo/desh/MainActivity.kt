package com.demo.desh

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.demo.desh.model.User
import com.demo.desh.screens.Home
import com.demo.desh.screens.Profile
import com.demo.desh.screens.Welcome
import com.demo.desh.ui.theme.DeshprojectfeTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getSerializableExtra("user") as User

        setContent {
            DeshprojectfeTheme {
                Surface {
                    MainActivityScreen(user)
                }
            }
        }
    }
}

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object Welcome : NavRoutes("welcome")
    object Profile : NavRoutes("profile")
}

@Composable
fun MainActivityScreen(user: User) {
    /*
    Text(text = "MainScreen")

    Column {
        Text(text = user.id!!.toString())
        Text(text = user.email)
        Text(text = user.nickname)

        Image(
            painter = rememberAsyncImagePainter(model = user.profileImageUrl),
            contentDescription = ""
        )
    }
     */

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ) {
        composable(route = NavRoutes.Home.route) {
            Home(navController = navController)
        }

        composable(route = NavRoutes.Welcome.route + "/{userName}") { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName")
            Welcome(navController = navController, userName = userName)
        }

        composable(route = NavRoutes.Profile.route) {
            Profile()
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun MainActivityScreenPreview() {
    DeshprojectfeTheme {
    }
}