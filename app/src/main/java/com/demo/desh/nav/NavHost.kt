package com.demo.desh.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.demo.desh.ui.screens.ChatListScreen
import com.demo.desh.ui.screens.ChatRoomScreen
import com.demo.desh.ui.screens.HomeScreen
import com.demo.desh.ui.screens.ProfileScreen
import com.demo.desh.ui.screens.RealtyAddScreen
import com.demo.desh.ui.screens.RealtyDetailScreen
import com.demo.desh.viewModel.ChatViewModel
import com.demo.desh.viewModel.RoomViewModel
import com.demo.desh.viewModel.UserViewModel

sealed class Screen(val route: String) {
    object Profile : Screen("profile")
    object Home : Screen("home")
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
    LaunchedEffect(Unit) {
        roomViewModel.findLocalUser()
    }

    val user by roomViewModel.user.observeAsState()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        val realtyId = "realtyId"
        val userId = "userId"
        val chatRoomId = "chatRoomId"

        val goToProfileScreen = { uid: Long -> navController.navigate(Screen.Profile.route + "/$uid") }
        val goToChatListScreen = { uid: Long -> navController.navigate(Screen.ChatList.route + "/$uid") }
        val goToRealtyDetail = { rid: Long -> navController.navigate(Screen.RealtyDetail.route + "/$rid") }
        val goToChatroom = { cid: Long -> navController.navigate(Screen.ChatRoom.route + "/$cid")}
        val goToHomeScreen = { navController.navigate(Screen.Home.route) }

        composable(route = Screen.Home.route) {
            HomeScreen(userViewModel, roomViewModel, goToRealtyDetail, goToProfileScreen, goToChatListScreen)
        }

        composable(route = Screen.RealtyDetail.route + "/{$realtyId}") { backStackEntry ->
            val rid = backStackEntry.arguments?.getString(realtyId)

            if (rid != null && user != null) {
                RealtyDetailScreen(user!!.userId!!, rid.toLong(), userViewModel, goToProfileScreen, goToChatListScreen)
            }
        }

        composable(route = Screen.Profile.route) {
            user?.userId?.let {
                ProfileScreen(it, userViewModel, chatViewModel, roomViewModel, goToChatListScreen, goToRealtyDetail)
            }
        }

        composable(route = Screen.Profile.route + "/{$userId}") { backStackEntry ->
            val targetId = backStackEntry.arguments?.getString(userId)?.toLong()

            targetId?.let {
                ProfileScreen(
                    it,
                    userViewModel,
                    chatViewModel,
                    roomViewModel,
                    goToChatListScreen,
                    goToRealtyDetail
                )
            }
        }

        composable(route = "${Screen.ChatList.route}/{${userId}}") { backStackEntry ->
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
            RealtyAddScreen(userViewModel, goToHomeScreen, goToProfileScreen)
        }
    }
}