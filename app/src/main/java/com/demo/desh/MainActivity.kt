package com.demo.desh

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.demo.desh.model.User
import com.demo.desh.screens.Home
import com.demo.desh.screens.NavBarItems
import com.demo.desh.screens.NavContacts
import com.demo.desh.screens.NavFavorites
import com.demo.desh.screens.NavHome
import com.demo.desh.screens.Profile
import com.demo.desh.screens.Welcome
import com.demo.desh.screens.WelcomeNavRoutes
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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Bottom Nav") }) },
        content = { NavigationHost(navController = navController, user = user) },
        bottomBar = { BottomNavigationBar(navController = navController) }
    )
}

@Composable
fun NavigationHost(navController: NavHostController, user: User) {
    NavHost(navController = navController, startDestination = WelcomeNavRoutes.Home.route) {
        composable(route = WelcomeNavRoutes.Home.route) {
            NavHome(user)
        }

        composable(route = WelcomeNavRoutes.Contacts.route) {
            NavContacts()
        }

        composable(route = WelcomeNavRoutes.Favorites.route) {
            NavFavorites()
        }
    }
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
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(imageVector = navItem.image, contentDescription = navItem.title) },
                label = { Text(text = navItem.title) }
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun MainActivityScreenPreview() {
    DeshprojectfeTheme {
    }
}