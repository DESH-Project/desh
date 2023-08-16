package com.demo.desh.ui.screens

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.demo.desh.api.RetrofitClient
import com.demo.desh.model.Recommend
import com.demo.desh.model.RecommendInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.daum.mf.map.api.CameraUpdateFactory
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPointBounds
import net.daum.mf.map.api.MapView


@Composable
fun MainScreen() {
    val dumList = listOf("의류", "제빵", "문구", "의료", "카페", "치킨")
    val coroutineScope = rememberCoroutineScope()

    Column {
        CreateListButton(dumList)

        AndroidView(
            factory = getMapView(coroutineScope),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CreateListButton(dums: List<String>) {
    Row {
        dums.forEach { dum -> TextButton(onClick = { }, content = { Text(text = dum) }) }
    }
}

private fun getMapView(coroutineScope: CoroutineScope) : (context: Context) -> MapView {
    val circles = mutableListOf<MapCircle>()
    val circleInfos = getAllRecommendInfo(coroutineScope = coroutineScope)
    Log.d("circleInfos", "${circleInfos.size}")

    return { context: Context ->
        val mv = MapView(context)
        val mapPointBoundsArray = mutableListOf<MapPointBounds>()

        circles.add(
            MapCircle(
                MapPoint.mapPointWithGeoCoord(37.5666805, 126.9784147),
                1000,
                Color.argb(128, 255, 0, 0),
                Color.argb(128, 255, 255, 0)
            )
        )

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

private fun getAllRecommendInfo(coroutineScope: CoroutineScope) : List<Recommend> {
    var result: RecommendInfo? = null

    coroutineScope.launch {
        val userService = RetrofitClient.userService
        result = userService.getRecommendationAllInfo()
    }

    val size = result?.size
    val list = result?.list

    Log.d("전체 추천 DTO 응답 성공", "size = $size, list = $list")

    return list ?: listOf()
}
