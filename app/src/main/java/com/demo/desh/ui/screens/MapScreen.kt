package com.demo.desh.ui.screens

import android.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.demo.desh.model.ServiceList
import com.demo.desh.util.MapViewCreator
import com.demo.desh.viewModel.MainViewModel
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPoint
import kotlin.random.Random


@Composable
fun MapScreen(
    viewModel: MainViewModel,
    onBackButtonClick: () -> Unit
) {
    val serviceList by viewModel.serviceList.observeAsState()
    val mv by viewModel.mapView.observeAsState()
    val recommendInfo by viewModel.recommendInfo.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchMapView()
        viewModel.fetchServiceList()
    }

    Column {
        CreateListButton(serviceList) { viewModel.fetchMapView(it) }

        IconButton(onClick = onBackButtonClick) {
            Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "뒤로가기")
        }

        AndroidView(
            factory = mv ?: MapViewCreator.createMapView(),
            modifier = Modifier.fillMaxSize(),
            update = { mv ->
                mv.removeAllCircles()

                var lats: Double = 0.0
                var lngs: Double = 0.0
                val size = recommendInfo?.list?.size ?: 1

                recommendInfo?.list?.forEach {
                    lats += it.lat
                    lngs += it.lng

                    val circle = MapCircle(
                        MapPoint.mapPointWithGeoCoord(it.lat, it.lng),
                        1500,
                        randomArgbColor(),
                        randomArgbColor()
                    )

                    circle.tag = it.predict.toInt()

                    mv.addCircle(circle)
                }

                mv.setMapCenterPointAndZoomLevel(
                    MapPoint.mapPointWithGeoCoord(lats / size, lngs / size),
                    7,
                    true
                )

            }
        )
    }
}

@Composable
private fun CreateListButton(
    serviceList: ServiceList?,
    onSelectedServiceNameChange: (String) -> Unit
) {
    LazyRow {
        itemsIndexed(serviceList?.list ?: listOf("Loading...")) { _, item ->
            Card {
                TextButton(onClick = { onSelectedServiceNameChange(item) }) {
                    Text(text = item)
                }
            }
        }
    }
}

private fun randomArgbColor() : Int {
    val random = Random.Default
    val alpha = random.nextInt(256)
    val red = random.nextInt(256)
    val green = random.nextInt(256)
    val blue = random.nextInt(256)

    return Color.argb(alpha, red, green, blue)
}