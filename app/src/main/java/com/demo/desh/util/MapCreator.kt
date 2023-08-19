package com.demo.desh.util

import android.content.Context
import android.graphics.Color.argb
import android.util.Log
import com.demo.desh.api.RetrofitClient
import com.demo.desh.model.RecommendInfo
import net.daum.mf.map.api.CameraUpdateFactory
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPointBounds
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder

object MapCreator {
    private const val DEFAULT_SERVICE_NAME = "전체"

    fun getCommonMapView(serviceName: String = DEFAULT_SERVICE_NAME) : (context: Context) -> MapView {
        val commonCircles = getMapItems(serviceName)
        return createMapView(commonCircles)
    }

    private fun createMapView(circles: List<MapCircle>) : (context: Context) -> MapView {
        return { context: Context ->
            val mv = MapView(context)
            mv.removeAllCircles()
            val mapPointBoundsArray = mutableListOf<MapPointBounds>()

            circles.forEach { circle ->
                mv.addCircle(circle)
                mapPointBoundsArray.add(circle.bound)
            }

            val mapPointBounds = MapPointBounds(mapPointBoundsArray.toTypedArray())
            val padding = 150

            mv.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding))
            mv
        }
    }

    private fun getMapItems(serviceName: String) : List<MapCircle> {
        val userService = RetrofitClient.userService
        val circles = mutableListOf<MapCircle>()

        val response = if (serviceName == DEFAULT_SERVICE_NAME) {
            userService.getRecommendationAllInfo()
        } else {
            val encodedServiceName = URLEncoder.encode(serviceName, "UTF-8")
            userService.getRecommendationInfo(encodedServiceName)
        }

        response.enqueue(object : Callback<RecommendInfo> {
            override fun onResponse(call: Call<RecommendInfo>, response: Response<RecommendInfo>) {
                val body = response.body()!!
                val size = body.size
                val list = body.list

                Log.d("추천 DTO 응답 성공", "service = $serviceName, size = $size, list = $list")

                list.forEachIndexed { _, res ->
                    val circle = MapCircle(
                        MapPoint.mapPointWithGeoCoord(res.lat, res.lng),
                        1000,
                        argb(255, 255, 255, 0),
                        argb(128, 128, 128, 128)
                    )

                    circles.add(circle)
                }
            }

            override fun onFailure(call: Call<RecommendInfo>, t: Throwable) {
            }
        })

        return circles
    }
}