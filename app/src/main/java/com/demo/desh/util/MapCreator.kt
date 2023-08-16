package com.demo.desh.util

import android.content.Context
import android.graphics.Color
import android.util.Log
import com.demo.desh.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import net.daum.mf.map.api.CameraUpdateFactory
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPointBounds
import net.daum.mf.map.api.MapView

object MapCreator {
    suspend fun getMapView() : (context: Context) -> MapView {
        val mapCircles = getMapCircles()
        return createMapView(mapCircles)
    }

    private fun createMapView(circles: List<MapCircle>) : (context: Context) -> MapView {
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
}