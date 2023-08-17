package com.demo.desh

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.desh.api.RetrofitClient
import com.demo.desh.model.ServiceList
import com.demo.desh.model.User
import com.demo.desh.util.BottomNavigationBar
import com.demo.desh.util.MainBottomBarNav
import com.demo.desh.ui.screens.MainScreen
import com.demo.desh.ui.screens.MapScreen
import com.demo.desh.ui.screens.ProfileScreen
import com.demo.desh.ui.screens.SettingsScreen
import com.demo.desh.ui.theme.DeshprojectfeTheme
import com.demo.desh.util.MapCreator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getSerializableExtra("user") as User

        CoroutineScope(Dispatchers.IO).launch {
            val mapView = MapCreator.getMapView()

            val userService = RetrofitClient.userService
            val result = userService.getServiceList()
            val serviceList = mutableListOf<String>()

            result.enqueue(object : Callback<ServiceList> {
                override fun onResponse(call: Call<ServiceList>, response: Response<ServiceList>) {
                    val body = response.body()
                    val list = body!!.list
                    list.forEach { serviceList.add(it) }
                }

                override fun onFailure(call: Call<ServiceList>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
            Log.e("MainActivity", "mapView = $mapView")

            runOnUiThread {
                setContent {
                    DeshprojectfeTheme {
                        Surface {
                            App(user, mapView, serviceList)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun App(user: User, mapView: (context: Context) -> MapView, serviceList: List<String>) {
    val navController = rememberNavController()
    val onMapButtonClick = { navController.navigate(MainBottomBarNav.Map.route) {
        restoreState = true
    } }

    Scaffold(
        content = { it
            MainNavigationHost(
                navController = navController,
                user = user,
                mapView = mapView,
                onMapButtonClick = onMapButtonClick,
                serviceList = serviceList
            )
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    )
}

@Composable
fun MainNavigationHost(
    navController: NavHostController,
    user: User,
    mapView: (context: Context) -> MapView,
    onMapButtonClick: () -> Unit,
    serviceList: List<String>
) {
    NavHost(navController = navController, startDestination = MainBottomBarNav.Home.route) {
        composable(route = MainBottomBarNav.Home.route) {
            MainScreen(user, onMapButtonClick)
        }

        composable(route = MainBottomBarNav.Profile.route) {
            ProfileScreen()
        }

        composable(route = MainBottomBarNav.Settings.route) {
            SettingsScreen()
        }

        composable(route = MainBottomBarNav.Map.route) {
            MapScreen(mapView, serviceList)
        }
    }
}