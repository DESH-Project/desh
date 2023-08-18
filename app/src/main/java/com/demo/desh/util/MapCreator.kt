package com.demo.desh.util

import android.content.Context
import android.graphics.Color.argb
import android.util.Log
import com.demo.desh.api.RetrofitClient
import com.demo.desh.model.Recommend
import com.demo.desh.model.RecommendInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.daum.mf.map.api.CameraUpdateFactory
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPointBounds
import net.daum.mf.map.api.MapView

object MapCreator {
    data class MapMetaData(
        val circles: List<MapCircle>,
        val markers: List<MapPOIItem>
    )

    suspend fun getMapView(map: MutableMap<String, RecommendInfo?>) : (context: Context) -> MapView {
        val mapMetaData = getMapItems(map)
        return createMapView(mapMetaData)
    }

    private fun createMapView(metaData: MapMetaData) : (context: Context) -> MapView {
        return { context: Context ->
            val mv = MapView(context)
            val mapPointBoundsArray = mutableListOf<MapPointBounds>()

            val circles = metaData.circles
            val markers = metaData.markers

            circles.forEach { circle ->
                mv.addCircle(circle)
                mapPointBoundsArray.add(circle.bound)
            }

            mv.addPOIItems(markers.toTypedArray())

            val mapPointBounds = MapPointBounds(mapPointBoundsArray.toTypedArray())
            val padding = 150

            mv.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding))
            mv
        }
    }

    private suspend fun getMapItems(map: MutableMap<String, RecommendInfo?>) : MapMetaData {
        val userService = RetrofitClient.userService
        val circles = mutableListOf<MapCircle>()
        val markers = mutableListOf<MapPOIItem>()

        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            val response = userService.getRecommendationAllInfo()

            if (response.isSuccessful) {
                val body = response.body()!!
                val size = body.size
                val list = body.list
                val serviceName = "전체"
                val recommendList = mutableListOf<Recommend>()

                Log.d("전체 추천 DTO 응답 성공", "size = $size, list = $list")

                list.forEachIndexed { _, res ->
                    val circle = MapCircle(
                        MapPoint.mapPointWithGeoCoord(res.lat, res.lng),
                        1000,
                        argb(255, 255, 255, 0),
                        argb(128, 128, 128, 128)
                    )

                    circles.add(circle)

                    val recommend = Recommend(
                        lat = res.lat,
                        lng = res.lng,
                        service = res.service,
                        district = res.district,
                        predict = res.predict
                    )

                    recommendList.add(recommend)
                }

                map[serviceName] = RecommendInfo(recommendList.size, recommendList)
            }
        }

        return MapMetaData(circles, markers)
    }

    /*
    suspend fun getMapView() : (context: Context) -> MapView {
        val markers = getMapMarkers()
        Log.e("getMapView()", "${markers.size}")

        return { context: Context ->
            val mv = MapView(context)

            markers.forEach { marker ->
                mv.addPOIItem(marker)
            }

            mv
        }
    }

    private suspend fun getMapMarkers() : List<MapPOIItem> {
        val userService = RetrofitClient.userService
        val markers = mutableListOf<MapPOIItem>()

        CoroutineScope(Dispatchers.IO).async {
            val response = userService.getRecommendationAllInfo()

            if (response.isSuccessful) {
                val body = response.body()!!
                val size = body.size
                val list = body.list

                Log.d("전체 추천 DTO 응답 성공", "size = $size, list = $list")

                list.forEachIndexed { idx, res ->
                    val marker = MapPOIItem()

                    marker.tag = idx
                    marker.itemName = "(${res.service}) ${res.district}\n평균 추정매출: ${res.predict}"
                    marker.mapPoint = MapPoint.mapPointWithGeoCoord(res.lat, res.lng)
                    marker.markerType = MapPOIItem.MarkerType.RedPin
                    marker.selectedMarkerType = MapPOIItem.MarkerType.BluePin

                    Log.e("marker 생성", marker.toString())

                    markers.add(marker)
                }
            }
        }.await()

        return markers
    }
    */
}