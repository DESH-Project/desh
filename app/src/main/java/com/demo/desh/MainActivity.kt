package com.demo.desh

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.desh.api.RetrofitClient
import com.demo.desh.model.Recommend
import com.demo.desh.model.RecommendInfo
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapView
import java.net.URLEncoder

private val mapFlow = MutableStateFlow(mutableStateMapOf<String, RecommendInfo?>())

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getSerializableExtra("user") as User
        val map = mapFlow.value

        CoroutineScope(Dispatchers.IO).launch {
            val mapView = MapCreator.getMapView(map)
            val serviceList = getServiceList()

            serviceList.forEach { serviceName ->
                if (map[serviceName] == null) {
                    val recommendInfo = getRecommendInfo(map, serviceName)
                    map[serviceName] = recommendInfo
                    Log.e("MainActivity", "$serviceName = ${recommendInfo.toString()}")
                }
            }

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
            MainScreen(navController)
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

private suspend fun getServiceList() : List<String> {
    val userService = RetrofitClient.userService
    val result = userService.getServiceList()
    val serviceList = mutableListOf<String>()

    if (result.isSuccessful) {
        val body = result.body()!!
        val list = body.list
        list.forEach { serviceList.add(it) }
    }

    return serviceList
}

private suspend fun getRecommendInfo(map: MutableMap<String, RecommendInfo?>, serviceName: String) : RecommendInfo {
    if (map[serviceName] != null) {
        return map[serviceName]!!
    }

    val userService = RetrofitClient.userService
    val res = userService.getRecommendationInfo(URLEncoder.encode(serviceName, "UTF-8"))
    val recommendInfoList = mutableListOf<Recommend>()

    if (res.isSuccessful) {
        val body = res.body()!!
        val list = body.list

        list.forEach {
            val recommend = Recommend(
                lat = it.lat,
                lng = it.lng,
                service = it.service,
                district = it.district,
                predict = it.predict
            )

            recommendInfoList.add(recommend)
        }
    }

    val recommendInfo = RecommendInfo(recommendInfoList.size, recommendInfoList)
    map[serviceName] = recommendInfo

    return recommendInfo
}