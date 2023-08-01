package com.demo.desh.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.demo.desh.NavRoutes

sealed class WelcomeNavRoutes(val route: String) {
    object Home : WelcomeNavRoutes("home")
    object Contacts : WelcomeNavRoutes("contacts")
    object Favorites : WelcomeNavRoutes("favorites")
}

data class BarItem(
    val title: String,
    val image: ImageVector,
    val route: String
)

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Home",
            image = Icons.Filled.Home,
            route = "home"
        ),

        BarItem(
            title = "Contacts",
            image = Icons.Filled.Face,
            route = "contacts"
        ),

        BarItem(
            title = "Favorites",
            image = Icons.Filled.Favorite,
            route = "favorites"
        ),
    )
}

@Composable
fun Welcome(navController: NavController, userName: String?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Welcome, $userName", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.size(30.dp))

            Button(onClick = { navController.navigate(NavRoutes.Profile.route) { popUpTo(NavRoutes.Home.route) } }) {
                Text(text = "Set up your Profile")
            }
        }
    }
}

@Composable
fun NavHome() {
    Box(modifier = Modifier.fillMaxSize()) {
        Icon(
            imageVector = Icons.Filled.Home,
            contentDescription = "home",
            tint = Color.Blue,
            modifier = Modifier.size(150.dp).align(Alignment.Center)
        )
    }
}

@Composable
fun NavContacts() {
    Box(modifier = Modifier.fillMaxSize()) {
        Icon(
            imageVector = Icons.Filled.Face,
            contentDescription = "contacts",
            tint = Color.Blue,
            modifier = Modifier.size(150.dp).align(Alignment.Center)
        )
    }
}

@Composable
fun NavFavorites() {
    Box(modifier = Modifier.fillMaxSize()) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "favorites",
            tint = Color.Blue,
            modifier = Modifier.size(150.dp).align(Alignment.Center)
        )
    }
}