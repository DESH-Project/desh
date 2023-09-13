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

    /*
    class MarkerEventListener(private val viewModel: MainViewModel) : MapView.POIItemEventListener {
        // 마커 클릭시
        override fun onPOIItemSelected(mapView: MapView?, mapPOIItem: MapPOIItem?) {
            Log.e("MarkerEventListener", "POI Item Selected : $mapPOIItem")

            val builder = AlertDialog.Builder(context)
            val itemList = arrayOf("토스트", "마커 삭제", "취소")

            builder.setTitle("${mapPOIItem?.itemName}")
            builder.setItems(itemList) { dialog, which ->
                when (which) {
                    0 -> Toast.makeText(context, "토스트", Toast.LENGTH_SHORT).show()
                    1 -> mapView?.removePOIItem(mapPOIItem)
                    2 -> dialog.dismiss()
                }
            }

            builder.show()

            onCalloutBalloonOfPOIItemTouched(mapView, mapPOIItem)

            mapView?.setMapCenterPointAndZoomLevel(
                mapPOIItem?.mapPoint,
                3,
                true
            )
        }

        // 말풍선 클릭시 1 (사용 X)
        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, mapPOIItem: MapPOIItem?) {
            this.onCalloutBalloonOfPOIItemTouched(mapView, mapPOIItem, null)
        }

        // 말풍선 클릭시 2 (사용 O)
        override fun onCalloutBalloonOfPOIItemTouched(
            mapView: MapView?,
            mapPOIItem: MapPOIItem?,
            callOutButtonType: MapPOIItem.CalloutBalloonButtonType?
        ) {
            viewModel.showBottomDrawer()
            viewModel.fetchDistrictInfoList(mapPOIItem?.itemName!!)
            mapPOIItem.selectedMarkerType = MapPOIItem.MarkerType.BluePin
        }

        // 마커 이동시 (isDraggable = true) 인 경우
        override fun onDraggablePOIItemMoved(mapView: MapView?, mapPOIItem: MapPOIItem?, mapPoint: MapPoint?) {
            TODO("Not yet implemented")
        }
    }

    class CustomCallOutBalloonAdapter : CalloutBalloonAdapter {
        override fun getCalloutBalloon(mapPOIItem: MapPOIItem?): View {
            TODO("Not yet implemented")
        }

        override fun getPressedCalloutBalloon(p0: MapPOIItem?): View {
            TODO("Not yet implemented")
        }
    }
     */
}

sealed class MainNavigation(
    val route: String,
    val title: String,
) {
    object Profile : MainNavigation("profile", "Profile")
    object Settings : MainNavigation("settings", "Settings")
    object Map : MainNavigation("map", "Map")
    object RealtyDetail : MainNavigation("realtyDetail", "RealtyDetail")
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

                composable(route = MainNavigation.Profile.route) {
                    ProfileScreen()
                }

                composable(route = MainNavigation.Settings.route) {
                    SettingsScreen()
                }

                composable(route = MainNavigation.Map.route) {
                    val goToRealtyDetail = { realtyId: Long -> navController.navigate(MainNavigation.RealtyDetail.route + "/$realtyId") }
                    MapScreen(user, viewModel, goToRealtyDetail)
                }

                composable(route = "${MainNavigation.RealtyDetail.route}/{${REALTY_ID}}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString(REALTY_ID)
                    id?.let { realtyId -> RealtyDetailScreen(realtyId.toLong(), user, viewModel) }
                }
            }
        }
    }
}
