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
import com.demo.desh.ui.screens.ChatListScreen
import com.demo.desh.ui.screens.ChatRoomScreen
import com.demo.desh.ui.screens.LoginScreen
import com.demo.desh.ui.screens.MapScreen
import com.demo.desh.ui.screens.ProfileScreen
import com.demo.desh.ui.screens.RealtyAddScreen
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
    object ChatRoom : Screen("chatRoom")
    object ChatList : Screen("chatList")
}

@Composable
fun Root(userViewModel: UserViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        val realtyId = "realtyId"
        val userId = "userId"
        val chatRoomId = "chatRoomId"

        composable(route = Screen.Login.route) {
            val goToMapScreen = { navController.navigate(Screen.Map.route) {
                popUpTo(navController.graph.id) {
                    inclusive = false
                }
            } }

            LoginScreen(userViewModel, goToMapScreen)
        }

        composable(route = Screen.Map.route) {
            val goToRealtyDetailScreen = { realtyId: Long -> navController.navigate(Screen.RealtyDetail.route + "/$realtyId") }
            val goToProfileScreen = { userId: Long -> navController.navigate(Screen.Profile.route + "/$userId") }
            MapScreen(userViewModel, goToRealtyDetailScreen, goToProfileScreen)
        }

        composable(route = "${Screen.RealtyDetail.route}/{${realtyId}}") { backStackEntry ->
            val goToProfileScreen = { navController.navigate(Screen.Profile.route) }
            val goToChatListScreen = { navController.navigate(Screen.ChatList.route) }

            backStackEntry.arguments?.getString(realtyId)?.let {
                RealtyDetailScreen(it.toLong(), userViewModel, goToProfileScreen, goToChatListScreen)
            }
        }
        
        // [ uid != null ] -> 다른 유저의 프로필을 보는 경우
        composable(route = "${Screen.Profile.route}/{${userId}}") { backStackEntry ->
            backStackEntry.arguments?.getString(userId)?.let {
                ProfileScreen(it.toLong(), userViewModel)
            }
        }

        // [ uid == null ] -> 내 자신의 프로필을 보는 경우
        composable(route = Screen.Profile.route) {
            ProfileScreen(userViewModel = userViewModel)
        }

        composable(route = Screen.ChatList.route) {
            ChatListScreen()
        }

        composable(route = "${Screen.ChatRoom.route}/{${chatRoomId}}") { backStackEntry ->
            backStackEntry.arguments?.getString(chatRoomId)?.let {
                ChatRoomScreen(it.toLong())
            }
        }

        composable(route = Screen.RealtyAdd.route) {
            RealtyAddScreen()
        }
    }
}
