package com.demo.desh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.desh.model.User
import com.demo.desh.ui.screens.MapScreen
import com.demo.desh.ui.screens.ProfileScreen
import com.demo.desh.ui.screens.RealtyDetailScreen
import com.demo.desh.ui.screens.RemainServiceListScreen
import com.demo.desh.ui.screens.SettingsScreen
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
                Surface {
                    App(
                        viewModel = viewModel,
                        user = user
                    )
                }
            }
        }
    }
}

sealed class MainNavigation(
    val route: String,
    val title: String,
) {
    object Profile : MainNavigation("profile", "Profile")
    object Settings : MainNavigation("settings", "Settings")
    object Map : MainNavigation("map", "Map")
    object RealtyDetail : MainNavigation("realtyDetail", "RealtyDetail")
    object RemainServiceList : MainNavigation("remainServiceList", "RemainServiceList")
}

@Composable
fun App(
    viewModel: MainViewModel,
    user: User
) {
    val navController = rememberNavController()

    Scaffold() { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = MainNavigation.Map.route) {
                val REALTY_ID = "realtyId"
                val INDEX = "index"

                composable(route = MainNavigation.Profile.route) {
                    ProfileScreen()
                }

                composable(route = MainNavigation.Settings.route) {
                    SettingsScreen()
                }

                composable(route = MainNavigation.Map.route) {
                    val goToRealtyDetail = { realtyId: Long -> navController.navigate(MainNavigation.RealtyDetail.route + "/$realtyId") }
                    val goToRemainServiceListScreen = { index: Int -> navController.navigate(MainNavigation.RemainServiceList.route + "/$index") }
                    MapScreen(user, viewModel, goToRealtyDetail, goToRemainServiceListScreen)
                }

                composable(route = "${MainNavigation.RealtyDetail.route}/{${REALTY_ID}}") { backStackEntry ->
                    backStackEntry.arguments?.getString(REALTY_ID)?.let { RealtyDetailScreen(
                        realtyId = it.toLong(),
                        user = user,
                        viewModel = viewModel
                    ) }
                }

                composable(route = "${MainNavigation.RemainServiceList.route}/{${INDEX}}") { backStackEntry ->
                    backStackEntry.arguments?.getString(INDEX)?.let { RemainServiceListScreen(
                        index = it.toInt(),
                        viewModel = viewModel
                    ) }
                }
            }
        }
    }
}
