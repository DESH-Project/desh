package com.demo.desh

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.compose.material.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.demo.desh.model.User
import com.demo.desh.util.BottomNavigationBar
import com.demo.desh.ui.theme.DeshprojectfeTheme
import com.demo.desh.util.MainNavigationHost
import com.demo.desh.viewModel.MainViewModel
import com.demo.desh.viewModel.MainViewModelFactory
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
}

@Composable
fun App(
    viewModel: MainViewModel,
    markerEventListener: MainActivity.MarkerEventListener,
    user: User
) {
    val navController = rememberNavController()

    Scaffold(
        content = { it
            MainNavigationHost(
                navController = navController,
                viewModel = viewModel,
                markerEventListener = markerEventListener
            )
        },

        bottomBar = { BottomNavigationBar(navController = navController) }
    )
}
