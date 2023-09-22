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
import com.demo.desh.ui.screens.LoginScreen
import com.demo.desh.ui.screens.MapScreen
import com.demo.desh.ui.screens.ProfileScreen
import com.demo.desh.ui.screens.RemainServiceListScreen
import com.demo.desh.ui.screens.StartScreen
import com.demo.desh.ui.theme.DeshprojectfeTheme
import com.demo.desh.viewModel.MainViewModel
import com.demo.desh.viewModel.MainViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val room = MemberRoomDatabase.getInstance(this)
        val memberDao = room.memberDao()
        val memberRepository = MemberRepository(memberDao)

        viewModel = ViewModelProvider(this, MainViewModelFactory(memberRepository = memberRepository))[MainViewModel::class.java]

        setContent {
            DeshprojectfeTheme {
                Root(
                    viewModel = viewModel
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
    object RemainServiceList : Screen("remainServiceList")
}

@Composable
fun Root(viewModel: MainViewModel) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        viewModel.deleteAllMember()
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        val realtyId = "realtyId"
        val serviceListIndex = "index"

        composable(route = Screen.Login.route) {
            val goToStartScreen = { navController.navigate(Screen.Start.route) {
                popUpTo(navController.graph.id) {
                    inclusive = false
                }
            } }

            LoginScreen(viewModel, goToStartScreen)
        }

        composable(route = Screen.Start.route) {
            val goToMapScreen = { navController.navigate(Screen.Map.route) }
            StartScreen(viewModel, goToMapScreen)
        }

        composable(route = Screen.Map.route) {
            val goToRealtyDetail = { realtyId: Long -> navController.navigate(Screen.RealtyDetail.route + "/$realtyId") }
            val goToRemainServiceListScreen = { index: Int -> navController.navigate(Screen.RemainServiceList.route + "/$index") }

            MapScreen(viewModel, goToRealtyDetail, goToRemainServiceListScreen)
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen()
        }

        composable(route = "${Screen.RealtyDetail.route}/{${realtyId}}") { backStackEntry ->
            backStackEntry.arguments?.getString(realtyId)?.let { RealtyDetailScreen(
                viewModel = viewModel,
                realtyId = it.toLong()
            ) }
        }

        composable(route = "${Screen.RemainServiceList.route}/{${serviceListIndex}}") { backStackEntry ->
            backStackEntry.arguments?.getString(serviceListIndex)?.let { RemainServiceListScreen(
                index = it.toInt(),
                viewModel = viewModel
            ) }
        }
    }
}
