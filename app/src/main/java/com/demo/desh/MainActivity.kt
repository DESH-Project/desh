package com.demo.desh

import android.app.Activity
import android.content.Context
import android.graphics.Color
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
import com.demo.desh.model.User
import com.demo.desh.nav.BottomNavigationBar
import com.demo.desh.nav.MainBottomBarNav
import com.demo.desh.ui.screens.MainScreen
import com.demo.desh.ui.screens.ProfileScreen
import com.demo.desh.ui.screens.SettingsScreen
import com.demo.desh.ui.theme.DeshprojectfeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import net.daum.mf.map.api.CameraUpdateFactory
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPointBounds
import net.daum.mf.map.api.MapView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getSerializableExtra("user") as User

        CoroutineScope(Dispatchers.IO).launch {
            val circles = getMapCircles()
            val mapView = getMapView(circles)

            runOnUiThread {
                setContent {
                    DeshprojectfeTheme {
                        Surface {
                            App(user, mapView)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun App(user: User, mapView: (context: Context) -> MapView) {
    val navController = rememberNavController()

    Scaffold(
        content = { it
            MainNavigationHost(navController = navController, user = user, mapView = mapView)
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    )
}

@Composable
fun MainNavigationHost(navController: NavHostController, user: User, mapView: (context: Context) -> MapView) {
    NavHost(navController = navController, startDestination = MainBottomBarNav.Home.route) {
        composable(route = MainBottomBarNav.Home.route) {
            MainScreen(mapView)
        }

        composable(route = MainBottomBarNav.Profile.route) {
            ProfileScreen()
        }

        composable(route = MainBottomBarNav.Settings.route) {
            SettingsScreen()
        }
    }
}

private fun getMapView(circles: List<MapCircle>) : (context: Context) -> MapView {
    Log.e("getMapView()", "${circles.size}")

    return { context: Context ->
        val mv = MapView(context)
        val mapPointBoundsArray = mutableListOf<MapPointBounds>()

        circles.forEach { circle ->
            mv.addCircle(circle)
            mapPointBoundsArray.add(circle.bound)
        }

        val mapPointBounds = MapPointBounds(mapPointBoundsArray.toTypedArray())
        val padding = 50

        mv.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding))
        mv
    }
}

private suspend fun getMapCircles() : List<MapCircle> {
    val userService = RetrofitClient.userService
    val circles = mutableListOf<MapCircle>()

    CoroutineScope(Dispatchers.IO).async {
        val res = userService.getRecommendationAllInfo()

        if (res.isSuccessful) {
            val body = res.body()!!
            val size = body.size
            val list = body.list

            Log.d("전체 추천 DTO 응답 성공", "size = $size, list = $list")

            list.forEach { res ->
                val circle = MapCircle(
                    MapPoint.mapPointWithGeoCoord(res.lat, res.lng),
                    1000,
                    Color.argb(255, 255, 255, 0),
                    Color.argb(128, 128, 128, 128)
                )

                circles.add(circle)
            }
        }
    }.await()

    return circles
}