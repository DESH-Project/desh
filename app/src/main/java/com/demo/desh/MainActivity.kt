package com.demo.desh

import com.demo.desh.ui.screens.RealtyDetailScreen
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
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
import com.demo.desh.viewModel.ChatViewModel
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

        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        val chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]

        setContent {
            DeshprojectfeTheme {
                Root(userViewModel, chatViewModel)
            }
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
fun Root(
    userViewModel: UserViewModel,
    chatViewModel: ChatViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        val realtyId = "realtyId"
        val userId = "userId"
        val chatRoomId = "chatRoomId"

        composable(route = Screen.Login.route) {
            val goToMapScreen = { userId: Long -> navController.navigate(Screen.Map.route + "/$userId") {
                popUpTo(navController.graph.id) {
                    inclusive = false
                }
            } }

            LoginScreen(userViewModel, goToMapScreen)
        }

        composable(route = Screen.Map.route + "/{$userId}") { backStackEntry ->
            backStackEntry.arguments?.getString(userId)?.let {
                val goToRealtyDetailScreen = { realtyId: Long -> navController.navigate(Screen.RealtyDetail.route + "/$realtyId") }
                val goToProfileScreen = { userId: Long -> navController.navigate(Screen.Profile.route + "/$userId") }
                val goToChatListScreen = { userId: Long -> navController.navigate(Screen.ChatList.route + "/$userId") }

                MapScreen(it.toLong(), userViewModel, goToRealtyDetailScreen, goToProfileScreen, goToChatListScreen)
            }
        }

        composable(route = Screen.RealtyDetail.route + "/{$userId}" + "/{$realtyId}") { backStackEntry ->
            val uid = backStackEntry.arguments?.getString(userId)
            val rid = backStackEntry.arguments?.getString(realtyId)

            if (uid != null && rid != null) {
                val goToProfileScreen = { navController.navigate(Screen.Profile.route) }
                val goToChatListScreen = { userId: Long -> navController.navigate(Screen.ChatList.route + "/$userId") }
                RealtyDetailScreen(uid.toLong(), rid.toLong(), userViewModel, goToProfileScreen, goToChatListScreen)
            }
        }
        
        composable(route = "${Screen.Profile.route}/{${userId}}") { backStackEntry ->
            backStackEntry.arguments?.getString(userId)?.let {
                ProfileScreen(it.toLong(), userViewModel)
            }
        }

        composable(route = "${Screen.ChatList.route}/{${userId}}") { backStackEntry ->
            val goToChatroom = { chatRoomId: Long -> navController.navigate(Screen.ChatRoom.route + "/$chatRoomId")}
            backStackEntry.arguments?.getString(userId)?.let {
                ChatListScreen(it.toLong(), goToChatroom, userViewModel, chatViewModel)
            }
        }

        composable(route = "${Screen.ChatRoom.route}/{${chatRoomId}}") { backStackEntry ->
            backStackEntry.arguments?.getString(chatRoomId)?.let {
                ChatRoomScreen(it.toLong(), userViewModel, chatViewModel)
            }
        }

        composable(route = Screen.RealtyAdd.route) {
            RealtyAddScreen()
        }
    }
}
