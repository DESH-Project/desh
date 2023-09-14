package com.demo.desh

import RealtyDetailScreen
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.desh.model.User
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

        val user = intent.getSerializableExtra("user") as User
        Log.d("MainActivity", "user = $user")

        viewModel = ViewModelProvider(this, MainViewModelFactory())[MainViewModel::class.java]

        setContent {
            DeshprojectfeTheme {
                Root(
                    viewModel = viewModel,
                    user = user
                )
            }
        }
    }
}

sealed class MainNavigation(val route: String) {
    object Start : MainNavigation("start")
    object Profile : MainNavigation("profile")
    object Settings : MainNavigation("settings")
    object Map : MainNavigation("map")
    object RealtyDetail : MainNavigation("realtyDetail")
    object RemainServiceList : MainNavigation("remainServiceList")
}

@Composable
fun Root(
    viewModel: MainViewModel,
    user: User
) {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.padding(0.dp),
        navController = navController,
        startDestination = MainNavigation.Start.route
    ) {
        val realtyId = "realtyId"
        val serviceListIndex = "index"

        composable(route = MainNavigation.Start.route) {
            val goToMapScreen = { navController.navigate(MainNavigation.Map.route) }
            StartScreen(user, viewModel, goToMapScreen)
        }

        composable(route = MainNavigation.Profile.route) {
            ProfileScreen()
        }

        composable(route = MainNavigation.Map.route) {
            val goToRealtyDetail = { realtyId: Long -> navController.navigate(MainNavigation.RealtyDetail.route + "/$realtyId") }
            val goToRemainServiceListScreen = { index: Int -> navController.navigate(MainNavigation.RemainServiceList.route + "/$index") }
            MapScreen(user, viewModel, goToRealtyDetail, goToRemainServiceListScreen)
        }

        composable(route = "${MainNavigation.RealtyDetail.route}/{${realtyId}}") { backStackEntry ->
            backStackEntry.arguments?.getString(realtyId)?.let { RealtyDetailScreen(
                realtyId = it.toLong(),
                user = user,
                viewModel = viewModel
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
