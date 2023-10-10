package com.demo.desh

import RealtyDetailScreen
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.desh.access.repository.MemberRepository
import com.demo.desh.access.room.MemberRoomDatabase
import com.demo.desh.ui.screens.loginScreen.LoginScreen
import com.demo.desh.ui.screens.mapScreen.MapScreen
import com.demo.desh.ui.screens.profileScreen.ProfileScreen
import com.demo.desh.ui.screens.startScreen.StartScreen
import com.demo.desh.ui.theme.DeshprojectfeTheme
import com.demo.desh.viewModel.MemberRoomViewModel
import com.demo.desh.viewModel.UserViewModel
import com.demo.desh.viewModel.factory.MainViewModelFactory
import com.demo.desh.viewModel.factory.MemberRoomViewModelFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val room = MemberRoomDatabase.getInstance(this)
        val memberDao = room.memberDao()
        val memberRepository = MemberRepository(memberDao)

        val userViewModel = ViewModelProvider(this, MainViewModelFactory())[UserViewModel::class.java]
        val memberRoomViewModel = ViewModelProvider(this, MemberRoomViewModelFactory(memberRepository))[MemberRoomViewModel::class.java]

        setContent {
            DeshprojectfeTheme {
                Root(
                    userViewModel = userViewModel,
                    memberRoomViewModel = memberRoomViewModel
                )
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Start : Screen("start")
    object Login : Screen("login")
    object Profile : Screen("profile")
    object Map : Screen("map")
    object RealtyDetail : Screen("realtyDetail")
}

@Composable
fun Root(
    userViewModel: UserViewModel,
    memberRoomViewModel: MemberRoomViewModel
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        memberRoomViewModel.deleteAllMember()
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        val realtyId = "realtyId"

        composable(route = Screen.Login.route) {
            val goToStartScreen = { navController.navigate(Screen.Start.route) {
                popUpTo(navController.graph.id) {
                    inclusive = false
                }
            } }

            LoginScreen(userViewModel, memberRoomViewModel, goToStartScreen)
        }

        composable(route = Screen.Start.route) {
            val goToMapScreen = { navController.navigate(Screen.Map.route) }
            StartScreen(userViewModel, goToMapScreen)
        }

        composable(route = Screen.Map.route) {
            val goToRealtyDetail = { realtyId: Long -> navController.navigate(Screen.RealtyDetail.route + "/$realtyId") }
            MapScreen(userViewModel, goToRealtyDetail)
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen()
        }

        composable(route = "${Screen.RealtyDetail.route}/{${realtyId}}") { backStackEntry ->
            backStackEntry.arguments?.getString(realtyId)?.let { RealtyDetailScreen(
                viewModel = userViewModel,
                realtyId = it.toLong()
            ) }
        }
    }
}
