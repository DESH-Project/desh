package com.demo.desh

import RealtyDetailScreen
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

sealed class MainNavigation(val route: String) {
    object Start : MainNavigation("start")
    object Login : MainNavigation("login")
    object Profile : MainNavigation("profile")
    object Settings : MainNavigation("settings")
    object Map : MainNavigation("map")
    object RealtyDetail : MainNavigation("realtyDetail")
    object RemainServiceList : MainNavigation("remainServiceList")
}

@Composable
fun Root(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val member by viewModel.member.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.deleteAllMember()
    }

    NavHost(
        modifier = Modifier.padding(0.dp),
        navController = navController,
        startDestination = if (member == null) MainNavigation.Login.route else MainNavigation.Start.route
    ) {

        val realtyId = "realtyId"
        val serviceListIndex = "index"

        composable(route = MainNavigation.Login.route) {
            val goToStartScreen = { navController.navigate(MainNavigation.Start.route) }
            LoginScreen(viewModel, goToStartScreen)
        }

        composable(route = MainNavigation.Start.route) {
            val goToMapScreen = { navController.navigate(MainNavigation.Map.route) }
            StartScreen(viewModel, goToMapScreen)
        }

        composable(route = MainNavigation.Profile.route) {
            ProfileScreen()
        }

        composable(route = MainNavigation.Map.route) {
            val goToRealtyDetail = { realtyId: Long -> navController.navigate(MainNavigation.RealtyDetail.route + "/$realtyId") }
            val goToRemainServiceListScreen = { index: Int -> navController.navigate(MainNavigation.RemainServiceList.route + "/$index") }

            MapScreen(viewModel, goToRealtyDetail, goToRemainServiceListScreen)
        }

        composable(route = "${MainNavigation.RealtyDetail.route}/{${realtyId}}") { backStackEntry ->
            backStackEntry.arguments?.getString(realtyId)?.let { RealtyDetailScreen(
                viewModel = viewModel,
                realtyId = it.toLong()
            ) }
        }

        composable(route = "${MainNavigation.RemainServiceList.route}/{${serviceListIndex}}") { backStackEntry ->
            backStackEntry.arguments?.getString(serviceListIndex)?.let { RemainServiceListScreen(
                index = it.toInt(),
                viewModel = viewModel
            ) }
        }
    }
}
