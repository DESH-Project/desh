package com.demo.desh

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.demo.desh.model.User
import com.demo.desh.ui.screens.MainScreen
import com.demo.desh.ui.screens.MapScreen
import com.demo.desh.ui.screens.ProfileScreen
import com.demo.desh.ui.screens.SettingsScreen
import com.demo.desh.ui.theme.DeshprojectfeTheme
import com.demo.desh.viewModel.MainViewModel
import com.demo.desh.viewModel.MainViewModelFactory
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getSerializableExtra("user") as User
        Log.d("MainActivity", "user = $user")

        viewModel = ViewModelProvider(this, MainViewModelFactory())[MainViewModel::class.java]
        val markerEventListener = MarkerEventListener(this, viewModel)

        setContent {
            DeshprojectfeTheme {
                Surface {
                    App(
                        viewModel = viewModel,
                        markerEventListener = markerEventListener,
                        user = user
                    )
                }
            }
        }
    }

    class MarkerEventListener(val context: Context, private val viewModel: MainViewModel) : MapView.POIItemEventListener {
        // 마커 클릭시
        override fun onPOIItemSelected(mapView: MapView?, mapPOIItem: MapPOIItem?) {
            /*
            val builder = AlertDialog.Builder(context)
            val itemList = arrayOf("토스트", "마커 삭제", "취소")

            builder.setTitle("${p1?.itemName}")
            builder.setItems(itemList) { dialog, which ->
                when (which) {
                    0 -> Toast.makeText(context, "토스트", Toast.LENGTH_SHORT).show()
                    1 -> p0?.removePOIItem(p1)
                    2 -> dialog.dismiss()
                }
            }

            builder.show()
            */

            val itemName = mapPOIItem?.itemName!!
            viewModel.fetchDistrictInfoList(itemName)
        }

        // 말풍선 클릭시 1 (사용 X)
        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, mapPOIItem: MapPOIItem?) {
            TODO("Not yet implemented")
        }

        // 말풍선 클릭시 2 (사용 O)
        override fun onCalloutBalloonOfPOIItemTouched(
            mapView: MapView?,
            mapPOIItem: MapPOIItem?,
            callOutButtonType: MapPOIItem.CalloutBalloonButtonType?
        ) {

        }

        // 마커 이동시 (isDraggable = true) 인 경우
        override fun onDraggablePOIItemMoved(mapView: MapView?, mapPOIItem: MapPOIItem?, mapPoint: MapPoint?) {
            TODO("Not yet implemented")
        }
    }

    /* 커스텀 말풍선 구현을 위한 클래스 */
    class CustomCallOutBalloonAdapter : CalloutBalloonAdapter {
        override fun getCalloutBalloon(mapPOIItem: MapPOIItem?): View {
            TODO("Not yet implemented")
        }

        override fun getPressedCalloutBalloon(p0: MapPOIItem?): View {
            TODO("Not yet implemented")
        }
    }
}

sealed class MainNavigation(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : MainNavigation("home", "Home", Icons.Outlined.Home)
    object Map : MainNavigation("map", "Map", Icons.Outlined.Phone)
    object Profile : MainNavigation("profile", "Profile", Icons.Outlined.AccountCircle)
    object Settings : MainNavigation("settings", "Settings", Icons.Outlined.Settings)

    companion object {
        val items = listOf(Home, Profile, Settings)
    }
}

@Composable
fun App(
    viewModel: MainViewModel,
    markerEventListener: MainActivity.MarkerEventListener,
    user: User
) {
    val navController = rememberNavController()
    val showBottomBar = navController
        .currentBackStackEntryAsState().value?.destination?.route != MainNavigation.Map.route

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigation {
                    val backStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = backStackEntry?.destination?.route

                    MainNavigation.items.forEach { navItem ->
                        BottomNavigationItem(
                            selected = currentRoute == navItem.route,
                            onClick = {
                                navController.navigate(navItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },

                            icon = {
                                Icon(
                                    imageVector = navItem.icon,
                                    contentDescription = navItem.title
                                )
                            },
                            label = { Text(text = navItem.title) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = MainNavigation.Home.route) {
                composable(route = MainNavigation.Home.route) {
                    MainScreen(viewModel, navController, user)
                }

                composable(route = MainNavigation.Profile.route) {
                    ProfileScreen()
                }

                composable(route = MainNavigation.Settings.route) {
                    SettingsScreen()
                }

                composable(route = MainNavigation.Map.route) {
                    val onBackButtonClick = { navController.navigate(MainNavigation.Home.route) }
                    MapScreen(viewModel, onBackButtonClick, markerEventListener)
                }
            }
        }
    }
}
