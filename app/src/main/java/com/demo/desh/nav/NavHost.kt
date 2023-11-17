package com.demo.desh.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.demo.desh.ui.screens.ChatListScreen
import com.demo.desh.ui.screens.ChatRoomScreen
import com.demo.desh.ui.screens.MapScreen
import com.demo.desh.ui.screens.ProfileScreen
import com.demo.desh.ui.screens.RealtyAddScreen
import com.demo.desh.ui.screens.RealtyDetailScreen
import com.demo.desh.viewModel.ChatViewModel
import com.demo.desh.viewModel.RoomViewModel
import com.demo.desh.viewModel.UserViewModel

sealed class Screen(val route: String) {
    object Profile : Screen("profile")
    object Map : Screen("map")
    object RealtyDetail : Screen("realtyDetail")
    object RealtyAdd : Screen("realtyAdd")
    object ChatRoom : Screen("chatRoom")
    object ChatList : Screen("chatList")
}

@Composable
fun NavigationHost(
    navController: NavHostController,
    userViewModel: UserViewModel,
    chatViewModel: ChatViewModel,
    roomViewModel: RoomViewModel
) {
    NavHost(navController = navController, startDestination = Screen.Map.route) {
        val realtyId = "realtyId"
        val userId = "userId"
        val chatRoomId = "chatRoomId"

        composable(route = Screen.Map.route) { backStackEntry ->
            val goToRealtyDetailScreen = { userId: Long, realtyId: Long -> navController.navigate(Screen.RealtyDetail.route + "/$userId/$realtyId") }
            val goToProfileScreen = { userId: Long -> navController.navigate(Screen.Profile.route + "/$userId") }
            val goToChatListScreen = { userId: Long -> navController.navigate(Screen.ChatList.route + "/$userId") }

            MapScreen(userViewModel, roomViewModel, goToRealtyDetailScreen, goToProfileScreen, goToChatListScreen)
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
                val goToProfileScreen = { navController.navigate(Screen.Profile.route) }
                val goToChatListScreen = { userId: Long -> navController.navigate(Screen.ChatList.route + "/$userId") }
                ProfileScreen(it.toLong(), userViewModel, chatViewModel, goToProfileScreen, goToChatListScreen)
            }
        }

        composable(route = "${Screen.ChatList.route}/{${userId}}") { backStackEntry ->
            backStackEntry.arguments?.getString(userId)?.let {
                val goToChatroom = { chatRoomId: Long -> navController.navigate(Screen.ChatRoom.route + "/$chatRoomId")}
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